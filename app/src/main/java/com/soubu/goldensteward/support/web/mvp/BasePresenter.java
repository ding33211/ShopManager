package com.soubu.goldensteward.support.web.mvp;

import java.lang.ref.WeakReference;

/**
 * 作者：余天然 on 2016/12/12 上午11:20
 */
public abstract class BasePresenter<V extends BaseView> {

    private WeakReference<V> viewRef;

    public void attachView(V view) {
        viewRef = new WeakReference<V>((V) view);
        getView().initWidget();
        initData();
    }

    protected void initData() {

    }

    public void detachView(boolean retainInstance) {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

    public V getView() {
        return viewRef == null ? null : viewRef.get();
    }

}
