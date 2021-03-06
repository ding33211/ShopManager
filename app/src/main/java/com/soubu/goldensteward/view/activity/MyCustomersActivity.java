package com.soubu.goldensteward.view.activity;

import android.content.Intent;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.adapter.BaseRecyclerViewAdapter;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.MyCustomersActivityDelegate;
import com.soubu.goldensteward.module.BaseEventBusResp;
import com.soubu.goldensteward.module.Constant;
import com.soubu.goldensteward.module.EventBusConfig;
import com.soubu.goldensteward.module.server.BaseDataArray;
import com.soubu.goldensteward.module.server.BaseResp;
import com.soubu.goldensteward.module.server.CustomerServerParams;
import com.soubu.goldensteward.server.RetrofitRequest;
import com.soubu.goldensteward.utils.PinyinComparator;

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
        viewDelegate.setOnRvItemSelectedListener(new BaseRecyclerViewAdapter.OnRvItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MyCustomersActivity.this, CustomerSpecActivity.class);
                intent.putExtra(Constant.EXTRA_PARAMS, mList.get(position));
                startActivity(intent);
            }
        });
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
