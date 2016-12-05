package com.soubu.goldensteward.base.delegate;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;
import com.soubu.goldensteward.widget.TitleFragmentPagerAdapter;

import java.util.List;

/**
 * Created by dingsigang on 16-10-19.
 */
public class TabViewpagerActivityDelegate extends AppDelegate {

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_tab_viewpager;
    }

    @Override
    public void initWidget() {
        super.initWidget();
    }

    public void initFragment(List<Fragment> fragmentList, String[] titles) {
        ViewPager viewPager = get(R.id.vp_content);
        TabLayout tabLayout = get(R.id.tl_title);
        TitleFragmentPagerAdapter adapter = new TitleFragmentPagerAdapter(getActivity().getSupportFragmentManager(),
                fragmentList, titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }
}
