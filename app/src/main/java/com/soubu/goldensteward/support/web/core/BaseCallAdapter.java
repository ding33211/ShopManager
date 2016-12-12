package com.soubu.goldensteward.support.web.core;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.subscriptions.Subscriptions;

/**
 * 作者：余天然 on 2016/12/9 下午5:13
 */
public class BaseCallAdapter implements CallAdapter<ObservableWrapper<?>> {

    private final Type responseType;

    // 下面的 responseType 方法需要数据的类型
    BaseCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public <R> ObservableWrapper<R> adapt(Call<R> call) {
        // 由 CustomCall 决定如何使用
        Observable<R> observable = Observable.create(new CallOnSubscribe<>(call)) //
                .flatMap(new Func1<Response<R>, Observable<R>>() {
                    @Override
                    public Observable<R> call(Response<R> response) {
                        if (response.isSuccess()) {
                            R body = response.body();
                            return Observable.just(body);
                        }
                        return Observable.error(new HttpException(response));
                    }
                });
        return new ObservableWrapper<R>(observable);
    }

    static final class CallOnSubscribe<T> implements Observable.OnSubscribe<Response<T>> {
        private final Call<T> originalCall;

        CallOnSubscribe(Call<T> originalCall) {
            this.originalCall = originalCall;
        }

        @Override
        public void call(final Subscriber<? super Response<T>> subscriber) {
            // Since Call is a one-shot type, clone it for each new subscriber.
            final Call<T> call = originalCall.clone();

            // Attempt to cancel the call if it is still in-flight on unsubscription.
            subscriber.add(Subscriptions.create(new Action0() {
                @Override
                public void call() {
                    call.cancel();
                }
            }));

            try {
                Response<T> response = call.execute();
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(response);
                }
            } catch (Throwable t) {
                Exceptions.throwIfFatal(t);
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onError(t);
                }
                return;
            }

            if (!subscriber.isUnsubscribed()) {
                subscriber.onCompleted();
            }
        }
    }
}
