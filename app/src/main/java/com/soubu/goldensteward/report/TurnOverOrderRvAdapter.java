package com.soubu.goldensteward.report;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.adapter.BaseRecyclerViewAdapter;
import com.soubu.goldensteward.module.TurnOverOrderRvItem;
import com.soubu.goldensteward.utils.ConvertUtil;
import com.soubu.goldensteward.utils.GlideUtils;
import com.soubu.goldensteward.utils.RegularUtil;

import java.util.Date;

/**
 * Created by lakers on 16/10/25.
 */
public class TurnOverOrderRvAdapter extends BaseRecyclerViewAdapter<TurnOverOrderRvItem> {

//    private String[] mOrderStatus;
//    private String[] mFreight;
//    private String[] mProductType;

    public TurnOverOrderRvAdapter(Context context) {
//        mOrderStatus = context.getResources().getStringArray(R.array.order_state);
//        mFreight = context.getResources().getStringArray(R.array.freight_state);
//        mProductType = context.getResources().getStringArray(R.array.product_type);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_footer_in_report, parent, false);
            return new FooterViewHolder(v);
        }
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_turnover_order_recyclerview, parent, false);
        View vTop = v.findViewById(R.id.ll_top);
        View vBot = v.findViewById(R.id.ll_bot);
        switch (viewType) {
            case TYPE_TOP:
                vBot.setVisibility(View.GONE);
                break;
            case TYPE_MID:
                vTop.setVisibility(View.GONE);
                vBot.setVisibility(View.GONE);
                break;
            case TYPE_BOT:
                vTop.setVisibility(View.GONE);
                break;
        }
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder holder1 = (ItemViewHolder) holder;
            TurnOverOrderRvItem item = mList.get(position);
            GlideUtils.loadRoundedImage(holder1.ivProductImage.getContext(), holder1.ivProductImage, item.getPic(), R.drawable.common_product_placeholder, R.drawable.common_product_placeholder);
            holder1.tvOrderState.setText(item.getStatus());
            holder1.tvTime.setText(ConvertUtil.dateToYYYY_MM_DD_HH_mm_ss(new Date(Long.valueOf(item.getTime()) * 1000)));
            holder1.tvCompany.setText(item.getName() + "(" + item.getConsignee() + ")");
            holder1.tvPhone.setText(item.getPhone());
            holder1.tvAddress.setText(item.getProvince() + "  " + item.getCity());
//            holder1.tvForWhat.setText(mProductType[Integer.valueOf(item.getType()) - 1]);
            holder1.tvForWhat.setText(item.getType());
            holder1.tvUnit.setText(item.getPrice());
            holder1.tvPrice.setText(item.getSum_price());
//            holder1.tvPostageMode.setText(mFreight[Integer.valueOf(item.getFreight()) - 1]);
            holder1.vShipFee.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(item.getFreight())) {
                holder1.tvPostageMode.setText(item.getFreight());
                holder1.vShipFee.setVisibility(View.VISIBLE);
            }
            holder1.tvRefundState.setText(item.getSec_status());
            holder1.tvTotal.setText(item.getP_count());
            holder1.vDiscount.setVisibility(View.GONE);
            if (RegularUtil.isInteger(item.getDiscount())) {
                int discount = Integer.valueOf(item.getDiscount());
                if (discount > 1) {
                    holder1.vDiscount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            if (isShowFooter()) {
                return TYPE_FOOTER;
            }
        }
        return mList.get(position).getRvType();
    }

    @Override
    public boolean isShowFooter() {
        return true;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvOrderState;
        TextView tvTime;
        ImageView ivProductImage;
        TextView tvCompany;
        TextView tvPhone;
        TextView tvAddress;
        TextView tvForWhat;
        TextView tvUnit;
        TextView tvAmount;
        TextView tvTotal;
        TextView tvPostageMode;
        TextView tvPrice;
        TextView tvRefundState;
        TextView tvCustomerService;
        View vShipFee;
        View vDiscount;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvOrderState = (TextView) itemView.findViewById(R.id.tv_order_state);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            ivProductImage = (ImageView) itemView.findViewById(R.id.iv_product);
            tvOrderState = (TextView) itemView.findViewById(R.id.tv_order_state);
            tvCompany = (TextView) itemView.findViewById(R.id.tv_company);
            tvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_address);
            tvForWhat = (TextView) itemView.findViewById(R.id.tv_for_what);
            tvUnit = (TextView) itemView.findViewById(R.id.tv_unit);
            tvAmount = (TextView) itemView.findViewById(R.id.tv_amount);
            tvTotal = (TextView) itemView.findViewById(R.id.tv_total);
            tvPostageMode = (TextView) itemView.findViewById(R.id.tv_postage_mode);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvRefundState = (TextView) itemView.findViewById(R.id.tv_refund_state);
            tvCustomerService = (TextView) itemView.findViewById(R.id.tv_customer_service);
            vShipFee = itemView.findViewById(R.id.ll_ship_fee);
            vDiscount = itemView.findViewById(R.id.iv_discount);
        }
    }


}
