package com.soubu.goldensteward.support.delegate;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.soubu.goldensteward.support.adapter.FooterSingleAdapter;
import com.soubu.goldensteward.support.mvp.view.AppDelegate;

/**
 * Created by dingsigang on 16-11-22.
 */

public abstract class BaseWithFootOrRefreshRecyclerViewDelegate extends AppDelegate {
    public RecyclerView mRvContent;
    public LinearLayoutManager mManager;
    public FooterSingleAdapter mAdapter;

    public interface FreshInRvCallBack {
        void loadMore();

        void refresh();
    }

    public void setOnLoadMoreCallBack(final FreshInRvCallBack callBack) {
        mRvContent.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private int lastVisibleItem;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mManager.findLastVisibleItemPosition();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == mAdapter.getItemCount() && mAdapter.isShowFooter()) {
                    //加载更多
                    callBack.loadMore();
                }
            }
        });
    }
}
