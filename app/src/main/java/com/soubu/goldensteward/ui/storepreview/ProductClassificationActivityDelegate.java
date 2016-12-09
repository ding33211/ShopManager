package com.soubu.goldensteward.ui.storepreview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.SingleAdapter;
import com.soubu.goldensteward.support.bean.server.ProductClassificationServerParams;
import com.soubu.goldensteward.support.mvp.view.AppDelegate;

import java.util.List;

/**
 * Created by dingsigang on 16-11-25.
 */

public class ProductClassificationActivityDelegate extends AppDelegate {
    RecyclerView mRvContent;
    SingleAdapter mAdapter;

    @Override
    public void initWidget() {
        super.initWidget();
        mRvContent = get(R.id.rv_content);
        mRvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_product_classification;
    }

    public void setData(List<ProductClassificationServerParams> list) {
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }

    public void setProductClassificationRvAdapter(SingleAdapter adapter){
        mAdapter = adapter;
        mRvContent.setAdapter(mAdapter);
    }
}
