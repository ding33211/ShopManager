package com.soubu.goldensteward.support.delegate;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.base.BaseRecyclerViewAdapter;
import com.soubu.goldensteward.support.utils.ConvertUtil;
import com.soubu.goldensteward.support.widget.recyclerviewdecoration.DividerItemDecoration;

import java.util.List;

/**
 * Created by dingsigang on 16-10-19.
 */
public class RecyclerViewFragmentDelegate<T> extends BaseFragmentDelegate {

    private BaseRecyclerViewAdapter mAdapter;
    RecyclerView mRvContent;
    LinearLayout mLlEmptyView;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_recyclerview;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mRvContent = get(R.id.rv_content);
        mRvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void setDecorationHeight(int heightDp){
        mRvContent.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, ConvertUtil.dip2px(getActivity(), heightDp)));
    }

    public void setData(List<T> list) {
        if (mLlEmptyView != null) {
            if (list.isEmpty()) {
                mLlEmptyView.setVisibility(View.VISIBLE);
            } else {
                mLlEmptyView.setVisibility(View.GONE);
            }
        }
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }

    public void setAdapter(BaseRecyclerViewAdapter<T> adapter) {
        mAdapter = adapter;
        mRvContent.setAdapter(mAdapter);
    }

    public void setEmptyView(View view) {
        mLlEmptyView = get(R.id.ll_empty_view);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        mLlEmptyView.addView(view);
    }

    public void setRvItemOnClickListener(BaseRecyclerViewAdapter.OnRvItemClickListener listener) {
        mAdapter.setOnRvItemClickListener(listener);
    }

    public View getClickView(int position) {
        return mRvContent.getLayoutManager().findViewByPosition(position);
    }

    public void notifyItemChanged(int position){
        mAdapter.notifyItemChanged(position);
    }

}
