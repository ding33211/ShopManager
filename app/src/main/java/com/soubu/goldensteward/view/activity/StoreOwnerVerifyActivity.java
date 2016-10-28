package com.soubu.goldensteward.view.activity;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.StoreOwnerVerifyActivityDelegate;
import com.soubu.goldensteward.view.fragment.StoreOwnerVerifyBaseInfoFragment;

/**
 * Created by lakers on 16/10/27.
 */

public class StoreOwnerVerifyActivity extends ActivityPresenter<StoreOwnerVerifyActivityDelegate> implements StoreOwnerVerifyBaseInfoFragment.OnClickNextStepListener{


    @Override
    protected Class<StoreOwnerVerifyActivityDelegate> getDelegateClass() {
        return StoreOwnerVerifyActivityDelegate.class;
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.store_owner_verify);
    }

    @Override
    public void onClickNextStep() {
        viewDelegate.clickStep1();
    }
}
