package com.soubu.goldensteward.ui.market;

import android.content.res.Resources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.bean.server.ProductInOrderListServerParams;
import com.soubu.goldensteward.support.mvp.view.AppDelegate;
import com.soubu.goldensteward.support.utils.ConvertUtil;
import com.soubu.goldensteward.support.widget.recyclerviewdecoration.DividerItemDecoration;
import com.soubu.goldensteward.ui.report.ProductAccessProductsOnSaleRvAdapter;

import java.util.List;

/**
 * Created by dingsigang on 16-12-7.
 */

public class SignUpSpecActivityDelegate extends AppDelegate {
    RecyclerView mRvContent;
    ProductAccessProductsOnSaleRvAdapter mAdapter;

    @Override
    public void initWidget() {
        super.initWidget();
        mRvContent = get(R.id.rv_content);
        mRvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ProductAccessProductsOnSaleRvAdapter(ProductAccessProductsOnSaleRvAdapter.PRODUCT_HAVE_SIGNED_UP);
        mRvContent.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, ConvertUtil.dip2px(getActivity(), 10)));
        mRvContent.setAdapter(mAdapter);
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_sign_up_spec;
    }

    public void initProduct(List<ProductInOrderListServerParams> list) {
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }

    /**
     * 刷新报名状态
     *
     * @param type 0表示正在审核，1表示审核通过，2表示审核失败
     */
    public void refreshSignUpState(int type, int productCount, String failReason) {
        ImageView ivState = get(R.id.iv_appeal_state);
        TextView tvStep3 = get(R.id.tv_step3);
        View vLine2 = get(R.id.v_line_2);
        TextView tvStep3Num = get(R.id.tv_step3_num);
        TextView tvAppealState = get(R.id.tv_appeal_state);
        TextView tvAppealStatePost = get(R.id.tv_appeal_state_post);
        View vNormal = get(R.id.ll_appeal_state_normal);
        View vFail = get(R.id.ll_appeal_fail);
        TextView tvFailReason = get(R.id.tv_fail_reason);
        TextView tvProductCount = get(R.id.tv_product_count);
        tvProductCount.setText(productCount + "");
        if (type == 1 || type == 2) {
            Resources res = getActivity().getResources();
            tvStep3.setTextColor(res.getColor(R.color.colorPrimary));
            vLine2.setBackgroundResource(R.color.colorPrimary);
            tvStep3Num.setBackgroundResource(R.drawable.bg_orange_circle);
            if (type == 1) {
                ivState.setImageResource(R.drawable.sign_up_appeal_success);
                tvAppealState.setText(R.string.sign_up_appeal_success);
                tvAppealState.setTextColor(res.getColor(R.color.green_sign_up_product));
                tvAppealStatePost.setText(R.string.appeal_success_desc_post);
            } else {
                ivState.setImageResource(R.drawable.sign_up_appeal_fail);
                tvAppealState.setText(R.string.sign_up_appeal_fail);
                tvAppealState.setTextColor(res.getColor(R.color.red_sign_up_product));
                vNormal.setVisibility(View.GONE);
                vFail.setVisibility(View.VISIBLE);
                tvFailReason.setText(failReason);
                get(R.id.ll_sign_up_again).setVisibility(View.VISIBLE);
            }
        }
    }
}
