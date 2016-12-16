package com.soubu.goldensteward.support.widget.pullrefresh;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.adapter.SingleAdapter;
import com.soubu.goldensteward.support.utils.ConvertUtil;
import com.soubu.goldensteward.support.utils.LogUtil;
import com.soubu.goldensteward.support.utils.ShowWidgetUtil;
import com.soubu.goldensteward.support.web.core.BaseException;
import com.soubu.goldensteward.support.web.core.BaseSubscriber;
import com.soubu.goldensteward.support.web.core.BaseTransformer;
import com.soubu.goldensteward.support.web.mvp.BaseView;
import com.soubu.goldensteward.support.widget.RecyclerViewExceptionHandlerSupport;
import com.soubu.goldensteward.support.widget.pullrefresh.core.OnPullListener;
import com.soubu.goldensteward.support.widget.recyclerviewdecoration.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

/**
 * 作者：余天然 on 2016/10/18 下午9:25
 */
public class RefreshHelper<T> {

    public RefreshLayout viewRefresh;
    public RefreshInterface<T> refreshInterface;
    public int layoutId;

    public Context context;
    //    public View viewEmpty;
    public RecyclerView rv;
    public List<T> data;

    public SingleAdapter<T> adapter;
    public int curPage = 1;//当前页码
    public boolean isRefresh = false;//是否正在刷新
    public boolean isLoadMore = false;//是否正在加载更多
    public boolean isBgRefresh = false;//是否是后台静默刷新

    public boolean isShowLoading = true;//是否显示loading

    public BaseView view;

    public RefreshHelper(BaseView view, RefreshLayout viewRefresh, RefreshInterface<T> refreshInterface, int layoutId) {
        this.view = view;
        this.viewRefresh = viewRefresh;
        this.refreshInterface = refreshInterface;
        this.layoutId = layoutId;

        context = viewRefresh.getContext();
//        viewEmpty = viewRefresh.getChildAt(0);
        rv = (RecyclerView) viewRefresh.getChildAt(0);
        data = new ArrayList<>();

        initRv();
        initRefresh();
    }

    public void detachView() {
        this.view = null;
        this.viewRefresh = null;
        this.refreshInterface = null;
        this.context = null;
//        this.viewEmpty = null;
        this.rv = null;
        this.data = null;
    }

    private void initRv() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rv.setLayoutManager(layoutManager);
        rv.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL, ConvertUtil.dip2px(context, 10)));
        adapter = new SingleAdapter<T>(context, layoutId) {
            @Override
            protected void bindData(BaseViewHolder holder, T item, int position) {
                refreshInterface.bindData(holder, item, position);
            }

            @Override
            protected void bindListener(BaseViewHolder holder, T item, int position) {
                refreshInterface.bindListener(holder, item, position);
            }

            @Override
            public void onItemClick(BaseViewHolder holder, T item, int position) {
                refreshInterface.onItemClick(holder, item, position);
            }
        };
        rv.setAdapter(adapter);
    }

    private void initRefresh() {
        viewRefresh.setOnPullListener(new OnPullListener() {
            @Override
            public void onRefresh() {
                LogUtil.print("");
                isRefresh = true;
                curPage = 1;
                data = new ArrayList<>();
                loadData();
            }

            @Override
            public void onLoadMore() {
                if (rv instanceof RecyclerViewExceptionHandlerSupport) {
                    if (!((RecyclerViewExceptionHandlerSupport) rv).canLoadMore()) {
                        return;
                    }
                }
                LogUtil.print("");
                isLoadMore = true;
                curPage++;
                loadData();
            }
        });
    }

    public void bgRefresh() {
        isBgRefresh = true;
        curPage = 1;
        data = new ArrayList<>();
        loadData();
    }

    public void clearData() {
        data = new ArrayList<>();
    }

    public void loadData() {
        if (isRefresh || isLoadMore || isBgRefresh) {
            isShowLoading = false;
        } else {
            isShowLoading = true;
        }
        if (rv instanceof RecyclerViewExceptionHandlerSupport) {
            ((RecyclerViewExceptionHandlerSupport) rv).resetting();
        }
        refreshInterface.getData(curPage)
                .compose(new BaseTransformer<>())
                .compose(view.bindLife())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (isShowLoading) {
                            ShowWidgetUtil.showProgressDialog(null, R.style.LoadingProgressTheme);
                        }
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<T>>() {
                    @Override
                    public void onSuccess(List<T> list) {
                        LogUtil.print("list.size=" + list.size());
                        if (isRefresh) {
                            isRefresh = false;
                            viewRefresh.stopRefresh(true);
                        }
                        if (isLoadMore) {
                            isLoadMore = false;
                            viewRefresh.stopLoadMore(true);
                            if (list.isEmpty()) {
                                ShowWidgetUtil.showShort("没有更多数据了");
                            }
                        }
                        if (isBgRefresh) {
                            isBgRefresh = false;
                        }
                        data.addAll(list);
                        if (data.isEmpty()) {
                            rv.setVisibility(View.GONE);
                            viewRefresh.setCanLoadMore(false);
//                            viewEmpty.setVisibility(View.VISIBLE);
                        } else {
                            rv.setVisibility(View.VISIBLE);
                            viewRefresh.setCanLoadMore(true);
//                            viewEmpty.setVisibility(View.GONE);
                        }
                        adapter.setData(data);
                    }

                    @Override
                    public void onFailure(BaseException exception) {
                        super.onFailure(exception);
                        if (exception.getErrorCode() == 0) {
                            if (rv instanceof RecyclerViewExceptionHandlerSupport) {
                                ((RecyclerViewExceptionHandlerSupport) rv).setErrorType(RecyclerViewExceptionHandlerSupport.ERROR_INTERNET);
                                viewRefresh.setCanLoadMore(false);
                            }
                        }
                        if (exception.getErrorCode() == 404) {
                            if (rv instanceof RecyclerViewExceptionHandlerSupport) {
                                ((RecyclerViewExceptionHandlerSupport) rv).setErrorType(RecyclerViewExceptionHandlerSupport.ERROR_SERVER);
                                viewRefresh.setCanLoadMore(false);
                            }
                        }
                        if (isRefresh) {
                            isRefresh = false;
                            viewRefresh.stopRefresh(false);
                        }
                        if (isLoadMore) {
                            isLoadMore = false;
                            viewRefresh.stopLoadMore(false);
                        }
                        if (isBgRefresh) {
                            isBgRefresh = false;
                        }
                    }
                });
    }


    public interface RefreshInterface<T> {

        Observable<List<T>> getData(int curPage);

        void bindData(BaseViewHolder holder, T item, int position);

        void bindListener(BaseViewHolder holder, T item, int position);

        void onItemClick(BaseViewHolder holder, T item, int position);
    }

    public List<T> getData() {
        return data;
    }

    public SingleAdapter<T> getAdapter() {
        return adapter;
    }
}
