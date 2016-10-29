package com.soubu.goldensteward.view.fragment;

import com.soubu.goldensteward.base.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.delegate.StoreOwnerVerifyStoreAppealFragmentDelegate;

/**
 * Created by lakers on 16/10/28.
 */

public class StoreOwnerVerifyStoreAppealFragment extends FragmentPresenter<StoreOwnerVerifyStoreAppealFragmentDelegate> {


    @Override
    protected Class<StoreOwnerVerifyStoreAppealFragmentDelegate> getDelegateClass() {
        return StoreOwnerVerifyStoreAppealFragmentDelegate.class;
    }


}
