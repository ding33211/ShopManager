package com.soubu.goldensteward.ui.storepreview;

import android.content.Intent;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.base.BaseRecyclerViewAdapter;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.support.bean.Constant;
import com.soubu.goldensteward.support.bean.server.ProductClassificationServerParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-11-25.
 */

public class ProductClassificationActivity extends ActivityPresenter<ProductClassificationActivityDelegate> {
    List<ProductClassificationServerParams> mList;

    @Override
    protected Class<ProductClassificationActivityDelegate> getDelegateClass() {
        return ProductClassificationActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.product_classification);
    }

    @Override
    protected void initData() {
        super.initData();
        mList = new ArrayList<>();
        ProductClassificationServerParams params = new ProductClassificationServerParams();
        params.setMain("春夏面料");
        params.setSpecProducts(new String[]{"春夏系列", "春夏系列", "春夏系列", "春夏系列", "春夏系列", "春夏系列", "春夏系列", "春夏系列", "春夏系列"});
        mList.add(params);
        params = new ProductClassificationServerParams();
        params.setMain("春夏面料");
        params.setSpecProducts(new String[]{"春夏系列", "春夏系列", "春夏系列", "春夏系列", "春夏系列", "春夏系列", "春夏系列", "春夏系列", "春夏系列"});
        mList.add(params);
        params = new ProductClassificationServerParams();
        params.setMain("春夏面料");
        mList.add(params);
        params = new ProductClassificationServerParams();
        params.setMain("春夏面料");
        mList.add(params);
        params = new ProductClassificationServerParams();
        params.setMain("春夏面料");
        params.setSpecProducts(new String[]{"春夏系列", "春夏系列", "春夏系列", "春夏系列", "春夏系列", "春夏系列", "春夏系列"});
        mList.add(params);
        viewDelegate.setData(mList);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnRvItemClickListener(new BaseRecyclerViewAdapter.OnRvItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(ProductClassificationActivity.this, StorePreviewActivity.class);
                intent.putExtra(Constant.EXTRA_FROM, StorePreviewActivity.FROM_PRODUCT_CLASSIFICATION);
                intent.putExtra(Constant.EXTRA_CONTENT, mList.get(position).getMain());
                startActivity(intent);
            }
        });
    }
}
