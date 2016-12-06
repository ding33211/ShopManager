package com.soubu.goldensteward.ui.storepreview;

import android.support.design.widget.TabLayout;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.mvp.view.AppDelegate;

/**
 * Created by dingsigang on 16-11-25.
 */

public class EvaluateInStorePreviewActivityDelegate extends AppDelegate {
    int mCurrentTabIndex = 0;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_evaluate_in_store_preview;
    }

    public void initTabLayout(String[] titles){
        TabLayout tabLayout = get(R.id.tl_title);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (mCurrentTabIndex != tab.getPosition()) {
                    mCurrentTabIndex = tab.getPosition();

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
