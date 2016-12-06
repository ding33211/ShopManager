package com.soubu.goldensteward.ui.report;

import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.support.bean.BaseEventBusResp;
import com.soubu.goldensteward.support.bean.EventBusConfig;
import com.soubu.goldensteward.support.bean.server.BaseResp;
import com.soubu.goldensteward.support.bean.server.ProductInOrderListServerParams;
import com.soubu.goldensteward.support.bean.server.WithCountDataArray;
import com.soubu.goldensteward.support.net.RetrofitRequest;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by dingsigang on 16-11-30.
 */

public class StoreFlowStatisticsActivity extends ActivityPresenter<StoreFlowStatisticsActivityDelegate> implements View.OnClickListener {
    @Override
    protected Class<StoreFlowStatisticsActivityDelegate> getDelegateClass() {
        return StoreFlowStatisticsActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.store_flow_data);
    }

    @Override
    protected void initView() {
        super.initView();
        viewDelegate.initTabLayout(new String[]{getString(R.string.product_browse_statistics), getString(R.string.store_visitor_statistics)});
    }


    @Override
    protected void initData() {
        super.initData();
        RetrofitRequest.getInstance().getProductListOnSale();
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.bt_last_week, R.id.bt_last_month);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_last_week:
                viewDelegate.setTopBarSelected(0);
                break;
            case R.id.bt_last_month:
                viewDelegate.setTopBarSelected(1);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getDataSuccess(BaseEventBusResp resp) {
        BaseResp resp1 = (BaseResp) resp.getObject();
        int code = resp.getCode();
        switch (code) {
            case EventBusConfig.GET_PRODUCT_LIST_ON_SALE:
                WithCountDataArray result2 = (WithCountDataArray) resp1.getResult();
                viewDelegate.initProductAccessRecyclerView((ProductInOrderListServerParams[]) result2.getData());
                break;
        }

    }
}
