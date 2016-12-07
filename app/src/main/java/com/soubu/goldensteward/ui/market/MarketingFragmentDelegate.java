package com.soubu.goldensteward.ui.market;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.delegate.BaseFragmentDelegate;
import com.soubu.goldensteward.support.bean.server.AllActivityServerParams;
import com.soubu.goldensteward.support.bean.server.MyActivityServerParams;
import com.soubu.goldensteward.support.widget.TitleFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-10-18.
 */
public class MarketingFragmentDelegate extends BaseFragmentDelegate {
    RecyclerView mRvContent;
    AllActivityRvAdapter mAllActivityAdapter;
    MyActivityRvAdapter mMyActivityAdapter;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_marketing;
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

}
