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
 * 自带延迟加载的Fragment基类
 * <p>
 * 作者：余天然 on 2016/12/12 上午11:27
 */
public abstract class BaseMvpFragment<P extends BasePresenter> extends RxFragment implements BaseView {

    protected P presenter;

    protected View rootView;

    public boolean isPrepared = false;//控件是否加载好了

    public boolean isInit = false;//是否已经初始化数据了

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        rootView = create(inflater, container, savedInstanceState);
        LogUtil.print("绑定了View");
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public View create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int rootLayoutId = createLayoutId();
        return inflater.inflate(rootLayoutId, container, false);
    }

    @Override
    public <T> LifecycleTransformer<T> bindLife() {
        return bindToLifecycle();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //防止空指针异常
        if (!isPrepared) {
            return;
        }
        onFragmentVisibleChange(isVisibleToUser);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPrepared = true;
        if (getUserVisibleHint()) {
            onFragmentVisibleChange(true);
        }
    }

    public void onFragmentVisibleChange(boolean isVisible) {
        LogUtil.print("isVisible=" + isVisible);
        //可见且未加载过数据，则加载数据
        if (isVisible && !isInit) {
            init();
            isInit = true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView(getRetainInstance());
    }

    /**
     * 初始化操作
     */
    protected void init() {
        if (presenter == null) {
            presenter = createPresenter();
        }
        LogUtil.print("使用了View");
        presenter.attachView(this);
    }

    /**
     * 创建布局的id
     */
    protected abstract int createLayoutId();

    /**
     * 创建使用的Presenter
     */
    protected abstract P createPresenter();
}
