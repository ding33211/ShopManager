package com.soubu.goldensteward.ui.market;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.base.BaseRecyclerViewAdapter;
import com.soubu.goldensteward.support.bean.server.ActivitySpecServerParams;

/**
 * Created by dingsigang on 16-12-6.
 */

public class ActivitySpecRvAdapter extends BaseRecyclerViewAdapter<ActivitySpecServerParams.Content> {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_spec_recyclerview, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder holder1 = (ItemViewHolder) holder;
            ActivitySpecServerParams.Content params = mList.get(position);
            holder1.tvTitle.setText(params.getTitle());
            holder1.tvContent.setText(params.getContent());
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvTitle;
        public TextView tvContent;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onClick(getLayoutPosition());
            }
        }
    }
}
