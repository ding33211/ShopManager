package com.soubu.goldensteward.support.web.mvp;

import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
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
public abstract class BaseListMvpFragment<P extends BaseListPresenter<T>, T> extends BaseMvpFragment<P> implements BaseView, RefreshHelper.RefreshInterface<T> {

    @BindView(R.id.tv_nomsg)
    TextView tvNomsg;
    @BindView(R.id.view_empty)
    LinearLayout viewEmpty;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.view_refresh)
    RefreshLayout viewRefresh;

    public RefreshHelper refreshHelper;

    @Override
    public void initWidget() {
        refreshHelper = new RefreshHelper<T>(this, viewRefresh, this, createItmeId());
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

    protected abstract int createItmeId();

    @Override
    public void bindListener(BaseViewHolder holder, T item, int position) {

    }

    @Override
    public void onItemClick(BaseViewHolder holder, T item, int position) {

    }
}
