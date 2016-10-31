package com.soubu.goldensteward.view.activity;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.CustomerSpecActivityDelegate;
import com.soubu.goldensteward.module.TransactionRecordModule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakers on 16/10/31.
 */

public class CustomerSpecActivity extends ActivityPresenter<CustomerSpecActivityDelegate> {
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
        List<TransactionRecordModule> list = new ArrayList<>();
        TransactionRecordModule module = new TransactionRecordModule();
        list.add(module);
        list.add(module);
        list.add(module);
        list.add(module);
        list.add(module);
        list.add(module);
        list.add(module);
        list.add(module);
        list.add(module);
        list.add(module);
        viewDelegate.setData(list);
    }
}
