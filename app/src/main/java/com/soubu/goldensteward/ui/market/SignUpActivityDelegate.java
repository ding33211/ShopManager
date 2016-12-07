package com.soubu.goldensteward.ui.market;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.base.BaseRecyclerViewAdapter;
import com.soubu.goldensteward.support.bean.server.ProductInOrderListServerParams;
import com.soubu.goldensteward.support.mvp.view.AppDelegate;
import com.soubu.goldensteward.support.utils.ConvertUtil;
import com.soubu.goldensteward.support.widget.recyclerviewdecoration.DividerItemDecoration;
import com.soubu.goldensteward.ui.report.ProductAccessProductsOnSaleRvAdapter;

import java.util.List;

/**
 * Created by dingsigang on 16-12-6.
 */

public class SignUpActivityDelegate extends AppDelegate {
    RecyclerView mRvContent;
    ProductAccessProductsOnSaleRvAdapter mAdapter;
    TextView mTvTotal;
    FrameLayout mFmAll;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_sign_up;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mRvContent = get(R.id.rv_content);
        mTvTotal = get(R.id.tv_total);
        mFmAll = get(R.id.fm_all);
        mAdapter = new ProductAccessProductsOnSaleRvAdapter(ProductAccessProductsOnSaleRvAdapter.PRODUCT_CHOOSE_SIGN_UP);
        mRvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvContent.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, ConvertUtil.dip2px(getActivity(), 10)));
        mRvContent.setAdapter(mAdapter);
    }

    public void initProduct(List<ProductInOrderListServerParams> list) {
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }

    public void setOnProductItemClickListener(BaseRecyclerViewAdapter.OnRvItemClickListener listener) {
        mAdapter.setOnRvItemClickListener(listener);
    }

    public void onCommitSuccess() {
        //注意此处不要使用get的方式去setVisibility，需要借助从layout去getchildat去获得view再去set，直接使用get的方法是通过rootview的方式去做的
        View v = mFmAll.getChildAt(2);
        v.setVisibility(View.GONE);
        View v1 = mFmAll.getChildAt(1);
        v1.setVisibility(View.VISIBLE);
        setTitle(R.string.sign_up_success);
    }

    public void onServerError() {
        View v = mFmAll.getChildAt(2);
        v.setVisibility(View.GONE);
        View v0 = mFmAll.getChildAt(0);
        v0.setVisibility(View.VISIBLE);
    }

    public void onProductEmpty() {
        View v = mFmAll.getChildAt(2);
        v.setVisibility(View.GONE);
        View v0 = mFmAll.getChildAt(0);
        v0.setVisibility(View.VISIBLE);
        ImageView ivError = get(R.id.iv_error);
        ivError.setImageResource(R.drawable.sign_up_empty);
        TextView tvError = get(R.id.tv_error_desc);
        tvError.setText(R.string.sign_up_empty_product_desc);
    }

    public void refreshTotalText(int total) {
        mTvTotal.setText(total + "");
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }
}
