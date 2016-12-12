package com.soubu.goldensteward.ui.customer;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.SingleAdapter;
import com.soubu.goldensteward.support.bean.server.CustomerServerParams;
import com.soubu.goldensteward.support.mvp.view.AppDelegate;
import com.soubu.goldensteward.support.widget.RecyclerViewFastScroller;

import java.util.List;

/**
 * Created by lakers on 16/10/29.
 */

public class MyCustomersActivityDelegate extends AppDelegate {
    SingleAdapter mAdapter;
    RecyclerView mRecyclerView;
    @Override
    public int getRootLayoutId() {
        return R.layout.activity_my_customers;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mRecyclerView = get(R.id.rv_content);
        final RecyclerViewFastScroller fastScroller = get(R.id.fast_scroller);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public void onLayoutChildren(final RecyclerView.Recycler recycler, final RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
                final int firstVisibleItemPosition = findFirstVisibleItemPosition();
                if (firstVisibleItemPosition != 0) {
                    // this avoids trying to handle un-needed calls
                    if (firstVisibleItemPosition == -1)
                        //not initialized, or no items shown, so hide fast-scroller
                        fastScroller.setVisibility(View.GONE);
                    return;
                }
                final int lastVisibleItemPosition = findLastVisibleItemPosition();
                int itemsShown = lastVisibleItemPosition - firstVisibleItemPosition + 1;
                //if all items are shown, hide the fast-scroller
                fastScroller.setVisibility(mAdapter.getItemCount() > itemsShown ? View.VISIBLE : View.GONE);
            }
        });
        fastScroller.setRecyclerView(mRecyclerView);
        fastScroller.setViewsToUse(R.layout.recycler_view_fast_scroller, R.id.fast_scroller_bubble, R.id.fast_scroller_handle);
    }


    public void setMyCustomerAdapter(SingleAdapter adapter) {
        mAdapter = adapter;
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }

    public void setData(List<CustomerServerParams> list) {
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }
}
