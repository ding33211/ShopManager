package com.soubu.goldensteward.ui.market;

import android.content.Intent;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.base.BaseApplication;
import com.soubu.goldensteward.support.bean.server.ProductInSignUpActivityServerParams;
import com.soubu.goldensteward.support.constant.IntentKey;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.support.web.core.BaseException;
import com.soubu.goldensteward.support.web.core.BaseResponse;
import com.soubu.goldensteward.support.web.core.BaseSubscriber;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dingsigang on 16-12-7.
 */

public class SignUpSpecActivity extends ActivityPresenter<SignUpSpecActivityDelegate> {
    private int mSignUpId = -1;
    private int mActivityId = -1;
    private boolean mHaveSignedUp = false;

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
        mSignUpId = getIntent().getIntExtra(IntentKey.EXTRA_ACTIVITY_ID, -1);
        if(mSignUpId != -1){
            Map<String, Integer> map = new HashMap<>();
            map.put("id", mSignUpId);
            BaseApplication.getWebModel().getSignUpSpec(map).sendTo(new BaseSubscriber<BaseResponse<List<ProductInSignUpActivityServerParams>>>(this) {
                @Override
                public void onSuccess(BaseResponse<List<ProductInSignUpActivityServerParams>> response) {
                    BaseResponse.Entity entity = response.getResult();
                    mActivityId = entity.getActive_id();
                    mHaveSignedUp = entity.getSign_up_status() == 1;
                    List<ProductInSignUpActivityServerParams> list = response.getResult().getData();
                    viewDelegate.initProduct(list);
                    viewDelegate.refreshSignUpState(entity.getStatus(), list.size(), entity.getFail_cause());
                }

                @Override
                public void onFailure(BaseException exception) {
                    super.onFailure(exception);

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
                intent.putExtra(IntentKey.EXTRA_ACTIVITY_ID, mActivityId);
                intent.putExtra(IntentKey.EXTRA_HAVE_SIGNED_UP, mHaveSignedUp);
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
