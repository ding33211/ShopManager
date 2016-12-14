package com.soubu.goldensteward.ui.market;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.base.BaseApplication;
import com.soubu.goldensteward.support.bean.server.ProductInSignUpActivityServerParams;
import com.soubu.goldensteward.support.constant.IntentKey;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.support.web.core.BaseResponse;
import com.soubu.goldensteward.support.web.core.BaseSubscriber;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dingsigang on 16-12-7.
 */

public class SignUpSpecActivity extends ActivityPresenter<SignUpSpecActivityDelegate> {
    @Override
    protected Class<SignUpSpecActivityDelegate> getDelegateClass() {
        return SignUpSpecActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.sign_up_spec);
    }

    @Override
    protected void initData() {
        super.initData();
        String activityId = getIntent().getStringExtra(IntentKey.EXTRA_ACTIVITY_ID);
        if(!TextUtils.isEmpty(activityId)){
            Map<String, String> map = new HashMap<>();
            map.put("id", activityId);
            BaseApplication.getWebModel().getSignUpSpec(map).sendTo(new BaseSubscriber<BaseResponse<List<ProductInSignUpActivityServerParams>>>(this) {
                @Override
                public void onSuccess(BaseResponse<List<ProductInSignUpActivityServerParams>> response) {
                    List<ProductInSignUpActivityServerParams> list = response.getResult().getData();
                    viewDelegate.initProduct(list);
                    viewDelegate.refreshSignUpState(response.getResult().getStatus(), list.size(), response.getResult().getFail_cause());
                }
            });
        }
//        RetrofitRequest.getInstance().getProductListOnSale();
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpSpecActivity.this, ActivitySpecActivity.class);
                startActivity(intent);
            }
        }, R.id.btn_sign_up_again);
    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void getDataSuccess(BaseEventBusResp resp) {
//        BaseResp resp1 = (BaseResp) resp.getObject();
//        int code = resp.getCode();
//        switch (code) {
//            case EventBusConfig.GET_PRODUCT_LIST_ON_SALE:
//                WithCountDataArray result2 = (WithCountDataArray) resp1.getResult();
//                List<ProductInOrderListServerParams> list = Arrays.asList((ProductInOrderListServerParams[]) result2.getData());
//                viewDelegate.initProduct(list);
//                viewDelegate.refreshSignUpState(2, 14, "哈哈哈哈哈哈");
//                break;
//        }
//    }
}
