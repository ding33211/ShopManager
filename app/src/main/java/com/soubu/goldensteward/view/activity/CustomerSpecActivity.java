package com.soubu.goldensteward.view.activity;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.CustomerSpecActivityDelegate;
import com.soubu.goldensteward.module.BaseEventBusResp;
import com.soubu.goldensteward.module.Constant;
import com.soubu.goldensteward.module.EventBusConfig;
import com.soubu.goldensteward.module.server.BaseResp;
import com.soubu.goldensteward.module.server.CustomerDetailDataObject;
import com.soubu.goldensteward.module.server.CustomerServerParams;
import com.soubu.goldensteward.module.server.ProductInCustomerDetailServerParams;
import com.soubu.goldensteward.server.RetrofitRequest;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;

/**
 * Created by lakers on 16/10/31.
 */

public class CustomerSpecActivity extends ActivityPresenter<CustomerSpecActivityDelegate> {
    CustomerServerParams mParams;

    @Override
    protected Class<CustomerSpecActivityDelegate> getDelegateClass() {
        return CustomerSpecActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.spec_info);
    }

    @Override
    protected void initData() {
        super.initData();
        mParams = (CustomerServerParams) getIntent().getSerializableExtra(Constant.EXTRA_PARAMS);
        RetrofitRequest.getInstance().getCustomerDetail(mParams);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCustomerDetail(BaseEventBusResp resp) {
        BaseResp resp1 = (BaseResp) resp.getObject();
        int code = resp.getCode();
        if (code == EventBusConfig.GET_CUSTOMER_DETAIL) {
            CustomerServerParams params = ((CustomerDetailDataObject) resp1.getResult()).getData();
            mParams.deltaCopy(params);
            viewDelegate.initCustomerInfo(mParams);
            ProductInCustomerDetailServerParams[] orders = ((CustomerDetailDataObject) resp1.getResult()).getOrder();
            if(orders != null){
                viewDelegate.setData(Arrays.asList(orders));
            }
        }
    }
}
