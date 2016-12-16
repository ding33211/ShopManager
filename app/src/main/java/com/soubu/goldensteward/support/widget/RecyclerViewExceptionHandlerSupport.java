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

/**
 * 支持处理异常情况显示的recyclerView
 * Created by dingsigang on 16-12-15.
 */

public class RecyclerViewExceptionHandlerSupport extends RecyclerView {
    public static final int ERROR_SERVER = 0x01;
    public static final int ERROR_INTERNET = 0x02;
    private int errorType = -1;

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
                if (errorType != -1) {
                    ViewGroup vg = (ViewGroup) RecyclerViewExceptionHandlerSupport.this.getParent();
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
                    RecyclerViewExceptionHandlerSupport.this.setVisibility(GONE);
                } else if (adapter.getItemCount() == 0) {
                    if (mEmptyView.getParent() != null) {
                        mEmptyView.setVisibility(VISIBLE);
                    } else {
                        ViewGroup vg = (ViewGroup) RecyclerViewExceptionHandlerSupport.this.getParent();
                        vg.addView(mEmptyView);
                    }
                    RecyclerViewExceptionHandlerSupport.this.setVisibility(GONE);
                } else {
                    RecyclerViewExceptionHandlerSupport.this.setVisibility(VISIBLE);
                }
            }

        }
    };

    public RecyclerViewExceptionHandlerSupport(Context context) {
        super(context);
        initErrorView(context);
    }

    public RecyclerViewExceptionHandlerSupport(Context context, AttributeSet attrs) {
        super(context, attrs);
        initErrorView(context);
    }

    public RecyclerViewExceptionHandlerSupport(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initErrorView(context);
    }

    private void initErrorView(Context context) {
        LinearLayout view = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.error_view, null, false);
        mEmptyView = view.findViewById(R.id.ll_empty);
        mInternetErrorView = view.findViewById(R.id.ll_internet_error);
        mServerErrorView = view.findViewById(R.id.ll_server_error);
//        mEmptyView.setOnClickListener(this);
//        mInternetErrorView.setOnClickListener(this);
//        mServerErrorView.setOnClickListener(this);
        view.removeAllViews();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }
//        observer.onChanged();
    }


    public void setErrorType(int errorType) {
        this.errorType = errorType;
        observer.onChanged();
    }

    public void resetting() {
        errorType = -1;
    }

    public boolean canLoadMore(){
        return !(getAdapter() == null || getAdapter().getItemCount() == 0 || errorType != -1);
    }

    public void setEmptyDesc(String desc){
        TextView tvDesc = (TextView) mEmptyView.findViewById(R.id.tv_empty_desc);
        tvDesc.setText(desc);
    }

//
//    OnClickErrorViewListener mListener;
//
//    public interface OnClickErrorViewListener {
//        void onClick(View v);
//    }
//
//    public void setOnClickErrorViewListener(OnClickErrorViewListener listener) {
//        mListener = listener;
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (mListener != null) {
//            mListener.onClick(v);
//        }
//    }
}
