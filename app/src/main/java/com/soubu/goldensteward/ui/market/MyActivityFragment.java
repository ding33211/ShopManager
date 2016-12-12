package com.soubu.goldensteward.ui.market;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.adapter.SingleAdapter;
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
        SingleAdapter adapter = new SingleAdapter<MyActivityServerParams>(getActivity(), R.layout.item_my_activity_recyclerview) {
            @Override
            protected void bindData(BaseViewHolder holder, MyActivityServerParams item, int position) {
                TextView tvActivityName = holder.getView(R.id.tv_activity_name);
                TextView tvStartTime = holder.getView(R.id.tv_start_time);
                TextView tvEndTime = holder.getView(R.id.tv_end_time);
                ImageView ivActivity = holder.getView(R.id.iv_activity);

            }

            @Override
            protected void bindListener(BaseViewHolder holder, MyActivityServerParams item, int position) {
                TextView tvWatchSpec = holder.getView(R.id.tv_watch_spec);
                tvWatchSpec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), SignUpSpecActivity.class);
                        startActivity(intent);
                    }
                });
            }
        };
        viewDelegate.setAdapter(adapter);
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

}
