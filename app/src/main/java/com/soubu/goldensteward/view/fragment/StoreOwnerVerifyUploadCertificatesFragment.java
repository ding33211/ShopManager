package com.soubu.goldensteward.view.fragment;

import com.soubu.goldensteward.base.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.delegate.StoreOwnerVerifyUploadCertificatesFragmentDelegate;

/**
 * Created by lakers on 16/10/28.
 */

public class StoreOwnerVerifyUploadCertificatesFragment extends FragmentPresenter<StoreOwnerVerifyUploadCertificatesFragmentDelegate> {
    @Override
    protected Class<StoreOwnerVerifyUploadCertificatesFragmentDelegate> getDelegateClass() {
        return StoreOwnerVerifyUploadCertificatesFragmentDelegate.class;
    }
}
