package com.soubu.goldensteward.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.module.ProductAccessProductsOnSaleRvItem;

/**
 * Created by lakers on 16/10/25.
 */

public class ProductAccessProductsOnSaleRvAdapater extends BaseRecyclerViewAdapter<ProductAccessProductsOnSaleRvItem> {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_access_product_on_sale_recyclerview, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        ImageView ivProductImg;
        TextView tvProductName;
        TextView tvUnitPrice;
        TextView tvBrowse;
        TextView tvCollection;
        TextView tvTime;
        TextView tvCustomerService;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ivProductImg = (ImageView) itemView.findViewById(R.id.iv_product);
            tvProductName = (TextView) itemView.findViewById(R.id.tv_name);
            tvUnitPrice = (TextView) itemView.findViewById(R.id.tv_unit_price);
            tvBrowse = (TextView) itemView.findViewById(R.id.tv_browser_volume);
            tvCollection = (TextView) itemView.findViewById(R.id.tv_collection_volume);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvCustomerService = (TextView) itemView.findViewById(R.id.tv_customer_service);
        }
    }
}
