package com.soubu.goldensteward.report;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.adapter.BaseRecyclerViewAdapter;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;
import com.soubu.goldensteward.module.TurnOverOrderRvItem;
import com.soubu.goldensteward.module.server.EvaluateInReturnRateServerParams;
import com.soubu.goldensteward.module.server.OrderServerParams;
import com.soubu.goldensteward.module.server.ProductInOrderListServerParams;
import com.soubu.goldensteward.module.server.VisitFriendsServerParams;
import com.soubu.goldensteward.server.RetrofitRequest;
import com.soubu.goldensteward.widget.recyclerviewdecoration.DividerItemDecoration;
import com.soubu.goldensteward.widget.linebarchart.LineView;
import com.soubu.goldensteward.widget.linebarchart.YAxisView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by dingsigang on 16-10-21.
 */
public class OperationReportSpecActivityDelegate extends AppDelegate {
    LineView mLineView;
    YAxisView mLeftAxisView;
    TextView mLastWeek;
    TextView mLastMonth;
    RecyclerView mRvContent;
    TextView mTvLabel;
    int mCurrentTabIndex = 0;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_operation_report_spec;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mLineView = get(R.id.line_view);
        mLeftAxisView = get(R.id.v_left_line);
        mLastWeek = get(R.id.tv_last_week);
        mLastMonth = get(R.id.tv_last_month);
        mRvContent = get(R.id.rv_content);
        mTvLabel = get(R.id.tv_label);
        mRvContent.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    public void setColorInfo(String title, String unit) {
        ((TextView) get(R.id.tv_color_info)).setText(title + "(" + unit + ")");
        mLineView.setUnit(unit);
    }

    public void refreshTabTitle(String[] titles) {
        TabLayout tabLayout = get(R.id.tl_title);
        tabLayout.setVisibility(View.VISIBLE);
        get(R.id.v_tab_layout_margin).setVisibility(View.VISIBLE);
        mTvLabel.setVisibility(View.GONE);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (mCurrentTabIndex != tab.getPosition()) {
                    mCurrentTabIndex = tab.getPosition();
                    RetrofitRequest.getInstance().getOrderList(tab.getPosition());
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

    public void setBarDataList(ArrayList<ArrayList<Integer>> list, int space, ArrayList<Integer> colorList,
                               ArrayList<ArrayList<String>> contentList) {
        mLineView.setBarDataList(list, mLeftAxisView, space, colorList, contentList);
    }

    public void setBottomTextList(ArrayList<Date> list ,String format) {
        mLineView.setBottomTextList(list, format);
    }

    public void setLineViewBottomSize(int size) {
        mLineView.setBottomTextSize(size);
        mLineView.requestLayout();
    }

    public void clickLastWeek() {
        mLastWeek.setBackgroundResource(R.drawable.bg_orange_stroke_corners);
        mLastWeek.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        mLastMonth.setBackgroundResource(R.drawable.bg_grey_stroke_corners);
        mLastMonth.setTextColor(getActivity().getResources().getColor(R.color.subtitle_grey));
    }

    public void clickLastMonth() {
        mLastMonth.setBackgroundResource(R.drawable.bg_orange_stroke_corners);
        mLastMonth.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        mLastWeek.setBackgroundResource(R.drawable.bg_grey_stroke_corners);
        mLastWeek.setTextColor(getActivity().getResources().getColor(R.color.subtitle_grey));
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

    public void initLabel(String label) {
        mTvLabel.setText(label);
    }

    public void initReturnRateView() {
        get(R.id.ll_top).setVisibility(View.INVISIBLE);
    }

    public void initStoreVisitorRecyclerView(VisitFriendsServerParams[] params) {
        StoreVisitorContactFriendsRvAdapter adapter = new StoreVisitorContactFriendsRvAdapter();
        List<VisitFriendsServerParams> list = Arrays.asList(params);
        adapter.setData(list);
        mRvContent.setAdapter(adapter);
    }

    public void initProductAccessRecyclerView(ProductInOrderListServerParams[] params) {
        ProductAccessProductsOnSaleRvAdapter adapter = new ProductAccessProductsOnSaleRvAdapter();
        List<ProductInOrderListServerParams> list = Arrays.asList(params);
        adapter.setData(list);
        mRvContent.setAdapter(adapter);
    }

    public void initReturnRateRecyclerView(List<EvaluateInReturnRateServerParams> list) {
        ReturnRateAllEvaluateRvAdapter adapter = new ReturnRateAllEvaluateRvAdapter();
//        List<ReturnRateAllEvaluateRvItem> list = new ArrayList<>();
//        ReturnRateAllEvaluateRvItem item = new ReturnRateAllEvaluateRvItem();
//        item.setUrls(new String[]{"http://img.isoubu.net/jgj/certification/1479263394496.jpg","http://img.isoubu.net/jgj/certification/1479263394496.jpg","http://img.isoubu.net/jgj/certification/1479263394496.jpg","http://img.isoubu.net/jgj/certification/1479263394496.jpg","http://img.isoubu.net/jgj/certification/1479263394496.jpg","http://img.isoubu.net/jgj/certification/1479263394496.jpg","http://img.isoubu.net/jgj/certification/1479263394496.jpg","http://img.isoubu.net/jgj/certification/1479263394496.jpg","http://img.isoubu.net/jgj/certification/1479263394496.jpg"});
//        list.add(item);
//        list.add(new ReturnRateAllEvaluateRvItem());
//        list.add(new ReturnRateAllEvaluateRvItem());
//        ReturnRateAllEvaluateRvItem item2 = new ReturnRateAllEvaluateRvItem();
//        item2.setUrls(new String[]{"http://img.isoubu.net/jgj/certification/1479263394496.jpg","http://img.isoubu.net/jgj/certification/1479263394496.jpg","http://img.isoubu.net/jgj/certification/1479263394496.jpg","http://img.isoubu.net/jgj/certification/1479263394496.jpg","http://img.isoubu.net/jgj/certification/1479263394496.jpg","http://img.isoubu.net/jgj/certification/1479263394496.jpg","http://img.isoubu.net/jgj/certification/1479263394496.jpg"});
//        list.add(item2);
//        list.add(new ReturnRateAllEvaluateRvItem());
//        list.add(new ReturnRateAllEvaluateRvItem());
//        list.add(new ReturnRateAllEvaluateRvItem());
//        list.add(new ReturnRateAllEvaluateRvItem());
        adapter.setData(list);
        mRvContent.setAdapter(adapter);
        mRvContent.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, 2));
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }
}
