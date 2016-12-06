package com.soubu.goldensteward.ui.home;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.Button;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.mvp.view.AppDelegate;
import com.soubu.goldensteward.ui.market.MarketingFragment;
import com.soubu.goldensteward.ui.referstore.SubAccountFragment;

/**
 * Created by dingsigang on 16-10-17.
 */
public class HomeActivityDelegate extends AppDelegate {

    private Button[] mTabs;
    private Fragment[] mFragments;
    private HomeFragment mHomeFragment;
    private SubAccountFragment mSubAccountFragment;
    private MarketingFragment mMarketingFragment;
    FragmentManager mFragmentManager;
    final String TAG_HOME = "HOME";
    final String TAG_SUB_ACCOUNT = "SUB_ACCOUNT";
    final String TAG_MARKETING = "MARKETING";
    String[] mTags;


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
        mFragmentManager = getActivity().getSupportFragmentManager();
        mHomeFragment = new HomeFragment();
        mSubAccountFragment = new SubAccountFragment();
        mMarketingFragment = new MarketingFragment();
        mTabs = new Button[]{get(R.id.btn_home), get(R.id.btn_sub_account),
                get(R.id.btn_marketing)};
        mFragments = new Fragment[]{mHomeFragment, mSubAccountFragment, mMarketingFragment};
        mTags = new String[]{TAG_HOME, TAG_SUB_ACCOUNT, TAG_MARKETING};
        mTabs[0].setSelected(true);
        addFragment(mFragments[0], mTags[0]);
    }

    public void showFragmentAndSelectTab(int index, int currentIndex) {
        showFragment(mTags[currentIndex], mTags[index], mFragments[index]);
        mTabs[currentIndex].setSelected(false);
        // set current tab selected
        mTabs[index].setSelected(true);
    }


    public void showFragment(String hideTag, String showTag, Fragment fragment) {
        Fragment hideFragment = mFragmentManager.findFragmentByTag(hideTag);
        Fragment showFragment = mFragmentManager.findFragmentByTag(showTag);
        if (hideFragment != null && !hideFragment.isHidden()) {
            mFragmentManager.beginTransaction().hide(hideFragment).commit();
        }
        if (showFragment != null && showFragment.isHidden()) {
            mFragmentManager.beginTransaction().show(showFragment).commit();
        } else if (showFragment == null) {
            addFragment(fragment, showTag);
        }
    }

    public void addFragment(Fragment fragment, String tag) {
        mFragmentManager.beginTransaction().add(R.id.fragment_container, fragment, tag).commit();
    }

}
