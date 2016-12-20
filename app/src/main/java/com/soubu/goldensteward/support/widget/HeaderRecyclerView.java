package com.soubu.goldensteward.support.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.soubu.goldensteward.support.adapter.DataHolder;
import com.soubu.goldensteward.support.adapter.LayoutWrapper;
import com.soubu.goldensteward.support.adapter.SuperAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 带有Header和Footer的RecyclerView
 * <p>
 * 作者：余天然 on 2016/12/20 下午3:35
 */
public class HeaderRecyclerView<T, H, F> extends RecyclerView {

    private SuperAdapter adapter;
    private List<Integer> layoutIds = new ArrayList<>();

    private List<LayoutWrapper> data = new ArrayList<>();
    private DataHolder<T> itemHolder;

    private H headerData;
    private DataHolder<H> headerHolder;

    private F footerData;
    private DataHolder<F> footerHolder;

    private int itemId = -1;
    private int headerId = -1;
    private int footerId = -1;

    public HeaderRecyclerView(Context context) {
        super(context);
        init();
    }

    public HeaderRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeaderRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setLayoutManager(new LinearLayoutManager(getContext()));
    }

    /**
     * 更新布局类型
     */
    public void updateViewType() {
        layoutIds = new ArrayList<>();
        if (headerId != -1) {
            layoutIds.add(itemId);
        }
        if (headerId != -1) {
            layoutIds.add(headerId);
        }
        if (footerId != -1) {
            layoutIds.add(footerId);
        }
        adapter = new SuperAdapter(getContext(), layoutIds);
        setAdapter(adapter);
    }

    /**
     * 设置监听器
     */
    public void setItemHolder(DataHolder<T> holder) {
        this.itemHolder = holder;
    }

    public void setFooterHolder(DataHolder<F> holder) {
        this.footerHolder = holder;

    }

    public void setHeaderHolder(DataHolder<H> holder) {
        this.headerHolder = holder;
    }

    /**
     * 设置数据源
     */
    public void setData(List<T> list) {
        checkHeader();
        for (int i = 0; i < list.size(); i++) {
            T t = list.get(i);
            data.add(new LayoutWrapper(itemId, t, itemHolder));
        }
        checkFooter();
        adapter.setData(data);
    }

    public void setHeaderData(H headerData) {
        this.headerData = headerData;
    }

    public void setFooterData(F footerData) {
        this.footerData = footerData;
    }

    /**
     * 设置布局Id
     */
    public void setItemView(int itemId) {
        this.itemId = itemId;
    }

    public void setHeaderView(int headerId) {
        this.headerId = headerId;
    }

    public void setFooterView(int footerId) {
        this.footerId = footerId;
    }

    private void checkHeader() {
        if (headerId != -1) {
            data.add(new LayoutWrapper(headerId, headerData, headerHolder));
        }
    }

    private void checkFooter() {
        if (footerId != -1) {
            data.add(new LayoutWrapper(footerId, footerData, footerHolder));
        }
    }


}
