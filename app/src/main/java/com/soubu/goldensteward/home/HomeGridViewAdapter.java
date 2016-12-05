package com.soubu.goldensteward.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-10-18.
 */
public class HomeGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mTitleList = new ArrayList<>();
    private List<String> mSubTitleList = new ArrayList<>();
    private List<Integer> mIconList = new ArrayList<>();

    public HomeGridViewAdapter(Context context) {
        mContext = context;
    }

    public HomeGridViewAdapter(Context context, List<Integer> iconList, List<String> titleList, List<String> subTitleList) {
        mTitleList.addAll(titleList);
        mSubTitleList.addAll(subTitleList);
        mContext = context;
        if (iconList != null) {
            mIconList.addAll(iconList);
        }
    }

    public void setData(List<Integer> iconList, List<String> titleList, List<String> subTitleList) {
        if (iconList != null) {
            mIconList.clear();
            mIconList.addAll(iconList);
        }
        mTitleList.clear();
        mTitleList.addAll(titleList);
        mSubTitleList.clear();
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
        if (mIconList.size() == 0 || mIconList.get(i) == 0) {
            viewHolder.ivIcon.setVisibility(View.GONE);
        } else {
            viewHolder.ivIcon.setImageResource(mIconList.get(i));
        }
        viewHolder.tvLabel.setText(mTitleList.get(i));
        viewHolder.tvContent.setText(mSubTitleList.get(i));
        return convertView;
    }

    public class ViewHolder {
        ImageView ivIcon;
        TextView tvLabel;
        TextView tvContent;
    }

}
