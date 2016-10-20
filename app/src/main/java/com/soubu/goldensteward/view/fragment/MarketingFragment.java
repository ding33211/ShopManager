package com.soubu.goldensteward.view.fragment;

import com.soubu.goldensteward.base.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.delegate.MarketingFragmentDelegate;

/**
 * Created by dingsigang on 16-10-18.
 */
public class MarketingFragment extends FragmentPresenter<MarketingFragmentDelegate> {
    @Override
    protected Class getDelegateClass() {
        return MarketingFragmentDelegate.class;
    }
}
