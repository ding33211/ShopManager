package com.soubu.goldensteward.ui.customer;

import android.content.Intent;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.bean.BaseEventBusResp;
import com.soubu.goldensteward.support.constant.IntentKey;
import com.soubu.goldensteward.support.bean.EventBusConfig;
import com.soubu.goldensteward.support.bean.server.BaseDataArray;
import com.soubu.goldensteward.support.bean.server.BaseResp;
import com.soubu.goldensteward.support.bean.server.CustomerServerParams;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.support.net.RetrofitRequest;
import com.soubu.goldensteward.support.utils.PinyinComparator;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by lakers on 16/10/29.
 */

public class MyCustomersActivity extends ActivityPresenter<MyCustomersActivityDelegate> {
    private List<CustomerServerParams> mList;

    @Override
    protected Class<MyCustomersActivityDelegate> getDelegateClass() {
        return MyCustomersActivityDelegate.class;
    }


    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.my_customer);
    }


    @Override
    protected void initData() {
        super.initData();
        RetrofitRequest.getInstance().getCustomerList();
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        MyCustomerSingleRvAdapter adapter = new MyCustomerSingleRvAdapter(this) {
            @Override
            public void onItemClick(BaseViewHolder holder, CustomerServerParams item, int position) {
                Intent intent = new Intent(MyCustomersActivity.this, CustomerSpecActivity.class);
                intent.putExtra(IntentKey.EXTRA_PARAMS, mList.get(position));
                startActivity(intent);
            }
        };
        viewDelegate.setMyCustomerAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCustomerListSuccess(BaseEventBusResp resp) {
        BaseResp resp1 = (BaseResp) resp.getObject();
        int code = resp.getCode();
        if (code == EventBusConfig.GET_CUSTOMER_LIST) {
            CustomerServerParams[] params = (CustomerServerParams[]) ((BaseDataArray) resp1.getResult()).getData();
            mList = Arrays.asList(params);
            Collections.sort(mList, new PinyinComparator());
            viewDelegate.setData(mList);
        }
    }
}
