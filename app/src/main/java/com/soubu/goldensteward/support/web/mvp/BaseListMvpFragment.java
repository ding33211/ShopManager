package com.soubu.goldensteward.support.web.mvp;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.utils.LogUtil;
import com.soubu.goldensteward.support.widget.BaseRecyclerView;
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
    public BaseRecyclerView rv;
    @BindView(R.id.view_refresh)
    RefreshLayout viewRefresh;

    public RefreshHelper refreshHelper;

    @Override
    public void initWidget() {
        LogUtil.print("");
        refreshHelper = new RefreshHelper<T>(viewRefresh, this, createItemId());
        refreshHelper.loadData();
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

    protected abstract int createItemId();

    @Override
    public void bindListener(BaseViewHolder holder, T item, int position) {

    }

    @Override
    public void onItemClick(BaseViewHolder holder, T item, int position) {

    }

}
