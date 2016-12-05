package com.soubu.goldensteward.market;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.adapter.BaseRecyclerViewAdapter;
import com.soubu.goldensteward.module.server.AllActivityServerParams;
import com.soubu.goldensteward.utils.ConvertUtil;
import com.soubu.goldensteward.utils.GlideUtils;

import java.util.Date;

/**
 * Created by dingsigang on 16-12-5.
 */

public class AllActivityRvAdapter extends BaseRecyclerViewAdapter<AllActivityServerParams> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_activity_recyclerview, parent, false);
        View vBottom = v.findViewById(R.id.v_bottom_line);
        if (viewType == TYPE_FOOTER) {
            vBottom.setVisibility(View.VISIBLE);
        } else {
            vBottom.setVisibility(View.GONE);
        }
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder holder1 = (ItemViewHolder) holder;
            AllActivityServerParams params = mList.get(position);
            holder1.tvActivityName.setText(params.getName());
            holder1.tvStartTime.setText(ConvertUtil.dateToYYYY_MM_DD_HH_mm(new Date(Long.valueOf(params.getBegin_time()) * 1000)));
            holder1.tvEndTime.setText(ConvertUtil.dateToYYYY_MM_DD_HH_mm(new Date(Long.valueOf(params.getEnd_time()) * 1000)));
            GlideUtils.loadTopRoundedImage(holder1.ivActivity.getContext(), holder1.ivActivity, params.getUrl(), R.drawable.preview_bg, R.drawable.preview_bg);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ONLY;
        }
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
