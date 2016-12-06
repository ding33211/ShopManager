package com.soubu.goldensteward.ui.report;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.mvp.view.AppDelegate;
import com.soubu.goldensteward.support.bean.server.ProductInOrderListServerParams;
import com.soubu.goldensteward.support.bean.server.StoreVisitorServerParams;
import com.soubu.goldensteward.support.net.RetrofitRequest;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dingsigang on 16-11-30.
 */

public class StoreFlowStatisticsActivityDelegate extends AppDelegate {
    int mCurrentTabIndex = 0;
    RecyclerView mRvContent;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_store_flow_statistics;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        View vLastWeek = get(R.id.bt_last_week);
        View vLastMonth = get(R.id.bt_last_month);
        mRvContent = get(R.id.rv_content);
        mRvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
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
                            RetrofitRequest.getInstance().getProductListOnSale();
                            break;
                        case 1:
                            StoreVisitorServerParams params = new StoreVisitorServerParams();
                            params.setBrowse("256");
                            params.setName("烟台纺织品有限公司");
                            params.setVisit("333");
                            params.setUrl("https://img.alicdn.com/bao/uploadedi1/2135772166/TB2wxx7aXXXXXaTXpXXXXXXXXXX_!!2135772166.jpg_290x290xz.jpg");
                            StoreVisitorServerParams params1 = new StoreVisitorServerParams();
                            params1.setBrowse("256");
                            params1.setName("烟台纺织品有限公司");
                            params1.setVisit("333");
                            params1.setUrl("https://img.alicdn.com/bao/uploadedi1/2135772166/TB2wxx7aXXXXXaTXpXXXXXXXXXX_!!2135772166.jpg_290x290xz.jpg");
                            StoreVisitorServerParams params2 = new StoreVisitorServerParams();
                            params2.setBrowse("256");
                            params2.setName("烟台纺织品有限公司");
                            params2.setVisit("333");
                            params2.setUrl("https://img.alicdn.com/bao/uploadedi1/2135772166/TB2wxx7aXXXXXaTXpXXXXXXXXXX_!!2135772166.jpg_290x290xz.jpg");
                            StoreVisitorServerParams params3 = new StoreVisitorServerParams();
                            params3.setBrowse("256");
                            params3.setName("烟台纺织品有限公司");
                            params3.setVisit("333");
                            params3.setUrl("https://img.alicdn.com/bao/uploadedi1/2135772166/TB2wxx7aXXXXXaTXpXXXXXXXXXX_!!2135772166.jpg_290x290xz.jpg");
                            StoreVisitorServerParams[] arrays = new StoreVisitorServerParams[]{params, params1, params2, params3};
                            initProductAccessRecyclerView(arrays);
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

    public void initProductAccessRecyclerView(ProductInOrderListServerParams[] params) {
        ProductAccessProductsOnSaleRvAdapter adapter = new ProductAccessProductsOnSaleRvAdapter();
        List<ProductInOrderListServerParams> list = Arrays.asList(params);
        adapter.setData(list);
        mRvContent.setAdapter(adapter);
    }


    public void initProductAccessRecyclerView(StoreVisitorServerParams[] params) {
        StoreVisitorRvAdapter adapter = new StoreVisitorRvAdapter();
        List<StoreVisitorServerParams> list = Arrays.asList(params);
        adapter.setData(list);
        mRvContent.setAdapter(adapter);
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }
}
