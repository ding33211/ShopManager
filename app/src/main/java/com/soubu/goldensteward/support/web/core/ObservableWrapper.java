package com.soubu.goldensteward.support.web.core;


import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.constant.BaseConfig;
import com.soubu.goldensteward.support.utils.LogUtil;
import com.soubu.goldensteward.support.utils.ShowWidgetUtil;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

/**
 * 作者：余天然 on 2016/12/9 下午4:18
 */
public class ObservableWrapper<T> {

    private Observable<T> observable;

    public ObservableWrapper(Observable<T> observable) {
        this.observable = observable;
    }

    public Observable<T> getObservable() {
        return observable;
    }

    public Subscription sendTo(BaseSubscriber<T> subscriber) {
        return sendTo(subscriber, true);
    }

    public Subscription sendTo(BaseSubscriber<T> subscriber, boolean hasProgress) {
        LogUtil.print("BaseConfig.IS_TEST=" + BaseConfig.IS_TEST);
        if (!BaseConfig.IS_TEST) {
            return observable
                    .compose(new BaseTransformer<>())
                    .compose(subscriber.getBaseView().bindLife())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            if (hasProgress) {
                                ShowWidgetUtil.showProgressDialog(null, R.style.LoadingProgressTheme);
                            }
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())//在主线程显示进度条
                    .subscribe(subscriber);
        } else {
            return observable
                    .subscribe(subscriber);
        }

    }

}
