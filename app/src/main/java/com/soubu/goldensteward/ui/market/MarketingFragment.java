package com.soubu.goldensteward.ui.market;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.mvp.presenter.FragmentPresenter;

/**
 * Created by dingsigang on 16-10-18.
 */
public class MarketingFragment extends FragmentPresenter<MarketingFragmentDelegate> {
    @Override
    protected Class getDelegateClass() {
        return MarketingFragmentDelegate.class;
    }

    @Override
    protected void initView() {
        super.initView();
        viewDelegate.initTabLayout(new String[]{getString(R.string.all_activity), getString(R.string.activity_i_join)});
    }

    @Override
    protected void initData() {
        super.initData();
    }
}
