package com.soubu.goldensteward.ui.customer;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.adapter.FooterSingleAdapter;
import com.soubu.goldensteward.support.delegate.BaseWithFootOrRefreshRecyclerViewDelegate;
import com.soubu.goldensteward.support.utils.ConvertUtil;
import com.soubu.goldensteward.ui.report.TransactionRecordRvAdapter;
import com.soubu.goldensteward.support.bean.server.CustomerServerParams;
import com.soubu.goldensteward.support.bean.server.ProductInCustomerDetailServerParams;
import com.soubu.goldensteward.support.utils.GlideUtils;
import com.soubu.goldensteward.support.widget.recyclerviewdecoration.DividerItemDecoration;

import java.util.Date;
import java.util.List;

/**
 * Created by lakers on 16/10/31.
 */

public class CustomerSpecActivityDelegate extends BaseWithFootOrRefreshRecyclerViewDelegate {
    private String[] mStates;


    @Override
    public int getRootLayoutId() {
        return R.layout.activity_customer_spec;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mStates = getActivity().getResources().getStringArray(R.array.order_state);
        mAdapter = new FooterSingleAdapter<ProductInCustomerDetailServerParams>(getActivity(), R.layout.item_transaction_record_recyclerview, R.layout.item_recyclerview_footer) {
            @Override
            protected void bindData(BaseViewHolder holder, ProductInCustomerDetailServerParams item, int position) {
                TextView tvState = holder.getView(R.id.tv_state);
                TextView tvTime = holder.getView(R.id.tv_time);
                TextView tvPrice = holder.getView(R.id.tv_price);
                ImageView ivProduct = holder.getView(R.id.iv_product);
                TextView tvProduct = holder.getView(R.id.tv_product_name);
                tvState.setText(mStates[Integer.valueOf(item.getStatus()) - 1]);
                tvTime.setText(item.getAdd_time() == null ? "" : ConvertUtil.dateToYYYY_MM_DD_HH_mm_ss(new Date(Long.valueOf(item.getAdd_time()) * 1000)));
                tvPrice.setText(item.getPrice());
                GlideUtils.loadRoundedImage(ivProduct.getContext(), ivProduct, item.getPic(), R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                tvProduct.setText(item.getPname());
            }
        };
//        mAdapter = new TransactionRecordRvAdapter(getActivity());
        mRvContent = get(R.id.rv_content);
        mRvContent.setAdapter(mAdapter);
        mManager = new LinearLayoutManager(getActivity());
        mRvContent.setLayoutManager(mManager);
        mRvContent.addItemDecoration(new DividerItemDecoration(this.getActivity(), LinearLayoutManager.VERTICAL, 40));
    }

    public void setData(List<ProductInCustomerDetailServerParams> list, boolean mIsRefresh) {
        mAdapter.setData(list, mIsRefresh);
        mAdapter.notifyDataSetChanged();
    }

    public void initCustomerInfo(CustomerServerParams params) {
        GlideUtils.loadRoundedImage(getActivity(), (ImageView) get(R.id.iv_customer_avatar), params.getPortrait(), R.drawable.common_header, R.drawable.common_header);
        ((TextView) get(R.id.tv_name)).setText(params.getName());
        ((TextView) get(R.id.tv_take_name)).setText(params.getTake_person());
        ((TextView) get(R.id.tv_phone)).setText(params.getContact_phone());
        ((TextView) get(R.id.tv_address)).setText(params.getAddress());
        ((TextView) get(R.id.tv_traded_order_count)).setText(params.getOrder_count());
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }
}
