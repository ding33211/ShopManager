package com.soubu.goldensteward.view.activity;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.FragmentActivityDelegate;
import com.soubu.goldensteward.view.fragment.WithDrawFragment;
import com.soubu.goldensteward.view.fragment.WithDrawSuccessFragment;

/**
 * Created by dingsigang on 16-10-20.
 */
public class WithDrawActivity extends ActivityPresenter<FragmentActivityDelegate> implements WithDrawFragment.OnPayPwdMatchListener{

    WithDrawFragment mWithDrawFragment;
    WithDrawSuccessFragment mWithDrawSuccessFragment;

    @Override
    protected Class<FragmentActivityDelegate> getDelegateClass() {
        return FragmentActivityDelegate.class;
    }

    @Override
    protected void initView() {
        super.initView();
        mWithDrawFragment = new WithDrawFragment();
    }

    @Override
    protected void initData() {
        super.initData();
        viewDelegate.addFragment(mWithDrawFragment);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.withdraw);
    }

    @Override
    public void onPayPwdMatch() {
        mWithDrawSuccessFragment = new WithDrawSuccessFragment();
        viewDelegate.replaceFragment(mWithDrawSuccessFragment);

    }
}
