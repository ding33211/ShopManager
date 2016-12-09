package com.soubu.goldensteward.ui.referstore;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.adapter.SingleAdapter;
import com.soubu.goldensteward.support.utils.ConvertUtil;
import com.soubu.goldensteward.ui.home.HomeGridViewAdapter;
import com.soubu.goldensteward.support.mvp.view.AppDelegate;
import com.soubu.goldensteward.support.utils.GlideUtils;
import com.soubu.goldensteward.support.widget.recyclerviewdecoration.DividerItemDecoration;

import java.util.Date;
import java.util.List;

/**
 * Created by lakers on 16/10/31.
 */

public class SubAccountActivityDelegate extends AppDelegate {

    HomeGridViewAdapter mAdapter;
    SingleAdapter mRecyclerAdapter;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_sub_account_spec;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mAdapter = new HomeGridViewAdapter(this.getActivity());
        ((GridView) get(R.id.gv_container)).setAdapter(mAdapter);
        mRecyclerAdapter = new SingleAdapter<String>(getActivity(), R.layout.item_logintime_recyclerview) {
            @Override
            protected void bindData(BaseViewHolder holder, String item, int position) {
                TextView tvContent = holder.getView(R.id.tv_content);
                tvContent.setText(ConvertUtil.dateToYYYY_MM_DD_HH_mm(new Date(Long.valueOf(item) * 1000)));
            }
        };
        RecyclerView rvContent = get(R.id.rv_content);
        rvContent.setAdapter(mRecyclerAdapter);
        rvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvContent.addItemDecoration(new DividerItemDecoration(this.getActivity(), LinearLayoutManager.VERTICAL, 2));
    }

    public void setTop(String avatarUrl, String name, String phone) {
        GlideUtils.loadRoundedImage(this.getActivity(), (ImageView) get(R.id.iv_avatar), avatarUrl, R.drawable.common_header, R.drawable.common_header);
        ((TextView) get(R.id.tv_name)).setText(name);
        ((TextView) get(R.id.tv_phone_num)).setText(phone);
    }

    public void setTodayData(List<Integer> iconList, List<String> titleList, List<String> subTitleList) {
        mAdapter.setData(iconList, titleList, subTitleList);
    }

    public void setLoginList(List<String> list) {
        mRecyclerAdapter.setData(list);
        mRecyclerAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean ifNeedEventBus() {
        return true;
    }
}
