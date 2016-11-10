package com.soubu.goldensteward.delegate;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.adapter.TransactionRecordRvAdapter;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;
import com.soubu.goldensteward.module.server.CustomerServerParams;
import com.soubu.goldensteward.module.server.ProductInCustomerDetailServerParams;
import com.soubu.goldensteward.utils.GlideUtils;
import com.soubu.goldensteward.widget.DividerItemDecoration;

import java.util.List;

/**
 * Created by lakers on 16/10/31.
 */

public class CustomerSpecActivityDelegate extends AppDelegate {
    TransactionRecordRvAdapter mAdapter;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_customer_spec;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mAdapter = new TransactionRecordRvAdapter(getActivity());
        RecyclerView recyclerView = get(R.id.rv_content);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(), LinearLayoutManager.VERTICAL, 40));
    }

    public void setData(List<ProductInCustomerDetailServerParams> list) {
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }

    public void initCustomerInfo(CustomerServerParams params){
        GlideUtils.loadRoundedImage(getActivity(), (ImageView)get(R.id.iv_customer_avatar), params.getPortrait(), R.drawable.common_header, R.drawable.common_header);
        ((TextView)get(R.id.tv_name)).setText(params.getName());
        ((TextView)get(R.id.tv_take_name)).setText(params.getTake_person());
        ((TextView)get(R.id.tv_phone)).setText(params.getContact_phone());
        ((TextView)get(R.id.tv_address)).setText(params.getAddress());
        ((TextView) get(R.id.tv_traded_order_count)).setText(params.getOrder_count());
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }
}
