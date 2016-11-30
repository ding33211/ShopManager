package com.soubu.goldensteward.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.soubu.goldensteward.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-11-28.
 */

public class ProductClassificationGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mList = new ArrayList<>();

    public ProductClassificationGridViewAdapter(Context context, List<String> list) {
        mContext = context;
        mList.addAll(list);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_classification_gridview, null);
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText(mList.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView tvTitle;
    }
}
