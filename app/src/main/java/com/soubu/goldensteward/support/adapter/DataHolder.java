package com.soubu.goldensteward.support.adapter;

/**
 * 数据控制器
 * ----------
 * 将ItemData和ViewHolder进行绑定，并设置监听
 */
public abstract class DataHolder<T> {

    public abstract void bindData(BaseViewHolder holder, T item, int position);

    public void bindListener(BaseViewHolder holder, T item, int position) {
    }

    public void onItemClick(BaseViewHolder holder, T item, int position) {

    }
}
