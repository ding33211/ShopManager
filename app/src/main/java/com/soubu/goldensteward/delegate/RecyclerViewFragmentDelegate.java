package com.soubu.goldensteward.delegate;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.adapter.InformationRecyclerViewAdapter;
import com.soubu.goldensteward.module.RecyclerViewItem;

import java.util.List;

/**
 * Created by dingsigang on 16-10-19.
 */
public class RecyclerViewFragmentDelegate extends BaseFragmentDelegate {

    private InformationRecyclerViewAdapter mAdapter;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_recyclerview;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mAdapter = new InformationRecyclerViewAdapter();
        RecyclerView rvContent = get(R.id.rv_content);
        rvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvContent.setAdapter(mAdapter);
    }

    public void setData(List<RecyclerViewItem> list){
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }
}
