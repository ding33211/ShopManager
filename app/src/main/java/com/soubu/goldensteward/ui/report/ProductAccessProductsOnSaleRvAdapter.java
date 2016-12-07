package com.soubu.goldensteward.ui.report;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.base.BaseRecyclerViewAdapter;
import com.soubu.goldensteward.support.bean.server.ProductInOrderListServerParams;
import com.soubu.goldensteward.support.utils.ConvertUtil;
import com.soubu.goldensteward.support.utils.GlideUtils;

import java.util.Date;

/**
 * Created by lakers on 16/10/25.
 */

public class ProductAccessProductsOnSaleRvAdapter extends BaseRecyclerViewAdapter<ProductInOrderListServerParams> {
    public int mWhere;
    public static final int PRODUCT_ON_SALE = 0x00;
    public static final int PRODUCT_CHOOSE_SIGN_UP = 0x01;
    public static final int PRODUCT_HAVE_SIGNED_UP = 0x02;


    public ProductAccessProductsOnSaleRvAdapter(int where) {
        mWhere = where;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType != TYPE_FOOTER) {
            View v;
            if (mWhere == PRODUCT_ON_SALE) {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_old_product_maybe_delete_soon, parent, false);
            } else {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_access_product_on_sale_recyclerview, parent, false);
            }
            switch (mWhere) {
                case PRODUCT_HAVE_SIGNED_UP:
                    View vBottom = v.findViewById(R.id.ll_bottom);
                    vBottom.setVisibility(View.GONE);
                    View vCbChoose = v.findViewById(R.id.cb_choose);
                    vCbChoose.setVisibility(View.GONE);
                case PRODUCT_ON_SALE:
//                    View vCbChoose = v.findViewById(R.id.cb_choose);
//                    vCbChoose.setVisibility(View.GONE);
                    break;
                case PRODUCT_CHOOSE_SIGN_UP:
                    View vBottom1 = v.findViewById(R.id.ll_bottom);
                    vBottom1.setVisibility(View.GONE);
                    break;
            }
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
            if (mWhere == PRODUCT_ON_SALE) {
                holder1.tvUnitPrice.setText(params.getPrice());
                holder1.tvUnit.setText(params.getUnit());
                holder1.tvTime.setText(ConvertUtil.dateToYYYY_MM_DD(new Date(Long.valueOf(params.getTime()) * 1000)));
            } else {
                holder1.tvSamplePrice.setText(params.getPrice());
            }
            holder1.tvBrowse.setText(params.getVisit());
            holder1.tvCollection.setText(params.getCollection());
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

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivProductImg;
        TextView tvProductName;
        TextView tvSamplePrice;
        TextView tvBigGoodsPrice;
        TextView tvBrowse;
        TextView tvCollection;
        TextView tvTime;
        TextView tvUnitPrice;
        TextView tvCustomerService;
        CheckBox cbChoose;

        TextView tvUnit;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ivProductImg = (ImageView) itemView.findViewById(R.id.iv_product);
            tvProductName = (TextView) itemView.findViewById(R.id.tv_name);
            if (mWhere == PRODUCT_ON_SALE) {
                tvUnit = (TextView) itemView.findViewById(R.id.tv_unit);
                tvUnitPrice = (TextView) itemView.findViewById(R.id.tv_unit_price);
                tvTime = (TextView) itemView.findViewById(R.id.tv_time);
                tvCustomerService = (TextView) itemView.findViewById(R.id.tv_customer_service);
            } else {
                tvSamplePrice = (TextView) itemView.findViewById(R.id.tv_sample_card_price);
                tvBigGoodsPrice = (TextView) itemView.findViewById(R.id.tv_big_goods_price);
                cbChoose = (CheckBox) itemView.findViewById(R.id.cb_choose);
            }
            tvBrowse = (TextView) itemView.findViewById(R.id.tv_browser_volume);
            tvCollection = (TextView) itemView.findViewById(R.id.tv_collection_volume);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (mWhere != PRODUCT_ON_SALE) {
                if (cbChoose.isChecked()) {
                    cbChoose.setChecked(false);
                } else {
                    cbChoose.setChecked(true);
                }
            }
            if (mListener != null) {
                mListener.onClick(getLayoutPosition());
            }
        }
    }

    @Override
    public boolean isShowFooter() {
        if (mWhere == PRODUCT_ON_SALE) {
            return true;
        } else {
            return false;
        }
    }

}
