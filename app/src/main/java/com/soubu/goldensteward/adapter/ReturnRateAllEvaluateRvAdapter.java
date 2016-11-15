package com.soubu.goldensteward.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.module.ReturnRateAllEvaluateRvItem;

/**
 * Created by lakers on 16/10/25.
 */

public class ReturnRateAllEvaluateRvAdapter extends BaseRecyclerViewAdapter<ReturnRateAllEvaluateRvItem> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_footer_in_report, parent, false);
            return new FooterViewHolder(v);
        }
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_return_rate_all_evaluate_recyclerview, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

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

    @Override
    public boolean isShowFooter() {
        return true;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView ivCompanyIcon;
        TextView tvName;
        ImageView ivVip;
        ImageView ivShopType;
        ImageView ivShopSpec;
        TextView tvContent;
        TextView tvTime;
        TextView tvCustomerService;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ivCompanyIcon = (ImageView) itemView.findViewById(R.id.iv_company_icon);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            ivVip = (ImageView) itemView.findViewById(R.id.iv_vip);
            ivShopType = (ImageView) itemView.findViewById(R.id.iv_shop_type);
            ivShopSpec = (ImageView) itemView.findViewById(R.id.iv_shop_spec);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvCustomerService = (TextView) itemView.findViewById(R.id.tv_customer_service);
        }


    }
}
