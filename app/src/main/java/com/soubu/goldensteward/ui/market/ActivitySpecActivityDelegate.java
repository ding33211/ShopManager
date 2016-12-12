package com.soubu.goldensteward.ui.market;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.adapter.SingleAdapter;
import com.soubu.goldensteward.support.bean.server.ActivitySpecServerParams;
import com.soubu.goldensteward.support.mvp.view.AppDelegate;
import com.soubu.goldensteward.support.utils.ConvertUtil;
import com.soubu.goldensteward.support.widget.recyclerviewdecoration.DividerItemDecoration;

import java.util.List;

/**
 * Created by dingsigang on 16-12-6.
 */

public class ActivitySpecActivityDelegate extends AppDelegate {
    RecyclerView mRvContent;
    SingleAdapter mRvAdapter;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_activity_spec;
    }


    @Override
    public void initWidget() {
        super.initWidget();
        mRvContent = get(R.id.rv_content);
        mRvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvContent.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, ConvertUtil.dip2px(this.getActivity(), 10)));
        mRvAdapter = new SingleAdapter<ActivitySpecServerParams.Content>(getActivity(), R.layout.item_activity_spec_recyclerview) {
            @Override
            protected void bindData(BaseViewHolder holder, ActivitySpecServerParams.Content item, int position) {
                TextView tvTitle = holder.getView(R.id.tv_title);
                TextView tvContent = holder.getView(R.id.tv_content);
                tvTitle.setText(item.getTitle());
                tvContent.setText(item.getContent());
            }

        };
        mRvContent.setAdapter(mRvAdapter);
    }

    public void setActivitySpecContent(List<ActivitySpecServerParams.Content> list) {
        mRvAdapter.setData(list);
        mRvAdapter.notifyDataSetChanged();
    }
}
