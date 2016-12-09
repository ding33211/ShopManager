package com.soubu.goldensteward.ui.information;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.adapter.SingleAdapter;
import com.soubu.goldensteward.support.base.BaseApplication;
import com.soubu.goldensteward.support.bean.InformationRvItem;
import com.soubu.goldensteward.support.utils.GlideUtils;

/**
 * Created by dingsigang on 16-12-8.
 */

public class InformationSingleRvAdapter extends SingleAdapter<InformationRvItem> {

    public static final int TYPE_ITEM_CAN_CHOOSE = 0x00;  //可选
    public static final int TYPE_ITEM_CAN_NOT_CHOOSE = 0x01;  //不可选
    public static final int TYPE_ITEM_CONTENT_MULTILINE = 0x02;  //内容多行
    public static final int TYPE_ITEM_AVATAR = 0x03;  //头像展示
    public static final int TYPE_ITEM_COMPANY_PROFILE = 0x04;  //公司简介单独一个类型



    public InformationSingleRvAdapter(Context context) {
        super(context, R.layout.item_information_recyclerview);
    }

    @Override
    protected void bindData(BaseViewHolder holder, InformationRvItem item, int position) {
        View vChoose = holder.getView(R.id.iv_choose);
        ImageView ivAvatar = holder.getView(R.id.iv_avatar);
        TextView tvContent = holder.getView(R.id.tv_content);
        View vMultiLineContent = holder.getView(R.id.tv_multiline_content);
        vChoose.setVisibility(View.VISIBLE);
        ivAvatar.setVisibility(View.VISIBLE);
        tvContent.setVisibility(View.VISIBLE);
        vMultiLineContent.setVisibility(View.GONE);
        int itemType = data.get(position).getItemType();
        switch (itemType) {
            case TYPE_ITEM_CAN_NOT_CHOOSE:
                vChoose.setVisibility(View.GONE);
            case TYPE_ITEM_CAN_CHOOSE:
                ivAvatar.setVisibility(View.GONE);
                break;
            case TYPE_ITEM_AVATAR:
                tvContent.setVisibility(View.GONE);
                break;
            case TYPE_ITEM_COMPANY_PROFILE:
                vMultiLineContent.setVisibility(View.VISIBLE);
                ivAvatar.setVisibility(View.GONE);
                tvContent.setVisibility(View.GONE);
                break;
            case TYPE_ITEM_CONTENT_MULTILINE:
                vMultiLineContent.setVisibility(View.VISIBLE);
                vChoose.setVisibility(View.GONE);
                ivAvatar.setVisibility(View.GONE);
                tvContent.setVisibility(View.GONE);
                break;
        }
        TextView tvTitle = holder.getView(R.id.tv_title);
        TextView tvMultiLineContent = holder.getView(R.id.tv_multiline_content);
        View vItemBottomLine = holder.getView(R.id.v_bottom_line);
        View vMarginTop = holder.getView(R.id.v_margin_top);

        vItemBottomLine.setVisibility(View.VISIBLE);
        if (holder.getLayoutPosition() == getItemCount() - 1 || getItemViewType(holder.getLayoutPosition() + 1) == TYPE_ITEM_COMPANY_PROFILE
                || getItemViewType(holder.getLayoutPosition() + 1) == TYPE_ITEM_COMPANY_PROFILE) {
            vItemBottomLine.setVisibility(View.INVISIBLE);
        }
        //顶部空白需要不同的情况做不同适配
        switch (item.getTitleRes()) {
            case R.string.store_name:
            case R.string.company_logo:
            case R.string.company_profile:
                vMarginTop.setVisibility(View.VISIBLE);
                break;
            default:
                vMarginTop.setVisibility(View.GONE);
                break;
        }
        tvTitle.setText(item.getTitleRes());
        String content = item.getContent();
        if (item.getArrayRes() != 0) {
            tvContent.setText(BaseApplication.getContext().getResources().getTextArray(item.getArrayRes())[Integer.valueOf(content) - 1]);
        } else {
            tvContent.setText(content);
        }
        tvMultiLineContent.setText(item.getContent());
        if (itemType == TYPE_ITEM_AVATAR) {
            if (!TextUtils.isEmpty(content)) {
                GlideUtils.loadRoundedImage(BaseApplication.getContext(), ivAvatar, content, R.drawable.common_header, R.drawable.common_header);
            }
        }

    }
}
