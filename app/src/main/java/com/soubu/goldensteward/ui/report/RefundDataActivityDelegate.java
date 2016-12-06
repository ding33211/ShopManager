package com.soubu.goldensteward.ui.report;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.base.BaseRecyclerViewAdapter;
import com.soubu.goldensteward.support.mvp.view.AppDelegate;
import com.soubu.goldensteward.support.bean.TurnOverOrderRvItem;
import com.soubu.goldensteward.support.bean.server.OrderServerParams;
import com.soubu.goldensteward.support.widget.linebarchart.LineView;
import com.soubu.goldensteward.support.widget.linebarchart.YAxisView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dingsigang on 16-11-30.
 */

public class RefundDataActivityDelegate extends AppDelegate {
    LineView mLineView;
    YAxisView mLeftAxisView;
    RecyclerView mRvContent;

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
        mRvContent = get(R.id.rv_content);
        mRvContent.setLayoutManager(new LinearLayoutManager(getActivity()));

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

    public void initTurnOverVolumeRecyclerView(OrderServerParams[] params) {
        TurnOverOrderRvAdapter mAdapter = new TurnOverOrderRvAdapter(getActivity());
        List<TurnOverOrderRvItem> list = new ArrayList<>();
        for (OrderServerParams param : params) {
            TurnOverOrderRvItem item = new TurnOverOrderRvItem();
            item.setStatus(param.getStatus());
            item.setTime(param.getAdd_time());
            item.setCity(param.getCity());
            item.setProvince(param.getProvince());
            item.setDiscount(param.getDiscount());
            item.setSum_price(param.getTotal_price());
            item.setFreight(param.getShip_type());
            item.setName(param.getName());
            item.setPhone(param.getPhone());
            item.setPic(param.getPic());
            item.setSec_status(param.getSec_status());
            item.setType(param.getOrder_type());
            item.setConsignee(param.getConsignee());
            item.setRvType(BaseRecyclerViewAdapter.TYPE_ONLY);
            list.add(item);
        }
        mAdapter.setData(list);
        mRvContent.setAdapter(mAdapter);
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
