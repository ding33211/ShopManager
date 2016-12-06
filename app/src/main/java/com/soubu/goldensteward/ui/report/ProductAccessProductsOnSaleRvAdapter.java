package com.soubu.goldensteward.ui.report;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.base.BaseRecyclerViewAdapter;
import com.soubu.goldensteward.support.bean.server.ProductInOrderListServerParams;
import com.soubu.goldensteward.support.utils.GlideUtils;

/**
 * Created by lakers on 16/10/25.
 */

public class ProductAccessProductsOnSaleRvAdapter extends BaseRecyclerViewAdapter<ProductInOrderListServerParams> {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType != TYPE_FOOTER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_access_product_on_sale_recyclerview, parent, false);
            return new ItemViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_footer_in_report, parent, false);
            return new FooterViewHolder(v);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder holder1 = (ItemViewHolder) holder;
            ProductInOrderListServerParams params = mList.get(position);
            GlideUtils.loadRoundedImage(holder1.ivProductImg.getContext(), holder1.ivProductImg, params.getPic(), R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            holder1.tvProductName.setText(params.getTitle());
            holder1.tvSamplePrice.setText(params.getPrice());
//            holder1.tvUnit.setText(params.getUnit());
            holder1.tvBrowse.setText(params.getVisit());
            holder1.tvCollection.setText(params.getCollection());
//            holder1.tvTime.setText(ConvertUtil.dateToYYYY_MM_DD(new Date(Long.valueOf(params.getTime()) * 1000)));
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

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProductImg;
        TextView tvProductName;
        TextView tvSamplePrice;
        TextView tvBigGoodsPrice;
        TextView tvBrowse;
        TextView tvCollection;
//        TextView tvTime;
        TextView tvCustomerService;
//        TextView tvUnit;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ivProductImg = (ImageView) itemView.findViewById(R.id.iv_product);
            tvProductName = (TextView) itemView.findViewById(R.id.tv_name);
            tvSamplePrice = (TextView) itemView.findViewById(R.id.tv_sample_card_price);
            tvBigGoodsPrice = (TextView) itemView.findViewById(R.id.tv_big_goods_price);
            tvBrowse = (TextView) itemView.findViewById(R.id.tv_browser_volume);
            tvCollection = (TextView) itemView.findViewById(R.id.tv_collection_volume);
//            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvCustomerService = (TextView) itemView.findViewById(R.id.tv_customer_service);
//            tvUnit = (TextView) itemView.findViewById(R.id.tv_unit);
        }
    }

    @Override
    public boolean isShowFooter() {
        return true;
    }
}
