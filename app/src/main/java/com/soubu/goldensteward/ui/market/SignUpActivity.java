package com.soubu.goldensteward.ui.market;

import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.adapter.SingleAdapter;
import com.soubu.goldensteward.support.base.BaseApplication;
import com.soubu.goldensteward.support.bean.server.ProductInSignUpActivityServerParams;
import com.soubu.goldensteward.support.bean.server.SignUpServerParams;
import com.soubu.goldensteward.support.constant.IntentKey;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.support.utils.GlideUtils;
import com.soubu.goldensteward.support.web.core.BaseResponse;
import com.soubu.goldensteward.support.web.core.BaseSubscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dingsigang on 16-12-6.
 */

public class SignUpActivity extends ActivityPresenter<SignUpActivityDelegate> {
    private List<String> mCheckedProductId;
    private String mAccountId;
    private int mActivityId;


    @Override
    protected Class<SignUpActivityDelegate> getDelegateClass() {
        return SignUpActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.activity_sign_up);
    }

    @Override
    protected void initData() {
        super.initData();
        mCheckedProductId = new ArrayList<>();
        mAccountId = getIntent().getStringExtra(IntentKey.EXTRA_ACCOUNT_ID);
        mActivityId = getIntent().getIntExtra(IntentKey.EXTRA_ACTIVITY_ID, -1);
        if (!TextUtils.isEmpty(mAccountId)) {
            Map<String, String> map = new HashMap<>();
            map.put("id", mAccountId);
            BaseApplication.getWebModel().getProductListInActivity(map).sendTo(new BaseSubscriber<BaseResponse<List<ProductInSignUpActivityServerParams>>>(this) {
                @Override
                public void onSuccess(BaseResponse<List<ProductInSignUpActivityServerParams>> response) {
                    viewDelegate.initProduct(response.getResult().getData());
                }
            });
            SingleAdapter adapter = new SingleAdapter<ProductInSignUpActivityServerParams>(this, R.layout.item_product_access_product_on_sale_recyclerview) {
                @Override
                protected void bindData(BaseViewHolder holder, ProductInSignUpActivityServerParams item, int position) {
                    View vBottom1 = holder.getView(R.id.ll_bottom);
                    vBottom1.setVisibility(View.GONE);
                    CheckBox cbChoose = holder.getView(R.id.cb_choose);
                    ImageView ivProductImg = holder.getView(R.id.iv_product);
                    TextView tvProductName = holder.getView(R.id.tv_name);
                    TextView tvSamplePrice = holder.getView(R.id.tv_sample_card_price);
                    TextView tvBigGoodsPrice = holder.getView(R.id.tv_big_goods_price);
                    TextView tvSampleUnit = holder.getView(R.id.tv_sample_unit);
                    TextView tvBigGoodsUnit = holder.getView(R.id.tv_big_goods_unit);
                    GlideUtils.loadRoundedImage(ivProductImg.getContext(), ivProductImg, item.getCover(), R.drawable.common_product_placeholder, R.drawable.common_product_placeholder);
                    tvProductName.setText(item.getName());
                    tvSamplePrice.setText(item.getCut_price());
                    tvBigGoodsPrice.setText(item.getPrice());
                    tvSampleUnit.setText(item.getCut_units());
                    tvBigGoodsUnit.setText(item.getUnit());
                    if (mCheckedProductId.contains(item.getPro_id())) {
                        cbChoose.setChecked(true);
                    } else {
                        cbChoose.setChecked(false);
                    }
                }

                @Override
                public void onItemClick(BaseViewHolder holder, ProductInSignUpActivityServerParams item, int position) {
                    CheckBox cbChoose = holder.getView(R.id.cb_choose);
                    if (cbChoose.isChecked()) {
                        cbChoose.setChecked(false);
                    } else {
                        cbChoose.setChecked(true);
                    }
                    String id = item.getPro_id();
                    if (!mCheckedProductId.contains(id)) {
                        mCheckedProductId.add(id);
                    } else {
                        mCheckedProductId.remove(id);
                    }
                    viewDelegate.refreshTotalText(mCheckedProductId.size());
                }
            };
            viewDelegate.setProductAdapter(adapter);
        }

    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_commit:
                        SignUpServerParams params = new SignUpServerParams();
                        params.setUid(mAccountId);
                        params.setActive_id(mActivityId + "");
                        params.setProduct_list(JSON.toJSONString(mCheckedProductId));
                        BaseApplication.getWebModel().signUp(params).sendTo(new BaseSubscriber<BaseResponse>(SignUpActivity.this) {
                            @Override
                            public void onSuccess(BaseResponse response) {
                                viewDelegate.onCommitSuccess();
                            }
                        });
                        break;
                    case R.id.bt_return:
                        setResult(RESULT_OK);
                        finish();
                        break;
                }
            }
        }, R.id.tv_commit, R.id.bt_return);
    }
}
