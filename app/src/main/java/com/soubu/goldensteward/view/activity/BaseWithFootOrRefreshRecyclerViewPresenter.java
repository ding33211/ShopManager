package com.soubu.goldensteward.view.activity;

import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.BaseWithFootOrRefreshRecyclerViewDelegate;

/**
 * Created by dingsigang on 16-11-22.
 */

public abstract class BaseWithFootOrRefreshRecyclerViewPresenter<T extends BaseWithFootOrRefreshRecyclerViewDelegate> extends ActivityPresenter<T> {
    boolean mIsRefresh = false;
    int mPageNum = 1;

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnLoadMoreCallBack(new BaseWithFootOrRefreshRecyclerViewDelegate.FreshInRvCallBack() {
            @Override
            public void loadMore() {
                getList(false);
            }

            @Override
            public void refresh() {
                getList(true);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getList(true);
    }

    void getList(boolean isRefresh) {
        mIsRefresh = isRefresh;
        if (isRefresh) {
            mPageNum = 1;
        } else {
            mPageNum++;
        }
        doRequest(mPageNum);
    }

    protected abstract void doRequest(int pageNum);

}
