package com.soubu.goldensteward.wallet;

import com.soubu.goldensteward.base.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.wallet.WithDrawSuccessFragmentDelegate;

/**
 * Created by dingsigang on 16-10-20.
 */
public class WithDrawSuccessFragment extends FragmentPresenter<WithDrawSuccessFragmentDelegate> {
    @Override
    protected Class<WithDrawSuccessFragmentDelegate> getDelegateClass() {
        return WithDrawSuccessFragmentDelegate.class;
    }
}
