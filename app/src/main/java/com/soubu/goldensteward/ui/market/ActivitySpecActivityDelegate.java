package com.soubu.goldensteward.ui.market;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.adapter.SingleAdapter;
import com.soubu.goldensteward.support.bean.server.ActivitySpecServerParams;
import com.soubu.goldensteward.support.mvp.view.AppDelegate;
import com.soubu.goldensteward.support.utils.ConvertUtil;
import com.soubu.goldensteward.support.utils.GlideUtils;
import com.soubu.goldensteward.support.widget.recyclerviewdecoration.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Date;
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

    public void setActivitySpecContent(ActivitySpecServerParams params) {
        TextView tvName = get(R.id.tv_activity_name);
        TextView tvSignStartTime = get(R.id.tv_sign_up_start_time);
        TextView tvSignEndTime = get(R.id.tv_sign_up_end_time);
        TextView tvActivityStartTime = get(R.id.tv_activity_start_time);
        TextView tvActivityEndTime = get(R.id.tv_activity_end_time);
        TextView tvActivityStartYear = get(R.id.tv_activity_start_year);
        TextView tvActivityEndYear = get(R.id.tv_activity_end_year);
        ImageView ivActivity = get(R.id.iv_activity);
        tvName.setText(params.getActive_name());
        tvSignStartTime.setText(params.getSign_up_start_time());
        tvSignEndTime.setText(params.getSign_up_end_time());
        Date start = new Date(Long.valueOf(params.getActive_start_time()) * 1000);
        Date end = new Date(Long.valueOf(params.getActive_end_time()) * 1000);
        tvActivityStartTime.setText(ConvertUtil.dateToMM_DD_HH_mm(start));
        tvActivityEndTime.setText(ConvertUtil.dateToMM_DD_HH_mm(end));
        tvActivityStartYear.setText(ConvertUtil.dateToYYYY(start));
        tvActivityEndYear.setText(ConvertUtil.dateToYYYY(end));
        GlideUtils.loadImage(getActivity(), ivActivity, params.getDetail_img(), R.drawable.bg_no_activity, R.drawable.bg_no_activity);
        List<ActivitySpecServerParams.Content> list = new ArrayList<>();
        ActivitySpecServerParams.Content content = params.new Content();
        content.setTitle(getActivity().getString(R.string.activity_introduce));
        content.setContent(params.getSign_introduce());
        list.add(content);
        content = params.new Content();
        content.setTitle(getActivity().getString(R.string.sign_up_request));
        content.setContent(params.getSign_introduce());
        list.add(content);
        mRvAdapter.setData(list);
        mRvAdapter.notifyDataSetChanged();
    }
}
