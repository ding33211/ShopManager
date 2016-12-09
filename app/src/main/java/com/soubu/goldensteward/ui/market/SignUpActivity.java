package com.soubu.goldensteward.ui.market;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.adapter.SingleAdapter;
import com.soubu.goldensteward.support.bean.BaseEventBusResp;
import com.soubu.goldensteward.support.bean.EventBusConfig;
import com.soubu.goldensteward.support.bean.server.BaseResp;
import com.soubu.goldensteward.support.bean.server.ProductInOrderListServerParams;
import com.soubu.goldensteward.support.bean.server.WithCountDataArray;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.support.net.RetrofitRequest;
import com.soubu.goldensteward.support.utils.GlideUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dingsigang on 16-12-6.
 */

public class SignUpActivity extends ActivityPresenter<SignUpActivityDelegate> {
    List<Integer> mCheckedIndex;


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
        mCheckedIndex = new ArrayList<>();
        RetrofitRequest.getInstance().getProductListOnSale();
        SingleAdapter adapter = new SingleAdapter<ProductInOrderListServerParams>(this, R.layout.item_product_access_product_on_sale_recyclerview) {
            @Override
            protected void bindData(BaseViewHolder holder, ProductInOrderListServerParams item, int position) {
                View vBottom1 = holder.getView(R.id.ll_bottom);
                vBottom1.setVisibility(View.GONE);
                ImageView ivProductImg = holder.getView(R.id.iv_product);
                TextView tvProductName = holder.getView(R.id.tv_name);
                TextView tvSamplePrice = holder.getView(R.id.tv_sample_card_price);
                TextView tvBigGoodsPrice = holder.getView(R.id.tv_big_goods_price);
                TextView tvBrowse = holder.getView(R.id.tv_browser_volume);
                TextView tvCollection = holder.getView(R.id.tv_collection_volume);
                GlideUtils.loadRoundedImage(ivProductImg.getContext(), ivProductImg, item.getPic(), R.drawable.common_product_placeholder, R.drawable.common_product_placeholder);
                tvProductName.setText(item.getTitle());
                tvSamplePrice.setText(item.getPrice());
                tvBrowse.setText(item.getVisit());
                tvCollection.setText(item.getCollection());
            }

            @Override
            public void onItemClick(BaseViewHolder holder, ProductInOrderListServerParams item, int position) {
                CheckBox cbChoose = holder.getView(R.id.cb_choose);
                if (cbChoose.isChecked()) {
                    cbChoose.setChecked(false);
                } else {
                    cbChoose.setChecked(true);
                }
                if (!mCheckedIndex.contains(position)) {
                    mCheckedIndex.add(position);
                } else {
                    mCheckedIndex.remove((Integer) position);
                }
                viewDelegate.refreshTotalText(mCheckedIndex.size());
            }
        };
        viewDelegate.setProductAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getDataSuccess(BaseEventBusResp resp) {
        BaseResp resp1 = (BaseResp) resp.getObject();
        int code = resp.getCode();
        switch (code) {
            case EventBusConfig.GET_PRODUCT_LIST_ON_SALE:
                WithCountDataArray result2 = (WithCountDataArray) resp1.getResult();
                List<ProductInOrderListServerParams> list = Arrays.asList((ProductInOrderListServerParams[]) result2.getData());
                viewDelegate.initProduct(list);
                break;
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
                        viewDelegate.onCommitSuccess();
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
