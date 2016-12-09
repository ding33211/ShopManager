package com.soubu.goldensteward.ui.report;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.adapter.SingleAdapter;
import com.soubu.goldensteward.support.mvp.view.AppDelegate;
import com.soubu.goldensteward.support.bean.server.ProductInOrderListServerParams;
import com.soubu.goldensteward.support.bean.server.StoreVisitorServerParams;
import com.soubu.goldensteward.support.net.RetrofitRequest;
import com.soubu.goldensteward.support.utils.ConvertUtil;
import com.soubu.goldensteward.support.utils.GlideUtils;

import java.util.Arrays;
import java.util.Date;
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
        SingleAdapter adapter = new SingleAdapter<ProductInOrderListServerParams>(getActivity(), R.layout.item_old_product_maybe_delete_soon) {
            @Override
            protected void bindData(BaseViewHolder holder, ProductInOrderListServerParams item, int position) {
                ImageView ivProductImg = holder.getView(R.id.iv_product);
                TextView tvProductName = holder.getView(R.id.tv_name);
                TextView tvBrowse = holder.getView(R.id.tv_browser_volume);
                TextView tvCollection = holder.getView(R.id.tv_collection_volume);
                TextView tvUnit = holder.getView(R.id.tv_unit);
                TextView tvUnitPrice = holder.getView(R.id.tv_unit_price);
                TextView tvTime = holder.getView(R.id.tv_time);
                TextView tvCustomerService = holder.getView(R.id.tv_customer_service);
                GlideUtils.loadRoundedImage(ivProductImg.getContext(), ivProductImg, item.getPic(), R.drawable.common_product_placeholder, R.drawable.common_product_placeholder);
                tvProductName.setText(item.getTitle());
                tvUnitPrice.setText(item.getPrice());
                tvUnit.setText(item.getUnit());
                tvTime.setText(ConvertUtil.dateToYYYY_MM_DD(new Date(Long.valueOf(item.getTime()) * 1000)));
                tvBrowse.setText(item.getVisit());
                tvCollection.setText(item.getCollection());
            }

        };
        List<ProductInOrderListServerParams> list = Arrays.asList(params);
        adapter.setData(list);
        mRvContent.setAdapter(adapter);
    }


    public void initProductAccessRecyclerView(StoreVisitorServerParams[] params) {
        SingleAdapter adapter = new SingleAdapter<StoreVisitorServerParams>(getActivity(), R.layout.item_store_visitor_recyclerview) {
            @Override
            protected void bindData(BaseViewHolder holder, StoreVisitorServerParams item, int position) {
                View vBottom = holder.getView(R.id.v_bottom_line);
                TextView tvName = holder.getView(R.id.tv_name);
                TextView tvBrowse = holder.getView(R.id.tv_browse_num);
                TextView tvVisit = holder.getView(R.id.tv_store_visit_num);
                ImageView ivStore = holder.getView(R.id.iv_store);
                vBottom.setVisibility(View.VISIBLE);
                if (position == getItemCount() - 1) {
                    vBottom.setVisibility(View.GONE);
                }
                tvName.setText(item.getName());
                tvBrowse.setText(item.getBrowse());
                tvVisit.setText(item.getVisit());
                GlideUtils.loadImage(ivStore.getContext(), ivStore, item.getUrl(), R.drawable.common_product_placeholder, R.drawable.common_product_placeholder);


            }

        };
        List<StoreVisitorServerParams> list = Arrays.asList(params);
        adapter.setData(list);
        mRvContent.setAdapter(adapter);
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }
}
