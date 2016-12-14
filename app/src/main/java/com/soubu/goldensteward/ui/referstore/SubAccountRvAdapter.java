package com.soubu.goldensteward.ui.referstore;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.adapter.SingleAdapter;
import com.soubu.goldensteward.support.bean.server.SubAccountServerParams;
import com.soubu.goldensteward.support.utils.GlideUtils;
import com.soubu.goldensteward.support.widget.RecyclerViewFastScroller;

/**
 * Created by lakers on 16/10/31.
 */

public class SubAccountRvAdapter extends SingleAdapter<SubAccountServerParams> implements RecyclerViewFastScroller.BubbleTextGetter {

    public SubAccountRvAdapter(Context context) {
        super(context, R.layout.item_sub_account_recyclerview);
    }

    @Override
    public String getTextToShowInBubble(int pos) {
        return data.get(pos).getLetter();
    }

    @Override
    protected void bindData(BaseViewHolder holder, SubAccountServerParams item, int position) {
        String thisLetter = item.getLetter();
        String lastLetter = null;
        String nextLetter = null;
        View vLine = holder.getView(R.id.v_bottom_line);
        View vLetter = holder.getView(R.id.ll_letter);
        vLine.setVisibility(View.VISIBLE);
        vLetter.setVisibility(View.VISIBLE);
        if (position != 0) {
            lastLetter = getData().get(position - 1).getLetter();
        }
        if (position != getItemCount() - 1) {
            nextLetter = getData().get(position + 1).getLetter();
        }
        if (TextUtils.equals(thisLetter, lastLetter)) {
            if (TextUtils.equals(thisLetter, nextLetter)) {
                vLetter.setVisibility(View.GONE);
            } else {
                vLetter.setVisibility(View.GONE);
                vLine.setVisibility(View.GONE);
            }
        } else {
            if (TextUtils.equals(thisLetter, nextLetter)) {
            } else {
                vLine.setVisibility(View.GONE);
            }
        }
        TextView tvName = holder.getView(R.id.tv_name);
        TextView tvLetter = holder.getView(R.id.tv_letter);
        ImageView ivAvatar = holder.getView(R.id.iv_avatar);
        tvName.setText(item.getName());
        tvLetter.setText(item.getLetter());
        tvName.setTextColor(tvLetter.getResources().getColor(R.color.title_black));
        GlideUtils.loadRoundedImage(ivAvatar.getContext(), ivAvatar, item.getPortrait(), R.drawable.common_header, R.drawable.common_header);
    }


}
