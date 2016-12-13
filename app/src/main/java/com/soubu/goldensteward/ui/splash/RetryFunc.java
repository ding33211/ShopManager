package com.soubu.goldensteward.ui.splash;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Rx的重试器
 * <p>
 * 作者：余天然 on 2016/12/13 下午2:15
 */
public class RetryFunc implements Func1<Observable<? extends Throwable>, Observable<?>> {

    private int maxRetries;//最大重试次数

    private int retryCount;//实际重试次数

    public RetryFunc(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    @Override
    public Observable<?> call(Observable<? extends Throwable> observable) {
        return observable
                .flatMap(new Func1<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> call(Throwable throwable) {
                        if (++retryCount <= maxRetries) {
                            return Observable.timer(100, TimeUnit.MILLISECONDS, Schedulers.io());
                        }
                        return Observable.error(throwable);
                    }
                });
    }

}
