package com.soubu.goldensteward.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.module.TransactionRecordModule;

/**
 * Created by lakers on 16/10/31.
 */

public class TransactionRecordRvAdapter extends BaseRecyclerViewAdapter<TransactionRecordModule> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction_record_recyclerview, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }


    class ItemViewHolder extends  RecyclerView.ViewHolder {

        TextView tvState;
        TextView tvTime;
        ImageView ivProduct;
        TextView tvProduct;
        TextView tvPrice;
        TextView tvCustomerService;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvState = (TextView) itemView.findViewById(R.id.tv_state);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            ivProduct = (ImageView) itemView.findViewById(R.id.iv_product);
            tvProduct = (TextView) itemView.findViewById(R.id.tv_product_name);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvCustomerService = (TextView) itemView.findViewById(R.id.tv_customer_service);
        }
    }
}
