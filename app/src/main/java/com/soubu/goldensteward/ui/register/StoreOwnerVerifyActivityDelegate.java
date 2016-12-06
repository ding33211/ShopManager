package com.soubu.goldensteward.ui.register;

import android.support.v4.app.Fragment;

import com.soubu.goldensteward.support.delegate.FragmentActivityDelegate;
import com.soubu.goldensteward.support.utils.WindowUtil;

/**
 * Created by lakers on 16/10/27.
 */

public class StoreOwnerVerifyActivityDelegate extends FragmentActivityDelegate {
    int mCurrentIndex = 0;
    final String TAG_BASE_INFO = "BASE_INFO";
    final String TAG_UPLOAD_CERTIFICATES = "UPLOAD_CERTIFICATES";
    final String TAG_STORE_MERGE = "STORE_MERGE";
    final String TAG_STORE_APPEAL = "STORE_APPEAL";
    String[] mTags;
    Fragment[] mFragments;
    StoreOwnerVerifyBaseInfoFragment mBaseInfoFragment;
    StoreOwnerVerifyUploadCertificatesFragment mUploadBaseInfoFragment;
    StoreOwnerVerifyStoreMergeFragment mStoreMergeFragment;
    StoreOwnerVerifyStoreAppealFragment mStoreAppealFragment;

    @Override
    public void initWidget() {
        super.initWidget();
        mTags = new String[]{TAG_BASE_INFO, TAG_UPLOAD_CERTIFICATES, TAG_STORE_MERGE, TAG_STORE_APPEAL};
        mBaseInfoFragment = new StoreOwnerVerifyBaseInfoFragment();
        mUploadBaseInfoFragment = new StoreOwnerVerifyUploadCertificatesFragment();
        mStoreMergeFragment = new StoreOwnerVerifyStoreMergeFragment();
        if (mCurrentIndex == 2) {
            mStoreMergeFragment.setFromLogin(true);
        }
        mStoreAppealFragment = new StoreOwnerVerifyStoreAppealFragment();
        mFragments = new Fragment[]{mBaseInfoFragment, mUploadBaseInfoFragment, mStoreMergeFragment, mStoreAppealFragment};
        addFragment(mFragments[mCurrentIndex], mTags[mCurrentIndex]);
    }


    public void clickNextStep() {
        showFragment(mTags[mCurrentIndex++], mTags[mCurrentIndex], mFragments[mCurrentIndex]);
        WindowUtil.hideSoftInput(this.getActivity());
    }

    public void setCurrentIndex(int currentIndex) {
        mCurrentIndex = currentIndex;
    }

    public boolean backPopFragment() {
        if (mCurrentIndex != 1) {
            return false;
        } else {
            showFragment(mTags[mCurrentIndex--], mTags[mCurrentIndex], mFragments[mCurrentIndex]);
            WindowUtil.hideSoftInput(getActivity());
            return true;
        }
    }

    public void setFileType(String type) {
        mUploadBaseInfoFragment.setFileType(type);
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }
}
