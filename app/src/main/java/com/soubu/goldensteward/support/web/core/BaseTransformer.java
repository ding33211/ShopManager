package com.soubu.goldensteward.support.web.core;


import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.constant.BaseConfig;
import com.soubu.goldensteward.support.utils.ShowWidgetUtil;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

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
                    .subscribeOn(Schedulers.io())//在异步线程执行耗时操作
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            ShowWidgetUtil.showProgressDialog(null, R.style.LoadingProgressTheme);
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())//在主线程显示进度条
                    .observeOn(AndroidSchedulers.mainThread());//在主线程回调，更新UI;
        }

    }
}
