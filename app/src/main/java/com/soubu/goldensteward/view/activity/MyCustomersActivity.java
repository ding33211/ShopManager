package com.soubu.goldensteward.view.activity;

import android.content.Intent;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.adapter.BaseRecyclerViewAdapter;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.MyCustomersActivityDelegate;

/**
 * Created by lakers on 16/10/29.
 */

public class MyCustomersActivity extends ActivityPresenter<MyCustomersActivityDelegate> {
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

    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnRvItemSelectedListener(new BaseRecyclerViewAdapter.OnRvItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MyCustomersActivity.this, CustomerSpecActivity.class);
                startActivity(intent);
            }
        });
    }
}
