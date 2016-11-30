package com.soubu.goldensteward.delegate;

import android.support.design.widget.TabLayout;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;
import com.soubu.goldensteward.widget.linebarchart.LineView;
import com.soubu.goldensteward.widget.linebarchart.YAxisView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by dingsigang on 16-11-30.
 */

public class RefundDataActivityDelegate extends AppDelegate {
    LineView mLineView;
    YAxisView mLeftAxisView;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_refund_data;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mLineView = get(R.id.line_view);
        mLeftAxisView = get(R.id.v_left_line);
        mLineView.setUnit(getActivity().getString(R.string.percent));

    }

    public void initTabLayout(String[] titles) {
        TabLayout tabLayout = get(R.id.tl_title);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
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

    public void setBarDataList(ArrayList<ArrayList<Integer>> list, int space, ArrayList<Integer> colorList,
                               ArrayList<ArrayList<String>> contentList) {
        mLineView.setBarDataList(list, mLeftAxisView, space, colorList, contentList);
    }

    public void setBottomTextList(ArrayList<Date> list, String format) {
        mLineView.setBottomTextList(list, format);
    }

    public void setLineViewBottomSize(int size) {
        mLineView.setBottomTextSize(size);
        mLineView.requestLayout();
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }
}
