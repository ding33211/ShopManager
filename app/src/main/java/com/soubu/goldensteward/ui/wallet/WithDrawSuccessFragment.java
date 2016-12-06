package com.soubu.goldensteward.ui.wallet;

import com.soubu.goldensteward.support.mvp.presenter.FragmentPresenter;

/**
 * Created by dingsigang on 16-10-20.
 */
public class WithDrawSuccessFragment extends FragmentPresenter<WithDrawSuccessFragmentDelegate> {
    @Override
    protected Class<WithDrawSuccessFragmentDelegate> getDelegateClass() {
        return WithDrawSuccessFragmentDelegate.class;
    }
}
