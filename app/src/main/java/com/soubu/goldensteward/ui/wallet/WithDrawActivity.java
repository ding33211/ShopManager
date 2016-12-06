package com.soubu.goldensteward.ui.wallet;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.support.delegate.FragmentActivityDelegate;

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
