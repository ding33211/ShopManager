package com.soubu.goldensteward.ui.customer;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.adapter.SingleAdapter;
import com.soubu.goldensteward.support.bean.server.CustomerServerParams;
import com.soubu.goldensteward.support.utils.ConvertUtil;
import com.soubu.goldensteward.support.utils.GlideUtils;
import com.soubu.goldensteward.support.widget.RecyclerViewFastScroller;

import java.util.Date;

/**
 * Created by dingsigang on 16-12-8.
 */

public class MyCustomerSingleRvAdapter extends SingleAdapter<CustomerServerParams> implements RecyclerViewFastScroller.BubbleTextGetter{

    public MyCustomerSingleRvAdapter(Context context) {
        super(context, R.layout.item_my_customer_recycler);
    }

    @Override
    protected void bindData(BaseViewHolder holder, CustomerServerParams item, int position) {
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
        TextView tvDealCount = holder.getView(R.id.tv_traded_order_count);
        TextView tvLastDeal = holder.getView(R.id.tv_last_deal_time);
        ImageView ivAvatar = holder.getView(R.id.iv_avatar);
        TextView tvLetter = holder.getView(R.id.tv_letter);
        tvName.setText(item.getName());
        tvLetter.setText(item.getLetter());
        tvDealCount.setText(item.getOrder_count());
        GlideUtils.loadRoundedImage(ivAvatar.getContext(), ivAvatar, item.getPortrait(), R.drawable.common_header, R.drawable.common_header);
        tvLastDeal.setText(item.getAdd_time() == null ? "" : ConvertUtil.dateToYYYY_MM_DD_HH_mm(new Date(Long.valueOf(item.getAdd_time()) * 1000)).substring(2));
    }

    @Override
    public String getTextToShowInBubble(int pos) {
        return data.get(pos).getLetter();
    }
}
