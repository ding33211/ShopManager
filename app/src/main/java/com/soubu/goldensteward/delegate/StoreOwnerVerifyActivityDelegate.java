package com.soubu.goldensteward.delegate;

import com.soubu.goldensteward.view.fragment.StoreOwnerVerifyBaseInfoFragment;
import com.soubu.goldensteward.view.fragment.StoreOwnerVerifyUploadCertificatesFragment;

/**
 * Created by lakers on 16/10/27.
 */

public class StoreOwnerVerifyActivityDelegate extends FragmentActivityDelegate {
    int mCurrentIndex = 0;
    int mNextIndex = 0;
    StoreOwnerVerifyBaseInfoFragment mBaseInfoFragment;
    StoreOwnerVerifyUploadCertificatesFragment mUploadBaseInfoFragment;

    @Override
    public void initWidget() {
        super.initWidget();
        mBaseInfoFragment = new StoreOwnerVerifyBaseInfoFragment();
        mUploadBaseInfoFragment = new StoreOwnerVerifyUploadCertificatesFragment();
        addFragment(mBaseInfoFragment);
    }


    public void clickStep1(){
        addFragment(mUploadBaseInfoFragment);
    }
}
