package com.soubu.goldensteward.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.module.server.ProductInCustomerDetailServerParams;
import com.soubu.goldensteward.utils.ConvertUtil;
import com.soubu.goldensteward.utils.GlideUtils;

import java.util.Date;

/**
 * Created by lakers on 16/10/31.
 */

public class TransactionRecordRvAdapter extends BaseRecyclerViewAdapter<ProductInCustomerDetailServerParams> {

    private String[] mStates;

    public TransactionRecordRvAdapter(Context context) {
        mStates = context.getResources().getStringArray(R.array.order_state);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType != TYPE_FOOTER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction_record_recyclerview, parent, false);
            return new ItemViewHolder(v);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_recyclerview_footer, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder holder1 = (ItemViewHolder) holder;
            ProductInCustomerDetailServerParams params = mList.get(position);
            holder1.tvState.setText(mStates[Integer.valueOf(params.getStatus()) - 1]);
            holder1.tvTime.setText(params.getAdd_time() == null ? "" : ConvertUtil.dateToYYYY_MM_DD_HH_mm_ss(new Date(Long.valueOf(params.getAdd_time()) * 1000)));
            holder1.tvPrice.setText(params.getPrice());
            GlideUtils.loadRoundedImage(holder1.ivProduct.getContext(), holder1.ivProduct, params.getPic(), R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            holder1.tvProduct.setText(params.getPname());
        }
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

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

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            if (isShowFooter()) {
                return TYPE_FOOTER;
            }
        }
        return TYPE_ONLY;
    }


}
