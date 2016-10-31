package com.soubu.goldensteward.delegate;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.adapter.TransactionRecordRvAdapter;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;
import com.soubu.goldensteward.module.TransactionRecordModule;
import com.soubu.goldensteward.widget.DividerItemDecoration;

import java.util.List;

/**
 * Created by lakers on 16/10/31.
 */

public class CustomerSpecActivityDelegate extends AppDelegate {
    TransactionRecordRvAdapter mAdapter;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_customer_spec;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mAdapter = new TransactionRecordRvAdapter();
        RecyclerView recyclerView = get(R.id.rv_content);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(), LinearLayoutManager.VERTICAL, 40));
    }

    public void setData(List<TransactionRecordModule> list) {
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }
}
