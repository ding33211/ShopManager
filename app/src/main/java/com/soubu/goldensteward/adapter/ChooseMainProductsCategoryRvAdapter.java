package com.soubu.goldensteward.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soubu.goldensteward.R;

/**
 * Created by lakers on 16/10/29.
 */

public class ChooseMainProductsCategoryRvAdapter extends BaseRecyclerViewAdapter<String> {

    private int mSelectPosition = 0;


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose_main_products_category_recyclerview, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder holder1 = (ItemViewHolder) holder;
            holder1.tvContent.setText(mList.get(position));
            if (mSelectPosition == position) {
                holder1.vItem.setBackgroundResource(R.color.colorPrimary);
                holder1.tvContent.setTextColor(holder1.tvContent.getResources().getColor(android.R.color.white));
            } else {
                holder1.vItem.setBackgroundResource(android.R.color.transparent);
                holder1.tvContent.setTextColor(holder1.tvContent.getResources().getColor(R.color.subtitle_grey));
            }
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvContent;
        View vItem;

        public ItemViewHolder(View itemView) {
            super(itemView);
            vItem = itemView;
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            notifyItemChanged(mSelectPosition);
            mSelectPosition = getLayoutPosition();
            notifyItemChanged(mSelectPosition);
            if (mListener != null) {
                mListener.onClick(mSelectPosition);
            }

        }
    }

}
