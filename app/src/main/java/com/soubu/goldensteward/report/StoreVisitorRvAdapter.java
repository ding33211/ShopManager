package com.soubu.goldensteward.report;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.adapter.BaseRecyclerViewAdapter;
import com.soubu.goldensteward.module.server.StoreVisitorServerParams;
import com.soubu.goldensteward.utils.GlideUtils;

/**
 * Created by dingsigang on 16-11-30.
 */

public class StoreVisitorRvAdapter extends BaseRecyclerViewAdapter<StoreVisitorServerParams> {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store_visitor_recyclerview, parent, false);
        if (viewType == TYPE_FOOTER) {
            View vBottom = v.findViewById(R.id.v_bottom_line);
            vBottom.setVisibility(View.GONE);
        }
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder holder1 = (ItemViewHolder) holder;
            StoreVisitorServerParams params = mList.get(position);
            holder1.tvName.setText(params.getName());
            holder1.tvBrowse.setText(params.getBrowse());
            holder1.tvVisit.setText(params.getVisit());
            GlideUtils.loadImage(holder1.ivStore.getContext(), holder1.ivStore, params.getUrl(), R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_ONLY;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvBrowse;
        TextView tvVisit;
        ImageView ivStore;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvBrowse = (TextView) itemView.findViewById(R.id.tv_browse_num);
            tvVisit = (TextView) itemView.findViewById(R.id.tv_store_visit_num);
            ivStore = (ImageView) itemView.findViewById(R.id.iv_store);
        }
    }
}
