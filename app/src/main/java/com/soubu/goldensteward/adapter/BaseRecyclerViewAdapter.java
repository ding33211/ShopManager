package com.soubu.goldensteward.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-10-20.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter {
    OnRvItemClickListener mListener;

    List<T> mList;

    public BaseRecyclerViewAdapter() {
        mList = new ArrayList<>();
    }

    //公共方法
    public void setData(List<T> list) {
        if (!mList.isEmpty()) {
            mList.clear();
        }
        mList.addAll(list);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public interface OnRvItemClickListener {
        void onClick(int position);
    }

    public void setOnRvItemClickListener(OnRvItemClickListener listener){
        mListener = listener;
    }

}
