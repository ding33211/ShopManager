package com.soubu.goldensteward.support.web.core;


import com.soubu.goldensteward.support.base.BaseApplication;
import com.soubu.goldensteward.support.constant.BaseConfig;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Rx事件变换器
 * <p>
 * 对发送的事件做统一处理
 * <p>
 * 作者：余天然 on 16/7/18 下午10:20
 */
public class BaseTransformer<T> implements Observable.Transformer<T, T> {

    public BaseTransformer() {
    }

    @Override
    public Observable<T> call(Observable<T> observable) {
        if (BaseConfig.IS_TEST) {
            return observable;
        } else {
            return observable
                    .subscribeOn(BaseApplication.getScheduler())//在异步线程执行耗时操作
                    .observeOn(AndroidSchedulers.mainThread());//在主线程回调，更新UI;
        }

    }
}
