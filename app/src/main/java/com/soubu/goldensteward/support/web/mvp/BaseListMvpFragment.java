package com.soubu.goldensteward.support.web.mvp;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.helper.ViewConverter;
import com.soubu.goldensteward.support.helper.ViewType;
import com.soubu.goldensteward.support.utils.LogUtil;
import com.soubu.goldensteward.support.widget.pullrefresh.RefreshHelper;
import com.soubu.goldensteward.support.widget.pullrefresh.RefreshLayout;

import java.util.List;

import butterknife.BindView;
import rx.Observable;

/**
 * 下拉刷新上拉加载更多的Fragment基类
 * <p>
 * Created by dingsigang on 16-12-6.
 */
public abstract class BaseListMvpFragment<P extends BaseListPresenter, T> extends BaseMvpFragment<P> implements BaseView, RefreshHelper.RefreshInterface<T> {

    @BindView(R.id.rv)
    public RecyclerView rv;
    @BindView(R.id.view_refresh)
    RefreshLayout viewRefresh;

    public View errorView;

    public RefreshHelper refreshHelper;

    @Override
    public void initWidget() {
        LogUtil.print("");
        refreshHelper = new RefreshHelper<T>(viewRefresh, this, createItemId());
        refreshHelper.loadData();
        refreshHelper.setDataInterface(new RefreshHelper.DataInterface() {
            @Override
            public void onData(ViewType type) {
                LogUtil.print("type=" + type);
                switch (type) {
                    case CONTENT:
                        rv.setVisibility(View.VISIBLE);
                        if (errorView != null) {
                            errorView.setVisibility(View.GONE);
                        }
                        viewRefresh.setCanLoadMore(true);
                        break;
                    case ERROR_EMPTY:
                        errorView = ViewConverter.setEmptyView(rv, getEmptyDesc());
                        viewRefresh.setCanLoadMore(false);
                        break;
                    case ERROR_INTERNET:
                        errorView = ViewConverter.setInternetView(rv);
                        viewRefresh.setCanLoadMore(false);
                        break;
                    case ERROR_SERVER:
                        errorView = ViewConverter.setServerView(rv);
                        viewRefresh.setCanLoadMore(false);
                        break;
                }
            }
        });
    }

    @Override
    protected int createLayoutId() {
        return R.layout.fragment_listmvp;
    }

    @Override
    public Observable<List<T>> getData(int curPage) {
        return presenter.getData(curPage);
    }

    @Override
    public void onDestroyView() {
        refreshHelper.detachView();
        super.onDestroyView();
    }

    /**
     * 绑定Item的Id
     */
    protected abstract int createItemId();

    /**
     * 绑定Item的监听事件
     */
    public void bindListener(BaseViewHolder holder, T item, int position) {

    }

    /**
     * 绑定Item的点击事件
     */
    public void onItemClick(BaseViewHolder holder, T item, int position) {

    }

    /**
     * 获取数据为空时的提示
     */
    protected abstract int getEmptyDesc();

}
