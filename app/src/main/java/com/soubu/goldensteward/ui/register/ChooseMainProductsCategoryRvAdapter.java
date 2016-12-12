package com.soubu.goldensteward.ui.register;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.adapter.SingleAdapter;

import java.util.List;

/**
 * Created by lakers on 16/10/29.
 */

public class ChooseMainProductsCategoryRvAdapter extends SingleAdapter<String> {

    private int mSelectPosition = 0;

    private List<Integer> mSelectedList;

    public ChooseMainProductsCategoryRvAdapter(Context context) {
        super(context, R.layout.item_choose_main_products_category_recyclerview);
    }

    @Override
    protected void bindData(BaseViewHolder holder, String item, int position) {
        TextView tvContent = holder.getView(R.id.tv_content);
        ImageView ivSelected = holder.getView(R.id.iv_select);
        View vItem = holder.getRootView();
        tvContent.setText(item);
        ivSelected.setVisibility(View.GONE);
        if (mSelectedList.contains(position)) {
            ivSelected.setImageResource(R.drawable.login_product_checked);
            ivSelected.setVisibility(View.VISIBLE);
        }
        if (mSelectPosition == position) {
            vItem.setBackgroundResource(R.color.colorPrimary);
            tvContent.setTextColor(tvContent.getResources().getColor(android.R.color.white));
            ivSelected.setImageResource(R.drawable.login_product_checking);
            ivSelected.setVisibility(View.VISIBLE);
        } else {
            vItem.setBackgroundResource(android.R.color.transparent);
            tvContent.setTextColor(tvContent.getResources().getColor(R.color.subtitle_grey));
        }
    }

    @Override
    public void onItemClick(BaseViewHolder holder, String item, int position) {
        if (mListener != null) {
            if(!mListener.onClick(position)){
                return;
            }
        }
        mSelectPosition = position;
        notifyDataSetChanged();
    }

//    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        TextView tvContent;
//        ImageView ivSelected;
//        View vItem;
//
//        public ItemViewHolder(View itemView) {
//            super(itemView);
//            vItem = itemView;
//            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
//            ivSelected = (ImageView) itemView.findViewById(R.id.iv_select);
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View v) {
//            if (mListener != null) {
//                if(!mListener.onClick(getLayoutPosition())){
//                    return;
//                }
//            }
//            mSelectPosition = getLayoutPosition();
//            notifyDataSetChanged();
//        }
//    }


    public void setSelectedList(List<Integer> list) {
        mSelectedList = list;
    }

    OnCanGoOnClickListener mListener;

    public interface OnCanGoOnClickListener {
        //由于涉及到还有不能点击的情况，所以讲返回类型设置为布尔
        boolean onClick(int position);
    }

    public void setOnRvItemClickListener(OnCanGoOnClickListener listener) {
        mListener = listener;
    }

    public void setSelectedPosition(int position){
        mSelectPosition = position;
    }


}
