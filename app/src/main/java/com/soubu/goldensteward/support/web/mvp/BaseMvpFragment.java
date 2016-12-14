package com.soubu.goldensteward.support.web.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soubu.goldensteward.support.utils.LogUtil;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;

/**
 * 作者：余天然 on 2016/12/12 上午11:27
 */
public abstract class BaseMvpFragment<P extends BasePresenter> extends RxFragment implements BaseView {

    protected P presenter;
    protected View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        view = create(inflater, container, savedInstanceState);
        LogUtil.print("绑定了View");
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (presenter == null) {
            presenter = createPresenter();
        }
        LogUtil.print("使用了View");
        presenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView(getRetainInstance());
    }

    @Override
    public <T> LifecycleTransformer<T> bindLife() {
        return bindToLifecycle();
    }

    public View create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int rootLayoutId = createLayoutId();
        return inflater.inflate(rootLayoutId, container, false);
    }

    protected abstract int createLayoutId();

    protected abstract P createPresenter();

}
