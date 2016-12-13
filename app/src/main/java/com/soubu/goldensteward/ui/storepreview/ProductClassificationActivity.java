package com.soubu.goldensteward.ui.storepreview;

import android.content.Intent;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.adapter.SingleAdapter;
import com.soubu.goldensteward.support.constant.Constant;
import com.soubu.goldensteward.support.bean.server.ProductClassificationServerParams;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;

import java.util.ArrayList;
import java.util.Arrays;
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
        SingleAdapter adapter = new SingleAdapter<ProductClassificationServerParams>(this, R.layout.item_product_classification_recyclerview) {
            @Override
            protected void bindData(BaseViewHolder holder, ProductClassificationServerParams item, int position) {
                View gvSub = holder.getView(R.id.gv_container);
                View tvViewAll = holder.getView(R.id.tv_view_all);
                View vChoose = holder.getView(R.id.iv_choose);
                TextView tvTitle = holder.getView(R.id.tv_main_category);
                GridView gvSubGroup = holder.getView(R.id.gv_container);
                tvTitle.setText(item.getMain());
                if (item.getSpecProducts() != null) {
                    gvSub.setVisibility(View.VISIBLE);
                    tvViewAll.setVisibility(View.VISIBLE);
                    vChoose.setVisibility(View.GONE);
                    String[] titles = item.getSpecProducts();
                    ProductClassificationGridViewAdapter adapter = new ProductClassificationGridViewAdapter(tvTitle.getContext(), Arrays.asList(titles));
                    gvSubGroup.setAdapter(adapter);
                } else {
                    gvSub.setVisibility(View.GONE);
                }
            }

            @Override
            public void onItemClick(BaseViewHolder holder, ProductClassificationServerParams item, int position) {
                Intent intent = new Intent(ProductClassificationActivity.this, StorePreviewActivity.class);
                intent.putExtra(Constant.EXTRA_FROM, StorePreviewActivity.FROM_PRODUCT_CLASSIFICATION);
                intent.putExtra(Constant.EXTRA_CONTENT, item.getMain());
                startActivity(intent);
            }
        };
        viewDelegate.setProductClassificationRvAdapter(adapter);
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

}
