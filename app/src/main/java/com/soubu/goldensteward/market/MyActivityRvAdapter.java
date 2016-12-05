package com.soubu.goldensteward.market;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.adapter.BaseRecyclerViewAdapter;
import com.soubu.goldensteward.module.server.MyActivityServerParams;

/**
 * Created by dingsigang on 16-12-5.
 */

public class MyActivityRvAdapter extends BaseRecyclerViewAdapter<MyActivityServerParams> {


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_activity_recyclerview, parent, false);

        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvActivityName;
        public TextView tvStartTime;
        public TextView tvEndTime;
        public ImageView ivActivity;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvActivityName = (TextView) itemView.findViewById(R.id.tv_activity_name);
            tvStartTime = (TextView) itemView.findViewById(R.id.tv_start_time);
            tvEndTime = (TextView) itemView.findViewById(R.id.tv_end_time);
            ivActivity = (ImageView) itemView.findViewById(R.id.iv_activity);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
