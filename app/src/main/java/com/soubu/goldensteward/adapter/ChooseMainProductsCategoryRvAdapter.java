package com.soubu.goldensteward.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;

import java.util.List;

/**
 * Created by lakers on 16/10/29.
 */

public class ChooseMainProductsCategoryRvAdapter extends BaseRecyclerViewAdapter<String> {

    private int mSelectPosition = 0;

    private List<Integer> mSelectedList;


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
            holder1.ivSelected.setVisibility(View.GONE);
            if (mSelectedList.contains(position)) {
                holder1.ivSelected.setImageResource(R.drawable.login_product_checked);
                holder1.ivSelected.setVisibility(View.VISIBLE);
            }
            if (mSelectPosition == position) {
                holder1.vItem.setBackgroundResource(R.color.colorPrimary);
                holder1.tvContent.setTextColor(holder1.tvContent.getResources().getColor(android.R.color.white));
                holder1.ivSelected.setImageResource(R.drawable.login_product_checking);
                holder1.ivSelected.setVisibility(View.VISIBLE);
            } else {
                holder1.vItem.setBackgroundResource(android.R.color.transparent);
                holder1.tvContent.setTextColor(holder1.tvContent.getResources().getColor(R.color.subtitle_grey));
            }
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvContent;
        ImageView ivSelected;
        View vItem;

        public ItemViewHolder(View itemView) {
            super(itemView);
            vItem = itemView;
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            ivSelected = (ImageView) itemView.findViewById(R.id.iv_select);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                if(!mListener.onClick(getLayoutPosition())){
                    return;
                }
            }
            mSelectPosition = getLayoutPosition();
            notifyDataSetChanged();
        }
    }


    public void setSelectedList(List<Integer> list) {
        mSelectedList = list;
    }

    OnCanGoOnClickListener mListener;

    public interface OnCanGoOnClickListener {
        //由于涉及到还有不能点击的情况，所以讲返回类型设置为布尔
        boolean onClick(int position);
    }

    public void setOnRvItemClickListener(OnCanGoOnClickListener listener) {
        mListener = listener;
    }


}
