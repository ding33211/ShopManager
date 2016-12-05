package com.soubu.goldensteward.register;

import com.soubu.goldensteward.base.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.register.StoreOwnerVerifyStoreAppealFragmentDelegate;

/**
 * Created by lakers on 16/10/28.
 */

public class StoreOwnerVerifyStoreAppealFragment extends FragmentPresenter<StoreOwnerVerifyStoreAppealFragmentDelegate> {


    @Override
    protected Class<StoreOwnerVerifyStoreAppealFragmentDelegate> getDelegateClass() {
        return StoreOwnerVerifyStoreAppealFragmentDelegate.class;
    }


}
