package com.soubu.goldensteward.ui.market;

import android.content.Intent;

import com.soubu.goldensteward.support.base.BaseRecyclerViewAdapter;
import com.soubu.goldensteward.support.bean.server.MyActivityServerParams;
import com.soubu.goldensteward.support.delegate.RecyclerViewFragmentDelegate;
import com.soubu.goldensteward.support.mvp.presenter.FragmentPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-12-6.
 */

public class MyActivityFragment extends FragmentPresenter<RecyclerViewFragmentDelegate> {
    @Override
    protected Class<RecyclerViewFragmentDelegate> getDelegateClass() {
        return RecyclerViewFragmentDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        viewDelegate.setAdapter(new MyActivityRvAdapter());
        List<MyActivityServerParams> list = new ArrayList<>();
        MyActivityServerParams params = new MyActivityServerParams();
        params.setName("baidu");
        params.setSignUpEndTime(System.currentTimeMillis() / 1000 + "");
        params.setEndTime(System.currentTimeMillis() / 1000 + 3600000 + "");
        params.setStartTime(System.currentTimeMillis() / 1000 + "");
        list.add(params);
        params = new MyActivityServerParams();
        params.setName("baidu");
        params.setSignUpEndTime(System.currentTimeMillis() / 1000 + "");
        params.setEndTime(System.currentTimeMillis() / 1000 + 3600000 + "");
        params.setStartTime(System.currentTimeMillis() / 1000 + "");
        list.add(params);
        params = new MyActivityServerParams();
        params.setName("baidu");
        params.setSignUpEndTime(System.currentTimeMillis() / 1000 + "");
        params.setEndTime(System.currentTimeMillis() / 1000 + 3600000 + "");
        params.setStartTime(System.currentTimeMillis() / 1000 + "");
        list.add(params);
        params = new MyActivityServerParams();
        params.setName("baidu");
        params.setSignUpEndTime(System.currentTimeMillis() / 1000 + "");
        params.setEndTime(System.currentTimeMillis() / 1000 + 3600000 + "");
        params.setStartTime(System.currentTimeMillis() / 1000 + "");
        list.add(params);
        params = new MyActivityServerParams();
        params.setName("baidu");
        params.setSignUpEndTime(System.currentTimeMillis() / 1000 + "");
        params.setEndTime(System.currentTimeMillis() / 1000 + 3600000 + "");
        params.setStartTime(System.currentTimeMillis() / 1000 + "");
        list.add(params);
        params = new MyActivityServerParams();
        params.setName("baidu");
        params.setSignUpEndTime(System.currentTimeMillis() / 1000 + "");
        params.setEndTime(System.currentTimeMillis() / 1000 + 3600000 + "");
        params.setStartTime(System.currentTimeMillis() / 1000 + "");
        list.add(params);
        viewDelegate.setData(list);
        viewDelegate.setDecorationHeight(10);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setRvItemOnClickListener(new BaseRecyclerViewAdapter.OnRvItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), SignUpSpecActivity.class);
                startActivity(intent);
            }
        });
    }
}
