package com.soubu.goldensteward.view.activity;

import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.StoreFlowStatisticsActivityDelegate;

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
}
