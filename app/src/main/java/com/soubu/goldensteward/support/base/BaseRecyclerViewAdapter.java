package com.soubu.goldensteward.support.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-10-20.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter {
    public static final int TYPE_TOP = 0x00;
    public static final int TYPE_MID = 0x01;
    public static final int TYPE_BOT = 0x02;
    public static final int TYPE_ONLY = 0x03;

    public static final int TYPE_FOOTER = 0x10;

    public OnRvItemClickListener mListener;

    public List<T> mList;

    //默认一页显式10项数据
    final int PAGE_SIZE = 20;

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

    public void setData(List<T> list, boolean isRefresh) {
        if (isRefresh) {
            if (!mList.isEmpty()) {
                mList.clear();
            }
        }
        if (list.size() < PAGE_SIZE) {
            setShowFooter(false);
        } else {
            setShowFooter(true);
        }
        mList.addAll(list);
    }

    /**
     * 是否显示加载更多视图
     */
    boolean mShowFooter = false;

    public void setShowFooter(boolean showFooter) {
        this.mShowFooter = showFooter;
    }

    public boolean isShowFooter() {
        return this.mShowFooter;
    }


    @Override
    public int getItemCount() {
        return isShowFooter() ? mList.size() + 1 : mList.size();
    }


    public interface OnRvItemClickListener {
        void onClick(int position);
    }

    public void setOnRvItemClickListener(OnRvItemClickListener listener) {
        mListener = listener;
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View view) {
            super(view);
        }
    }

}
