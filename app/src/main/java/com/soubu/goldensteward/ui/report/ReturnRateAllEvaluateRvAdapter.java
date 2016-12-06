package com.soubu.goldensteward.ui.report;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.base.BaseRecyclerViewAdapter;
import com.soubu.goldensteward.support.bean.server.EvaluateInReturnRateServerParams;
import com.soubu.goldensteward.support.bean.server.ImageServerParams;
import com.soubu.goldensteward.support.utils.ConvertUtil;
import com.soubu.goldensteward.support.utils.GlideUtils;
import com.soubu.goldensteward.support.utils.RegularUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lakers on 16/10/25.
 */

public class ReturnRateAllEvaluateRvAdapter extends BaseRecyclerViewAdapter<EvaluateInReturnRateServerParams> {

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
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            EvaluateInReturnRateServerParams item = mList.get(position);
            viewHolder.gvImage.setVisibility(View.GONE);
            if (item.getImgList() != null && item.getImgList().length > 0) {
                ImageServerParams[] images = item.getImgList();
                List<String> urls = new ArrayList<>();
                for (ImageServerParams params : images) {
                    urls.add(params.getThumb_img());
                }
                viewHolder.gvImage.setAdapter(new ImageGridViewAdapter(viewHolder.gvImage.getContext(), urls));
                viewHolder.gvImage.setVisibility(View.VISIBLE);
            }
            GlideUtils.loadRoundedImage(viewHolder.ivCompanyIcon.getContext(), viewHolder.ivCompanyIcon, item.getPortrait(), R.drawable.common_header, R.drawable.common_header);
            viewHolder.tvName.setText(item.getName());
            if (RegularUtil.isInteger(item.getLevel())) {
                switch (Integer.valueOf(item.getLevel())) {
                    case 0:
                        viewHolder.ivVip.setImageResource(R.drawable.role_0);
                        break;
                    case 1:
                        viewHolder.ivVip.setImageResource(R.drawable.role_1);
                        break;
                    case 2:
                        viewHolder.ivVip.setImageResource(R.drawable.role_2);
                        break;
                    case 3:
                        viewHolder.ivVip.setImageResource(R.drawable.role_3);
                        break;
                    case 4:
                        viewHolder.ivVip.setImageResource(R.drawable.role_4);
                        break;
                }
            }
            try {
                viewHolder.rbRating.setRating(Float.valueOf(item.getRating()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            viewHolder.tvContent.setText(item.getContent());
            viewHolder.tvTime.setText(TextUtils.isEmpty(item.getAdd_time()) ? "" : ConvertUtil.dateToYYYY_MM_DD_HH_mm(new Date(Long.valueOf(item.getAdd_time()) * 1000)));
            viewHolder.tvReply.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(item.getReply())) {
                viewHolder.tvReply.setText("商家回复：" + item.getReply());
                viewHolder.tvReply.setVisibility(View.VISIBLE);
            }
            if (RegularUtil.isInteger(item.getRole())) {
                int role = Integer.valueOf(item.getRole());
                if (role == 1) {
                    viewHolder.ivShopSpec.setImageResource(R.drawable.purchaser_type_purchaser);
                } else {
                    viewHolder.ivShopSpec.setImageResource(R.drawable.supplier_type_supplier);
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
        //        ImageView ivShopType;
        ImageView ivShopSpec;
        TextView tvContent;
        TextView tvTime;
        TextView tvCustomerService;
        GridView gvImage;
        RatingBar rbRating;
        TextView tvReply;
//        TextView tvType;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ivCompanyIcon = (ImageView) itemView.findViewById(R.id.iv_company_icon);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            ivVip = (ImageView) itemView.findViewById(R.id.iv_vip);
//            ivShopType = (ImageView) itemView.findViewById(R.id.iv_shop_type);
            ivShopSpec = (ImageView) itemView.findViewById(R.id.iv_shop_spec);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvCustomerService = (TextView) itemView.findViewById(R.id.tv_customer_service);
            gvImage = (GridView) itemView.findViewById(R.id.gv_image);
            rbRating = (RatingBar) itemView.findViewById(R.id.rb_rating);
            tvReply = (TextView) itemView.findViewById(R.id.tv_reply);
//            tvType =
        }
    }
}
