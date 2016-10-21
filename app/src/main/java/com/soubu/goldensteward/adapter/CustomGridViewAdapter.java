package com.soubu.goldensteward.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.module.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-10-18.
 */
public class CustomGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mTitleList = new ArrayList<>();
    private List<String> mSubTitleList = new ArrayList<>();
    private List<Integer> mIconList = new ArrayList<>();

    public CustomGridViewAdapter(Context context) {
        mContext = context;
    }

    public CustomGridViewAdapter(Context context, List<Integer> iconList, List<String> titleList, List<String> subTitleList) {
        mTitleList.addAll(titleList);
        mSubTitleList.addAll(subTitleList);
        mContext = context;
        if(iconList != null){
            mIconList.addAll(iconList);
        }
    }

    public void setData(List<Integer> iconList, List<String> titleList, List<String> subTitleList){
        if(iconList != null){
            mIconList.addAll(iconList);
        }
        mTitleList.addAll(titleList);
        mSubTitleList.addAll(subTitleList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mTitleList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            convertView = mInflater.inflate(R.layout.grid_item_today_turnover, null);

            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tvLabel = (TextView) convertView
                    .findViewById(R.id.tv_label);
            viewHolder.tvContent = (TextView) convertView
                    .findViewById(R.id.tv_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (mIconList.size() == 0 || mIconList.get(i) == 0 ) {
            viewHolder.ivIcon.setVisibility(View.GONE);
        } else {
            viewHolder.ivIcon.setImageResource(mIconList.get(i));
        }
        viewHolder.tvLabel.setText(mTitleList.get(i));
        viewHolder.tvContent.setText(mSubTitleList.get(i));
//        switch (mViewTypeList.get(i)) {
//            case Constant.GRID_TYPE_TODAY_VISITOR_NUM:
//                viewHolder.tvLabel.setText(R.string.today_visitor_num);
//                break;
//            case Constant.GRID_TYPE_TODAY_PRODUCT_VISIT:
//                viewHolder.tvLabel.setText(R.string.today_product_visit);
//                break;
//            case Constant.GRID_TYPE_TODAY_ORDER_NUM:
//                viewHolder.tvLabel.setText(R.string.today_product_visit);
//                break;
//            case Constant.GRID_TYPE_TODAY_RETURN_RATE:
//                viewHolder.tvLabel.setText(R.string.today_return_rate);
//                break;
//            case Constant.GRID_TYPE_MY_WALLET:
//                viewHolder.tvLabel.setText(R.string.my_wallet);
//                viewHolder.ivIcon.setImageResource(R.drawable.home_wallet);
//                break;
//            case Constant.GRID_TYPE_OPERATION_REPORT:
//                viewHolder.tvLabel.setText(R.string.operation_report);
//                viewHolder.tvContent.setText(R.string.product_access_etc);
//                viewHolder.ivIcon.setImageResource(R.drawable.home_chart);
//                break;
//            case Constant.GRID_TYPE_MY_CUSTOMER:
//                viewHolder.tvLabel.setText(R.string.my_customer);
//                viewHolder.tvContent.setText(R.string.transaction_record_display);
//                viewHolder.ivIcon.setImageResource(R.drawable.home_customer);
//                break;
//            case Constant.GRID_TYPE_SETTING:
//                viewHolder.tvLabel.setText(R.string.setting);
//                viewHolder.tvContent.setText(R.string.real_name_authentication_etc);
//                viewHolder.ivIcon.setImageResource(R.drawable.home_setting);
//                break;
//            case Constant.GRID_TYPE_ACCUMULATED_INCOME:
//                viewHolder.tvLabel.setText(R.string.accumulated_income_yuan);
//                break;
//            case Constant.GRID_TYPE_TODAY_INCOME:
//                viewHolder.tvLabel.setText(R.string.today_income_yuan);
//                break;
//            case Constant.GRID_TYPE_LAST_WEEK_INCOME:
//                viewHolder.tvLabel.setText(R.string.last_week_income_yuan);
//                break;
//            case Constant.GRID_TYPE_LAST_MONTH_INCOME:
//                viewHolder.tvLabel.setText(R.string.last_month_income_yuan);
//                break;
//        }

        return convertView;
    }

    public class ViewHolder {
        ImageView ivIcon;
        TextView tvLabel;
        TextView tvContent;
    }

}
