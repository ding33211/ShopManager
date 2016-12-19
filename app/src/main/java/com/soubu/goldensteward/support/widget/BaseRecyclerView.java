package com.soubu.goldensteward.support.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.helper.ViewType;

/**
 * 支持处理异常情况显示的recyclerView
 * Created by dingsigang on 16-12-15.
 */

public class BaseRecyclerView extends RecyclerView {
    private ViewType errorType;

    private View mEmptyView;
    private View mServerErrorView;
    private View mInternetErrorView;

    private AdapterDataObserver observer = new AdapterDataObserver() {

        @Override
        public void onChanged() {
            Adapter<?> adapter = getAdapter();
            if (adapter != null) {
                if (mEmptyView.getParent() != null) {
                    mEmptyView.setVisibility(GONE);
                }
                if (mServerErrorView.getParent() != null) {
                    mServerErrorView.setVisibility(GONE);
                }
                if (mInternetErrorView.getParent() != null) {
                    mInternetErrorView.setVisibility(GONE);
                }
                if (errorType != null) {
                    ViewGroup vg = (ViewGroup) BaseRecyclerView.this.getParent();
                    switch (errorType) {
                        case ERROR_INTERNET:
                            if (mInternetErrorView.getParent() != null) {
                                mInternetErrorView.setVisibility(VISIBLE);
                            } else {
                                vg.addView(mInternetErrorView);
                            }
                            break;
                        case ERROR_SERVER:
                            if (mServerErrorView.getParent() != null) {
                                mServerErrorView.setVisibility(VISIBLE);
                            } else {
                                vg.addView(mServerErrorView);
                            }
                            break;
                    }
                    BaseRecyclerView.this.setVisibility(GONE);
                } else if (adapter.getItemCount() == 0) {
                    if (mEmptyView.getParent() != null) {
                        mEmptyView.setVisibility(VISIBLE);
                    } else {
                        ViewGroup vg = (ViewGroup) BaseRecyclerView.this.getParent();
                        vg.addView(mEmptyView);
                    }
                    BaseRecyclerView.this.setVisibility(GONE);
                } else {
                    BaseRecyclerView.this.setVisibility(VISIBLE);
                }
            }

        }
    };

    public BaseRecyclerView(Context context) {
        super(context);
        initErrorView(context);
    }

    public BaseRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initErrorView(context);
    }

    public BaseRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initErrorView(context);
    }

    private void initErrorView(Context context) {
        LinearLayout view = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.error_view, null, false);
        mEmptyView = view.findViewById(R.id.ll_empty);
        mInternetErrorView = view.findViewById(R.id.ll_internet_error);
        mServerErrorView = view.findViewById(R.id.ll_server_error);
        view.removeAllViews();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }
    }

    public void setErrorType(ViewType errorType) {
        this.errorType = errorType;
        observer.onChanged();
    }

    public void resetting() {
        errorType = null;
    }

    public boolean canLoadMore() {
        return !(getAdapter() == null || getAdapter().getItemCount() == 0 || errorType != null);
    }

    public void setEmptyDesc(String desc) {
        TextView tvDesc = (TextView) mEmptyView.findViewById(R.id.tv_empty_desc);
        tvDesc.setText(desc);
    }
}
