package com.soubu.goldensteward.delegate;

import android.support.design.widget.TabLayout;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;

/**
 * Created by dingsigang on 16-11-30.
 */

public class StoreFlowStatisticsActivityDelegate extends AppDelegate {
    int mCurrentTabIndex = 0;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_store_flow_statistics;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        View vLastWeek = get(R.id.bt_last_week);
        View vLastMonth = get(R.id.bt_last_month);
        mTopButtons = new View[]{vLastWeek, vLastMonth};
        mTopButtons[0].setSelected(true);
    }

    public void initTabLayout(String[] titles) {
        TabLayout tabLayout = get(R.id.tl_title);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (mCurrentTabIndex != tab.getPosition()) {
                    mCurrentTabIndex = tab.getPosition();
                    switch (mCurrentTabIndex) {
                        case 0:
                            break;
                        case 1:
                            break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        for (String a : titles) {
            tabLayout.addTab(tabLayout.newTab().setText(a));
        }
    }
}
