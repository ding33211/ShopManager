package com.soubu.goldensteward.view.fragment;

import com.soubu.goldensteward.base.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.delegate.SubAccountFragmentDelegate;

/**
 * Created by dingsigang on 16-10-18.
 */
public class SubAccountFragment extends FragmentPresenter<SubAccountFragmentDelegate> {
    @Override
    protected Class<SubAccountFragmentDelegate> getDelegateClass() {
        return SubAccountFragmentDelegate.class;
    }
}
