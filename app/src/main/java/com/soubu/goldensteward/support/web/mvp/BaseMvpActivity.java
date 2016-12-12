package com.soubu.goldensteward.support.web.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.soubu.goldensteward.support.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * 作者：余天然 on 2016/12/12 上午11:22
 */
public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity {

    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(createLayoutId());
        ButterKnife.bind(this);
        presenter = createPresenter();
        if (presenter == null) {
            throw new NullPointerException("Presenter is null");
        }
        presenter.attachView(this);
    }

    protected abstract int createLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView(false);
    }

    protected abstract P createPresenter();

    public P getPresenter() {
        return presenter;
    }
}
