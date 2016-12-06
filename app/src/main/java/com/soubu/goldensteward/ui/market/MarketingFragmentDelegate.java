package com.soubu.goldensteward.ui.market;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.delegate.BaseFragmentDelegate;
import com.soubu.goldensteward.support.bean.server.AllActivityServerParams;
import com.soubu.goldensteward.support.bean.server.MyActivityServerParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-10-18.
 */
public class MarketingFragmentDelegate extends BaseFragmentDelegate {
    RecyclerView mRvContent;
    AllActivityRvAdapter mAdapter;


    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_marketing;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mRvContent = get(R.id.rv_content);
        mRvContent.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mAdapter = new AllActivityRvAdapter();
    }

    public void initTabLayout(String[] titles) {
        TabLayout tabLayout = get(R.id.tl_title);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {

                } else {

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

    public void initAllActivityData(List<AllActivityServerParams> list1) {
        List<AllActivityServerParams> list = new ArrayList<>();
        AllActivityServerParams params = new AllActivityServerParams();
        params.setUrl("https://www.baidu.com/img/bd_logo1.png");
        params.setName("baidu");
        params.setBegin_time(System.currentTimeMillis() / 1000 + "");
        params.setEnd_time(System.currentTimeMillis() / 1000 + 3600000 + "");
        list.add(params);
        params = new AllActivityServerParams();
        params.setUrl("https://gss0.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/lbs/pic/item/7dd98d1001e939010f5ffd0672ec54e736d196ac.jpg");
        params.setName("baidu");
        params.setBegin_time(System.currentTimeMillis() / 1000 + "");
        params.setEnd_time(System.currentTimeMillis() / 1000 + 3600000 + "");
        list.add(params);
        params = new AllActivityServerParams();
        params.setUrl("https://www.baidu.com/img/bd_logo1.png");
        params.setName("baidu");
        params.setBegin_time(System.currentTimeMillis() / 1000 + "");
        params.setEnd_time(System.currentTimeMillis() / 1000 + 3600000 + "");
        list.add(params);
        params = new AllActivityServerParams();
        params.setUrl("https://gss0.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/lbs/pic/item/7dd98d1001e939010f5ffd0672ec54e736d196ac.jpg");
        params.setName("baidu");
        params.setBegin_time(System.currentTimeMillis() / 1000 + "");
        params.setEnd_time(System.currentTimeMillis() / 1000 + 3600000 + "");
        list.add(params);
        params = new AllActivityServerParams();
        params.setUrl("https://www.baidu.com/img/bd_logo1.png");
        params.setName("baidu");
        params.setBegin_time(System.currentTimeMillis() / 1000 + "");
        params.setEnd_time(System.currentTimeMillis() / 1000 + 3600000 + "");
        list.add(params);
        params = new AllActivityServerParams();
        params.setUrl("https://www.baidu.com/img/bd_logo1.png");
        params.setName("baidu");
        params.setBegin_time(System.currentTimeMillis() / 1000 + "");
        params.setEnd_time(System.currentTimeMillis() / 1000 + 3600000 + "");
        list.add(params);
        mAdapter.setData(list);
        mRvContent.setAdapter(mAdapter);
    }

    public void initMyActivityData(List<MyActivityServerParams> list1) {
        List<MyActivityServerParams> list = new ArrayList<>();
        MyActivityServerParams params = new MyActivityServerParams();
        params.setName("baidu");
        params.setSignUpEndTime(System.currentTimeMillis() / 1000 + "");
        params.setEndTime(System.currentTimeMillis() / 1000 + 3600000 + "");
        params.setStartTime(System.currentTimeMillis() / 1000 + "");
        list.add(params);
        params = new MyActivityServerParams();
        params.setName("baidu");
        params.setSignUpEndTime(System.currentTimeMillis() / 1000 + "");
        params.setEndTime(System.currentTimeMillis() / 1000 + 3600000 + "");
        params.setStartTime(System.currentTimeMillis() / 1000 + "");
        list.add(params);
        params = new MyActivityServerParams();
        params.setName("baidu");
        params.setSignUpEndTime(System.currentTimeMillis() / 1000 + "");
        params.setEndTime(System.currentTimeMillis() / 1000 + 3600000 + "");
        params.setStartTime(System.currentTimeMillis() / 1000 + "");
        list.add(params);
        params = new MyActivityServerParams();
        params.setName("baidu");
        params.setSignUpEndTime(System.currentTimeMillis() / 1000 + "");
        params.setEndTime(System.currentTimeMillis() / 1000 + 3600000 + "");
        params.setStartTime(System.currentTimeMillis() / 1000 + "");
        list.add(params);
        params = new MyActivityServerParams();
        params.setName("baidu");
        params.setSignUpEndTime(System.currentTimeMillis() / 1000 + "");
        params.setEndTime(System.currentTimeMillis() / 1000 + 3600000 + "");
        params.setStartTime(System.currentTimeMillis() / 1000 + "");
        list.add(params);
        params = new MyActivityServerParams();
        params.setName("baidu");
        params.setSignUpEndTime(System.currentTimeMillis() / 1000 + "");
        params.setEndTime(System.currentTimeMillis() / 1000 + 3600000 + "");
        params.setStartTime(System.currentTimeMillis() / 1000 + "");
        list.add(params);
//        mAdapter.setData(list);
        mRvContent.setAdapter(mAdapter);
    }

}
