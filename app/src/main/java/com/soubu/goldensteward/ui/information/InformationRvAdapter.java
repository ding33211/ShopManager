package com.soubu.goldensteward.ui.information;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.support.base.GoldenStewardApplication;
import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.base.BaseRecyclerViewAdapter;
import com.soubu.goldensteward.support.bean.InformationRvItem;
import com.soubu.goldensteward.support.utils.GlideUtils;

/**
 * Created by dingsigang on 16-10-19.
 */
public class InformationRvAdapter extends BaseRecyclerViewAdapter<InformationRvItem> {
    public static final int TYPE_ITEM_CAN_CHOOSE = 0x00;  //可选
    public static final int TYPE_ITEM_CAN_NOT_CHOOSE = 0x01;  //不可选
    public static final int TYPE_ITEM_CONTENT_MULTILINE = 0x02;  //内容多行
    public static final int TYPE_ITEM_AVATAR = 0x03;  //头像展示
    public static final int TYPE_ITEM_COMPANY_PROFILE = 0x04;  //公司简介单独一个类型


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
        View vMultiLineContent = v.findViewById(R.id.tv_multiline_content);
        switch (viewType) {
            case TYPE_ITEM_CAN_NOT_CHOOSE:
                vChoose.setVisibility(View.GONE);
            case TYPE_ITEM_CAN_CHOOSE:
                vAvatar.setVisibility(View.GONE);
                break;
            case TYPE_ITEM_AVATAR:
                vContent.setVisibility(View.GONE);
                break;
            case TYPE_ITEM_COMPANY_PROFILE:
                vMultiLineContent.setVisibility(View.VISIBLE);
                vAvatar.setVisibility(View.GONE);
                vContent.setVisibility(View.GONE);
                break;
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
        InformationRvItem item = mList.get(position);
        //顶部空白需要不同的情况做不同适配
        switch (item.getTitleRes()) {
            case R.string.store_name:
            case R.string.company_logo:
            case R.string.company_profile:
                holder1.vMarginTop.setVisibility(View.VISIBLE);
                break;
            default:
                holder1.vMarginTop.setVisibility(View.GONE);
                break;
        }

        holder1.tvTitle.setText(item.getTitleRes());
        String content = mList.get(position).getContent();
        if (mList.get(position).getArrayRes() != 0) {
            holder1.tvContent.setText(GoldenStewardApplication.getContext().getResources().getTextArray(mList.get(position).getArrayRes())[Integer.valueOf(content) - 1]);
        } else {
            holder1.tvContent.setText(content);
        }
        holder1.tvMultiLineContent.setText(item.getContent());
        if (getItemViewType(position) == TYPE_ITEM_AVATAR) {
            if (!TextUtils.isEmpty(content)) {
                GlideUtils.loadRoundedImage(GoldenStewardApplication.getContext(), holder1.ivAvatar, content, R.drawable.common_header, R.drawable.common_header);
            }
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvTitle;
        public TextView tvContent;
        public TextView tvMultiLineContent;
        public ImageView ivAvatar;
        public View vItemBottomLine;
        public View vMarginTop;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            tvMultiLineContent = (TextView) itemView.findViewById(R.id.tv_multiline_content);
            vItemBottomLine = itemView.findViewById(R.id.v_bottom_line);
            ivAvatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            vMarginTop = itemView.findViewById(R.id.v_margin_top);
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
