package com.soubu.goldensteward.ui.market;

import android.content.Intent;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.bean.BaseEventBusResp;
import com.soubu.goldensteward.support.bean.EventBusConfig;
import com.soubu.goldensteward.support.bean.server.BaseResp;
import com.soubu.goldensteward.support.bean.server.ProductInOrderListServerParams;
import com.soubu.goldensteward.support.bean.server.WithCountDataArray;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.support.net.RetrofitRequest;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.List;

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
        RetrofitRequest.getInstance().getProductListOnSale();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getDataSuccess(BaseEventBusResp resp) {
        BaseResp resp1 = (BaseResp) resp.getObject();
        int code = resp.getCode();
        switch (code) {
            case EventBusConfig.GET_PRODUCT_LIST_ON_SALE:
                WithCountDataArray result2 = (WithCountDataArray) resp1.getResult();
                List<ProductInOrderListServerParams> list = Arrays.asList((ProductInOrderListServerParams[]) result2.getData());
                viewDelegate.initProduct(list);
                viewDelegate.refreshSignUpState(2, 14, "哈哈哈哈哈哈");
                break;
        }
    }
}
