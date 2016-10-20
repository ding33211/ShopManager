package com.soubu.goldensteward.delegate;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;
import com.soubu.goldensteward.view.fragment.HomeFragment;
import com.soubu.goldensteward.view.fragment.MarketingFragment;
import com.soubu.goldensteward.view.fragment.SubAccountFragment;

/**
 * Created by dingsigang on 16-10-17.
 */
public class HomeActivityDelegate extends AppDelegate {

    private Button[] mTabs;
    private Fragment[] mFragments;
    private HomeFragment mHomeFragment;
    private SubAccountFragment mSubAccountFragment;
    private MarketingFragment mMarketingFragment;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public boolean ifNeedHideToolBar() {
        return true;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mHomeFragment = new HomeFragment();
        mSubAccountFragment = new SubAccountFragment();
        mMarketingFragment = new MarketingFragment();
        mTabs = new Button[]{get(R.id.btn_home), get(R.id.btn_sub_account),
                get(R.id.btn_marketing)};
        mFragments = new Fragment[]{mHomeFragment, mSubAccountFragment, mMarketingFragment};
        mTabs[0].setSelected(true);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mHomeFragment).commit();
    }

    public void showFragmentAndSelectTab(int index, int currentIndex) {
        if (currentIndex != index) {
            FragmentTransaction trx = getActivity().getSupportFragmentManager().beginTransaction();
            trx.hide(mFragments[currentIndex]);
            if (!mFragments[index].isAdded()) {
                trx.replace(R.id.fragment_container, mFragments[index]);
            }
            trx.commit();
        }
        mTabs[currentIndex].setSelected(false);
        // set current tab selected
        mTabs[index].setSelected(true);
    }
}
