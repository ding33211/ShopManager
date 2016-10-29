package com.soubu.goldensteward.view.activity;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.StoreOwnerVerifyActivityDelegate;
import com.soubu.goldensteward.view.fragment.StoreOwnerVerifyBaseInfoFragment;
import com.soubu.goldensteward.view.fragment.StoreOwnerVerifyStoreMergeFragment;
import com.soubu.goldensteward.view.fragment.StoreOwnerVerifyUploadCertificatesFragment;

/**
 * Created by lakers on 16/10/27.
 */

public class StoreOwnerVerifyActivity extends ActivityPresenter<StoreOwnerVerifyActivityDelegate>
        implements StoreOwnerVerifyBaseInfoFragment.OnClickNextStepListener,
        StoreOwnerVerifyUploadCertificatesFragment.OnClickNextStepListener,
        StoreOwnerVerifyStoreMergeFragment.OnClickFinishListener{


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
    public void onClickStep1() {
        viewDelegate.clickNextStep();
    }

    @Override
    public void onBackPressed() {
        if(!viewDelegate.backPopFragment()){
            super.onBackPressed();
        }
    }

    @Override
    public void onClickStep2() {
        viewDelegate.clickNextStep();
    }

    @Override
    public void onClickFinish() {
        viewDelegate.clickNextStep();
    }
}
