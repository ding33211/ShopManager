package com.soubu.goldensteward.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.module.TurnOverOrderRvItem;

/**
 * Created by lakers on 16/10/25.
 */
public class TurnOverOrderRvAdapter extends BaseRecyclerViewAdapter<TurnOverOrderRvItem>
{
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_turnover_order_recyclerview, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }


    class ItemViewHolder extends RecyclerView.ViewHolder{

        TextView tvOrderState;
        TextView tvTime;
        ImageView ivProductImage;
        TextView tvCompany;
        TextView tvPhone;
        TextView tvAddress;
        TextView tvForWhat;
        TextView tvUnit;
        TextView tvAmount;
        TextView tvProductAmountDesc;
        TextView tvPostageMode;
        TextView tvPrice;
        TextView tvRefundState;
        TextView tvCustomerService;


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
            tvProductAmountDesc = (TextView) itemView.findViewById(R.id.tv_amount_desc);
            tvPostageMode = (TextView) itemView.findViewById(R.id.tv_postage_mode);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvRefundState = (TextView) itemView.findViewById(R.id.tv_refund_state);
            tvCustomerService = (TextView) itemView.findViewById(R.id.tv_customer_service);

        }
    }



}
