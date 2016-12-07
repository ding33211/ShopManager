package com.soubu.goldensteward.ui.market;

import android.content.Intent;

import com.soubu.goldensteward.support.base.BaseRecyclerViewAdapter;
import com.soubu.goldensteward.support.bean.server.AllActivityServerParams;
import com.soubu.goldensteward.support.delegate.RecyclerViewFragmentDelegate;
import com.soubu.goldensteward.support.mvp.presenter.FragmentPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-12-6.
 */

public class AllActivityFragment extends FragmentPresenter<RecyclerViewFragmentDelegate> {
    @Override
    protected Class<RecyclerViewFragmentDelegate> getDelegateClass() {
        return RecyclerViewFragmentDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        viewDelegate.setAdapter(new AllActivityRvAdapter());
        List<AllActivityServerParams> list = new ArrayList<>();
        AllActivityServerParams params = new AllActivityServerParams();
        params.setUrl("https://www.baidu.com/img/bd_logo1.png");
        params.setName("baidu");
        params.setBegin_time(System.currentTimeMillis() / 1000 + "");
        params.setEnd_time(System.currentTimeMillis() / 1000 + 3600000 + "");
        list.add(params);
        params = new AllActivityServerParams();
        params.setUrl("https://gss0.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/lbs/pic/item/7dd98d1001e939010f5ffd0672ec54e736d196ac.jpg");
        params.setName("baidu");
        params.setBegin_time(System.currentTimeMillis() / 1000 + "");
        params.setEnd_time(System.currentTimeMillis() / 1000 + 3600000 + "");
        list.add(params);
        params = new AllActivityServerParams();
        params.setUrl("https://www.baidu.com/img/bd_logo1.png");
        params.setName("baidu");
        params.setBegin_time(System.currentTimeMillis() / 1000 + "");
        params.setEnd_time(System.currentTimeMillis() / 1000 + 3600000 + "");
        list.add(params);
        params = new AllActivityServerParams();
        params.setUrl("https://gss0.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/lbs/pic/item/7dd98d1001e939010f5ffd0672ec54e736d196ac.jpg");
        params.setName("baidu");
        params.setBegin_time(System.currentTimeMillis() / 1000 + "");
        params.setEnd_time(System.currentTimeMillis() / 1000 + 3600000 + "");
        list.add(params);
        params = new AllActivityServerParams();
        params.setUrl("https://www.baidu.com/img/bd_logo1.png");
        params.setName("baidu");
        params.setBegin_time(System.currentTimeMillis() / 1000 + "");
        params.setEnd_time(System.currentTimeMillis() / 1000 + 3600000 + "");
        list.add(params);
        params = new AllActivityServerParams();
        params.setUrl("https://www.baidu.com/img/bd_logo1.png");
        params.setName("baidu");
        params.setBegin_time(System.currentTimeMillis() / 1000 + "");
        params.setEnd_time(System.currentTimeMillis() / 1000 + 3600000 + "");
        list.add(params);
        viewDelegate.setData(list);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setRvItemOnClickListener(new BaseRecyclerViewAdapter.OnRvItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), ActivitySpecActivity.class);
                startActivity(intent);
            }
        });
    }
}
