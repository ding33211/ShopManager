package com.soubu.goldensteward.delegate;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.adapter.ProductAccessProductsOnSaleRvAdapter;
import com.soubu.goldensteward.adapter.ReturnRateAllEvaluateRvAdapter;
import com.soubu.goldensteward.adapter.StoreVisitorContactFriendsRvAdapter;
import com.soubu.goldensteward.adapter.TurnOverOrderRvAdapter;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;
import com.soubu.goldensteward.module.ProductAccessProductsOnSaleRvItem;
import com.soubu.goldensteward.module.ReturnRateAllEvaluateRvItem;
import com.soubu.goldensteward.module.StoreVisitorContactFriendsRvItem;
import com.soubu.goldensteward.module.TurnOverOrderRvItem;
import com.soubu.goldensteward.widget.DividerItemDecoration;
import com.soubu.goldensteward.widget.linebarchart.LineView;
import com.soubu.goldensteward.widget.linebarchart.YAxisView;

import java.util.ArrayList;
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

    public void setBarDataList(ArrayList<ArrayList<Integer>> list, int space, ArrayList<Integer> colorList){
        mLineView.setBarDataList(list, mLeftAxisView, space, colorList);
    }

    public void setBottomTextList(ArrayList<String> list){
        mLineView.setBottomTextList(list);
    }

    public void clickLastWeek(){
        mLastWeek.setBackgroundResource(R.drawable.bg_orange_stroke_corners);
        mLastWeek.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        mLastMonth.setBackgroundResource(R.drawable.bg_grey_stroke_corners);
        mLastMonth.setTextColor(getActivity().getResources().getColor(R.color.subtitle_grey));
    }

    public void clickLastMonth(){
        mLastMonth.setBackgroundResource(R.drawable.bg_orange_stroke_corners);
        mLastMonth.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        mLastWeek.setBackgroundResource(R.drawable.bg_grey_stroke_corners);
        mLastWeek.setTextColor(getActivity().getResources().getColor(R.color.subtitle_grey));
    }

    public void initTurnOverVolumeRecyclerView(String[] titles){
//        ViewPager viewPager = get(R.id.vp_content);
        TabLayout tabLayout = get(R.id.tl_title);
        tabLayout.setVisibility(View.VISIBLE);
        get(R.id.v_tab_layout_margin).setVisibility(View.VISIBLE);
        mTvLabel.setVisibility(View.GONE);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        for(String a : titles){
            tabLayout.addTab(tabLayout.newTab().setText(a));
        }
//        TitleFragmentPagerAdapter adapter = new TitleFragmentPagerAdapter(getActivity().getSupportFragmentManager(),
//                fragmentList, titles);
//        viewPager.setAdapter(adapter);
        final TurnOverOrderRvAdapter adapter = new TurnOverOrderRvAdapter();
        List<TurnOverOrderRvItem> list = new ArrayList<>();
        list.add(new TurnOverOrderRvItem());
        list.add(new TurnOverOrderRvItem());
        list.add(new TurnOverOrderRvItem());
        list.add(new TurnOverOrderRvItem());
        list.add(new TurnOverOrderRvItem());
        list.add(new TurnOverOrderRvItem());
        adapter.setData(list);
        mRvContent.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        mRvContent.setAdapter(adapter);
                        break;
                    case 1:
                        mRvContent.setAdapter(adapter);
                        break;
                    case 2:
                        mRvContent.setAdapter(adapter);
                        break;
                    case 3:
                        mRvContent.setAdapter(adapter);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//        tabLayout.setupWithViewPager(viewPager);
    }


    public void initStoreVisitorRecyclerView(){
        mTvLabel.setText(R.string.contact_friends);
        StoreVisitorContactFriendsRvAdapter adapter = new StoreVisitorContactFriendsRvAdapter();
        List<StoreVisitorContactFriendsRvItem> list = new ArrayList<>();
        list.add(new StoreVisitorContactFriendsRvItem());
        list.add(new StoreVisitorContactFriendsRvItem());
        list.add(new StoreVisitorContactFriendsRvItem());
        list.add(new StoreVisitorContactFriendsRvItem());
        list.add(new StoreVisitorContactFriendsRvItem());
        list.add(new StoreVisitorContactFriendsRvItem());
        list.add(new StoreVisitorContactFriendsRvItem());
        adapter.setData(list);
        mRvContent.setAdapter(adapter);
    }

    public void initProductAccessRecyclerView(){
        mTvLabel.setText(R.string.products_on_sale);
        ProductAccessProductsOnSaleRvAdapter adapter = new ProductAccessProductsOnSaleRvAdapter();
        List<ProductAccessProductsOnSaleRvItem> list = new ArrayList<>();
        list.add(new ProductAccessProductsOnSaleRvItem());
        list.add(new ProductAccessProductsOnSaleRvItem());
        list.add(new ProductAccessProductsOnSaleRvItem());
        list.add(new ProductAccessProductsOnSaleRvItem());
        list.add(new ProductAccessProductsOnSaleRvItem());
        list.add(new ProductAccessProductsOnSaleRvItem());
        list.add(new ProductAccessProductsOnSaleRvItem());
        adapter.setData(list);
        mRvContent.setAdapter(adapter);
    }

    public void initReturnRateRecyclerView(){
        mTvLabel.setText(R.string.all_evaluate);
        ReturnRateAllEvaluateRvAdapter adapter = new ReturnRateAllEvaluateRvAdapter();
        List<ReturnRateAllEvaluateRvItem> list = new ArrayList<>();
        list.add(new ReturnRateAllEvaluateRvItem());
        list.add(new ReturnRateAllEvaluateRvItem());
        list.add(new ReturnRateAllEvaluateRvItem());
        list.add(new ReturnRateAllEvaluateRvItem());
        list.add(new ReturnRateAllEvaluateRvItem());
        list.add(new ReturnRateAllEvaluateRvItem());
        list.add(new ReturnRateAllEvaluateRvItem());
        adapter.setData(list);
        mRvContent.setAdapter(adapter);
        mRvContent.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, 2));
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }
}
