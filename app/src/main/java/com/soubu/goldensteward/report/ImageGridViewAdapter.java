package com.soubu.goldensteward.report;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-11-17.
 */

public class ImageGridViewAdapter extends BaseAdapter {

    private List<String> mUrlList = new ArrayList<>();
    private Context mContext;

    public ImageGridViewAdapter(Context context, List<String> list) {
        mContext = context;
        mUrlList = list;
    }

    @Override
    public int getCount() {
        return mUrlList.size();
    }

    public void setData(List<String> urlList) {
        if (urlList != null) {
            mUrlList.clear();
            mUrlList.addAll(urlList);
        }
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            convertView = mInflater.inflate(R.layout.grid_item_image, parent, false);
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GlideUtils.loadRoundedImage(mContext, viewHolder.ivIcon, mUrlList.get(position), R.drawable.common_product_placeholder, R.drawable.common_product_placeholder);
        return convertView;
    }

    public class ViewHolder {
        ImageView ivIcon;
    }
}
