package com.soubu.goldensteward.ui.market;

import android.support.v4.app.Fragment;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.ui.information.CompanyInformationFragment;
import com.soubu.goldensteward.ui.information.PersonalInformationFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-10-18.
 */
public class MarketingFragment extends FragmentPresenter<MarketingFragmentDelegate> {
    AllActivityFragment mAllActivityFragment;
    MyActivityFragment mMyActivityFragment;

    @Override
    protected Class getDelegateClass() {
        return MarketingFragmentDelegate.class;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
        List<Fragment> fragments = new ArrayList<>();
        mAllActivityFragment = new AllActivityFragment();
        mMyActivityFragment = new MyActivityFragment();
        fragments.add(mAllActivityFragment);
        fragments.add(mMyActivityFragment);
        String[] titles = new String[]{getString(R.string.all_activity), getString(R.string.activity_i_join)};
        viewDelegate.initFragment(fragments, titles);
    }
}
