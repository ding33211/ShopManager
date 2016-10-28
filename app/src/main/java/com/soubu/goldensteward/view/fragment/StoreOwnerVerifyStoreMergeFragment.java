package com.soubu.goldensteward.view.fragment;

import com.soubu.goldensteward.base.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.delegate.StoreOwnerVerifyStoreMergeFragmentDelegate;

/**
 * Created by lakers on 16/10/28.
 */

public class StoreOwnerVerifyStoreMergeFragment extends FragmentPresenter<StoreOwnerVerifyStoreMergeFragmentDelegate> {
    @Override
    protected Class<StoreOwnerVerifyStoreMergeFragmentDelegate> getDelegateClass() {
        return StoreOwnerVerifyStoreMergeFragmentDelegate.class;
    }
}
