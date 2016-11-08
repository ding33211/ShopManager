package com.soubu.goldensteward.view.activity;

import android.content.Intent;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.adapter.BaseRecyclerViewAdapter;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.MyCustomersActivityDelegate;
import com.soubu.goldensteward.module.Constant;
import com.soubu.goldensteward.module.server.BaseDataArray;
import com.soubu.goldensteward.module.server.BaseResp;
import com.soubu.goldensteward.module.server.CustomerServerParams;
import com.soubu.goldensteward.server.RetrofitRequest;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by lakers on 16/10/29.
 */

public class MyCustomersActivity extends ActivityPresenter<MyCustomersActivityDelegate> {
    private CustomerServerParams[] mParams;

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
                intent.putExtra(Constant.EXTRA_PARAMS, mParams[position]);
                startActivity(intent);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCustomerListSuccess(BaseResp resp) {
        if (resp.getResult() instanceof BaseDataArray) {
            mParams = (CustomerServerParams[]) ((BaseDataArray) resp.getResult()).getData();
            viewDelegate.setData(Arrays.asList(mParams));
        }
    }
}
