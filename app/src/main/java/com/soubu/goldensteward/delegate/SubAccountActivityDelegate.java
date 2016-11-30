package com.soubu.goldensteward.delegate;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.adapter.HomeGridViewAdapter;
import com.soubu.goldensteward.adapter.LoginTimeRvAdapter;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;
import com.soubu.goldensteward.utils.GlideUtils;
import com.soubu.goldensteward.widget.recyclerviewdecoration.DividerItemDecoration;

import java.util.List;

/**
 * Created by lakers on 16/10/31.
 */

public class SubAccountActivityDelegate extends AppDelegate {

    HomeGridViewAdapter mAdapter;
    LoginTimeRvAdapter mRecyclerAdapter;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_sub_account_spec;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mAdapter = new HomeGridViewAdapter(this.getActivity());
        ((GridView) get(R.id.gv_container)).setAdapter(mAdapter);
        mRecyclerAdapter = new LoginTimeRvAdapter();
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
