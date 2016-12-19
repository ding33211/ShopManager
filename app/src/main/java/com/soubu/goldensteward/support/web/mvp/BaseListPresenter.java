package com.soubu.goldensteward.support.web.mvp;

import com.soubu.goldensteward.support.utils.LogUtil;
import com.soubu.goldensteward.support.web.core.BaseException;
import com.soubu.goldensteward.support.web.core.BaseResponse;
import com.soubu.goldensteward.support.web.core.BaseSubscriber;
import com.soubu.goldensteward.support.web.core.ObservableWrapper;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * 作者：余天然 on 2016/12/12 上午11:20
 */
public abstract class BaseListPresenter<R, V extends BaseView> extends BasePresenter<V> {

    public Observable<List<R>> getData(int curPage) {
        LogUtil.print("");
        return Observable.create(new Observable.OnSubscribe<List<R>>() {
            @Override
            public void call(Subscriber<? super List<R>> subscriber) {
                getWrapper(curPage)
                        .sendTo(new BaseSubscriber<BaseResponse<List<R>>>(getView()) {
                            @Override
                            public void onSuccess(BaseResponse<List<R>> response) {
                                LogUtil.print("");
                                List<R> data = response.getResult().getData();
                                List<R> subList = data.subList(0, 1);
                                subscriber.onNext(subList);
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onFailure(BaseException exception) {
                                LogUtil.print("");
                                super.onFailure(exception);
                                subscriber.onError(exception);
                            }
                        }, false);
            }
        });
    }

    protected abstract ObservableWrapper<BaseResponse<List<R>>> getWrapper(int curPage);

}
