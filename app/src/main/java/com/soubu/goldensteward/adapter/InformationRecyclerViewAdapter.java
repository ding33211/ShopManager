package com.soubu.goldensteward.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.module.RecyclerViewItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-10-19.
 */
public class InformationRecyclerViewAdapter extends RecyclerView.Adapter {
    public static final int TYPE_ITEM_CAN_CHOOSE = 0x00;  //可选
    public static final int TYPE_ITEM_CAN_NOT_CHOOSE = 0x01;  //不可选
    public static final int TYPE_ITEM_CONTENT_MULTILINE = 0x02;  //内容多行
    public static final int TYPE_ITEM_AVATAR = 0x03;  //头像展示
    public static final int TYPE_ITEM_COMPANY_PROFILE = 0x04;  //公司简介单独一个类型

    private List<RecyclerViewItem> mList;

    public InformationRecyclerViewAdapter() {
        mList = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getItemType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_information_recyclerview, parent, false);
        View vChoose = v.findViewById(R.id.iv_choose);
        View vAvatar = v.findViewById(R.id.iv_avatar);
        View vContent = v.findViewById(R.id.tv_content);
        View vMarginTop = v.findViewById(R.id.v_margin_top);
        View vMultiLineContent = v.findViewById(R.id.tv_multiline_content);
        switch (viewType) {
            case TYPE_ITEM_CAN_NOT_CHOOSE:
                vChoose.setVisibility(View.GONE);
            case TYPE_ITEM_CAN_CHOOSE:
                vAvatar.setVisibility(View.GONE);
                break;
            case TYPE_ITEM_AVATAR:
                vContent.setVisibility(View.GONE);
                vMarginTop.setVisibility(View.VISIBLE);
                break;
            case TYPE_ITEM_COMPANY_PROFILE:
                vMarginTop.setVisibility(View.VISIBLE);
            case TYPE_ITEM_CONTENT_MULTILINE:
                vMultiLineContent.setVisibility(View.VISIBLE);
                vChoose.setVisibility(View.GONE);
                vAvatar.setVisibility(View.GONE);
                vContent.setVisibility(View.GONE);
                break;

        }
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder holder1 = (ItemViewHolder) holder;
        holder1.vItemBottomLine.setVisibility(View.VISIBLE);
        if (holder.getLayoutPosition() == getItemCount() - 1 || getItemViewType(holder.getLayoutPosition() + 1) == TYPE_ITEM_COMPANY_PROFILE
                || getItemViewType(holder.getLayoutPosition() + 1) == TYPE_ITEM_COMPANY_PROFILE) {
            holder1.vItemBottomLine.setVisibility(View.INVISIBLE);
        }
        holder1.tvTitle.setText(mList.get(position).getTitleRes());
        holder1.tvContent.setText(mList.get(position).getContent());
        holder1.tvMultiLineContent.setText(mList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setData(List<RecyclerViewItem> list) {
        if (!mList.isEmpty()) {
            mList.clear();
        }
        mList.addAll(list);
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvTitle;
        public TextView tvContent;
        public TextView tvMultiLineContent;
        public ImageView ivAvatar;
        public View vItemBottomLine;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ivAvatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            tvMultiLineContent = (TextView) itemView.findViewById(R.id.tv_multiline_content);
            vItemBottomLine = itemView.findViewById(R.id.v_bottom_line);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
