package com.soubu.goldensteward.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.module.server.ProductPreviewSeverParams;
import com.soubu.goldensteward.utils.GlideUtils;

/**
 * Created by dingsigang on 16-11-28.
 */

public class ProductPreviewGridRvAdapter extends BaseRecyclerViewAdapter<ProductPreviewSeverParams> {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_preview_grid_recyclerview, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder holder1 = (ItemViewHolder) holder;
            ProductPreviewSeverParams params = mList.get(position);
            GlideUtils.loadImage(holder1.ivProduct.getContext(), holder1.ivProduct, params.getUrl(), R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            holder1.tvTitle.setText(params.getTitle());
            holder1.tvPrice.setText(params.getPrice());
            holder1.tvBrowserCount.setText(params.getBrowser_volume());
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProduct;
        TextView tvTitle;
        TextView tvPrice;
        TextView tvBrowserCount;
        View vRecommend;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ivProduct = (ImageView) itemView.findViewById(R.id.iv_product);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvBrowserCount = (TextView) itemView.findViewById(R.id.tv_browser_count);
            vRecommend = itemView.findViewById(R.id.iv_recommend);
        }

    }
}
