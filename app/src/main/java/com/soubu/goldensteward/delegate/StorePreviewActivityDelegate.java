package com.soubu.goldensteward.delegate;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;
import com.soubu.goldensteward.server.RetrofitRequest;
import com.soubu.goldensteward.view.activity.EvaluateInStorePreviewActivity;

/**
 * Created by dingsigang on 16-11-25.
 */

public class StorePreviewActivityDelegate extends AppDelegate {
    int mCurrentTabIndex = 0;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_store_preview;
    }

    @Override
    public void initWidget() {
        super.initWidget();

    }

    public void initTabLayout(String[] titles) {
        TabLayout tabLayout = get(R.id.tl_title);
        tabLayout.setVisibility(View.VISIBLE);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (mCurrentTabIndex != tab.getPosition()) {
                    mCurrentTabIndex = tab.getPosition();
                    switch (mCurrentTabIndex) {
                        case 3:
                            Intent intent = new Intent(getActivity(), EvaluateInStorePreviewActivity.class);
                            getActivity().startActivity(intent);
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

    @Override
    public boolean ifNeedHideToolBar() {
        return true;
    }
}
