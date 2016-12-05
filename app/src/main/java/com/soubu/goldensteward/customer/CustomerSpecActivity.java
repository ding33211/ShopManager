package com.soubu.goldensteward.customer;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.module.BaseEventBusResp;
import com.soubu.goldensteward.module.Constant;
import com.soubu.goldensteward.module.EventBusConfig;
import com.soubu.goldensteward.module.server.BaseResp;
import com.soubu.goldensteward.module.server.CustomerDetailDataObject;
import com.soubu.goldensteward.module.server.CustomerServerParams;
import com.soubu.goldensteward.module.server.ProductInCustomerDetailServerParams;
import com.soubu.goldensteward.server.RetrofitRequest;
import com.soubu.goldensteward.base.presenter.BaseWithFootOrRefreshRecyclerViewPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;

/**
 * Created by lakers on 16/10/31.
 */

public class CustomerSpecActivity extends BaseWithFootOrRefreshRecyclerViewPresenter<CustomerSpecActivityDelegate> {
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
    }

    @Override
    protected void doRequest(int pageNum) {
        mParams.setPage(mPageNum + "");
        RetrofitRequest.getInstance().getCustomerDetail(mParams);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCustomerDetail(BaseEventBusResp resp) {
        BaseResp resp1 = (BaseResp) resp.getObject();
        int code = resp.getCode();
        if (code == EventBusConfig.GET_CUSTOMER_DETAIL) {
            if (mPageNum == 1) {
                CustomerServerParams params = ((CustomerDetailDataObject) resp1.getResult()).getData();
                mParams.deltaCopy(params);
                viewDelegate.initCustomerInfo(mParams);
            }
            ProductInCustomerDetailServerParams[] orders = ((CustomerDetailDataObject) resp1.getResult()).getOrder();
            if (orders != null) {
                viewDelegate.setData(Arrays.asList(orders), mIsRefresh);
            }
        }
    }
}
