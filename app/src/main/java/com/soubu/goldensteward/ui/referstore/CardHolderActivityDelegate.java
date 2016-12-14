package com.soubu.goldensteward.ui.referstore;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.SingleAdapter;
import com.soubu.goldensteward.support.bean.server.CardHolderServerParams;
import com.soubu.goldensteward.support.mvp.view.AppDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-12-12.
 */

public class CardHolderActivityDelegate extends AppDelegate {
    RecyclerView mRvContent;
    SingleAdapter mAdapter;

    @Override
    public void initWidget() {
        super.initWidget();
        mRvContent = get(R.id.rv_content);
        mRvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void setCardHolderAdapter(SingleAdapter adapter){
        mAdapter= adapter;
        mRvContent.setAdapter(mAdapter);
    }

    public void setData(List<CardHolderServerParams> list){
        List<CardHolderServerParams> list1 = new ArrayList<>();
        CardHolderServerParams params = new CardHolderServerParams();
        params.setName("发发发");
        List<CardHolderServerParams.Tag> list2 = new ArrayList<>();
        CardHolderServerParams.Tag tag = params.new Tag();
        tag.setName("dada");
        tag.setType(0);
        list2.add(tag);
        params.setTag(list2);
        params.setContact_status(0);
        params.setCompany("dadada");
        list1.add(params);
        mAdapter.setData(list1);
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_card_holder;
    }
}
