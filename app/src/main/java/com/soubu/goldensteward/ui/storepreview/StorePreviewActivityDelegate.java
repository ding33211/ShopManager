package com.soubu.goldensteward.ui.storepreview;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.adapter.SingleAdapter;
import com.soubu.goldensteward.support.bean.server.ProductPreviewSeverParams;
import com.soubu.goldensteward.support.mvp.view.AppDelegate;
import com.soubu.goldensteward.support.utils.ConvertUtil;
import com.soubu.goldensteward.support.utils.GlideUtils;
import com.soubu.goldensteward.support.widget.recyclerviewdecoration.DividerGridItemDecoration;

import java.util.List;

/**
 * Created by dingsigang on 16-11-25.
 */

public class StorePreviewActivityDelegate extends AppDelegate {
    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    int mCurrentTabIndex = 0;
    RecyclerView mRvContent;
    SingleAdapter mAdapter;
    View mVFilter;
    View mLatestSeven;
    View[] mViews;
    FloatingActionButton mFabUp;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_store_preview;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mRvContent = get(R.id.rv_content);
        mVFilter = get(R.id.ll_filter);
        mFabUp = get(R.id.fab_up);
        mLatestSeven = get(R.id.tv_latest_7_new);
        mRvContent.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRvContent.addItemDecoration(new DividerGridItemDecoration(ConvertUtil.dip2px(getActivity(), 8), 2));
        mAdapter = new SingleAdapter<ProductPreviewSeverParams>(getActivity(), R.layout.item_product_preview_grid_recyclerview) {
            @Override
            protected void bindData(BaseViewHolder holder, ProductPreviewSeverParams item, int position) {
                ImageView ivProduct = holder.getView(R.id.iv_product);
                TextView tvTitle = holder.getView(R.id.tv_title);
                TextView tvPrice = holder.getView(R.id.tv_price);
                TextView tvBrowserCount = holder.getView(R.id.tv_browser_count);
                View vRecommend = holder.getView(R.id.iv_recommend);
                GlideUtils.loadImage(ivProduct.getContext(), ivProduct, item.getUrl(), R.drawable.common_product_placeholder, R.drawable.common_product_placeholder);
                tvTitle.setText(item.getTitle());
                tvPrice.setText(item.getPrice());
                tvBrowserCount.setText(item.getBrowser_volume());
            }

        };
        mRvContent.setAdapter(mAdapter);
        View vComplex = get(R.id.bt_complex);
        View vNewProduct = get(R.id.bt_new_product);
        View vPrice = get(R.id.ib_price);
        mViews = new View[]{vComplex, vNewProduct, vPrice};
    }

    public void setSelected(int index) {
        for (int i = 0; i < mViews.length; i++) {
            if (i == index) {
                mViews[i].setSelected(true);
            } else {
                mViews[i].setSelected(false);
            }
        }
    }

    public void initFromProductClassification() {
        get(R.id.rl_top).setVisibility(View.GONE);
        get(R.id.tl_title).setVisibility(View.GONE);
        get(R.id.tv_product_classification).setVisibility(View.GONE);
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
                    if (mCurrentTabIndex != 1) {
                        mVFilter.setVisibility(View.GONE);
                    }
                    switch (mCurrentTabIndex) {
                        case 3:
                            mVFilter.setVisibility(View.GONE);
                            mLatestSeven.setVisibility(View.GONE);
                            Intent intent = new Intent(getActivity(), EvaluateInStorePreviewActivity.class);
                            getActivity().startActivity(intent);
                            break;
                        case 1:
                            mVFilter.setVisibility(View.VISIBLE);
                            mLatestSeven.setVisibility(View.GONE);
                            break;
                        case 2:
                            mVFilter.setVisibility(View.GONE);
                            mLatestSeven.setVisibility(View.VISIBLE);
                            break;
                        case 0:
                            mVFilter.setVisibility(View.GONE);
                            mLatestSeven.setVisibility(View.GONE);
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

    public void setData(List<ProductPreviewSeverParams> list) {
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }

    public void clickFab() {
        mRvContent.scrollToPosition(0);
        ViewCompat.animate(mFabUp).scaleX(0.0F).scaleY(0.0F).alpha(0.0F).setInterpolator(INTERPOLATOR).withLayer().start();
    }
}
