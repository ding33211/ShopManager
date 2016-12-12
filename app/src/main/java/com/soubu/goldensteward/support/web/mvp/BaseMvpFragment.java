package com.soubu.goldensteward.support.web.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.components.RxFragment;

/**
 * 作者：余天然 on 2016/12/12 上午11:27
 */
public abstract class BaseMvpFragment<P extends BasePresenter> extends RxFragment implements BaseView {

    protected P presenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (presenter == null) {
            presenter = createPresenter();
        }
        presenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView(getRetainInstance());
    }

    protected abstract P createPresenter();

    @Override
    public <T> LifecycleTransformer<T> bindLife() {
        return bindToLifecycle();
    }

}
