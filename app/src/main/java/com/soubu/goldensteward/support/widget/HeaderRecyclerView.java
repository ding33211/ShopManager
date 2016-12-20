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
    private List<Integer> layoutIds = new ArrayList<>();//布局id列表
    private List<LayoutWrapper> data = new ArrayList<>();//多类型的数据源

    private ViewBuilder viewBuilder;//视图建造者
    private DataBuilder dataBuilder;//数据建造者

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
        viewBuilder = new ViewBuilder();
        dataBuilder = new DataBuilder();
    }

    public DataBuilder getDataBuilder() {
        return dataBuilder;
    }

    public ViewBuilder getViewBuilder() {
        return viewBuilder;
    }

    /**
     * 更新布局类型
     */
    public void updateViewType() {
        layoutIds = new ArrayList<>();
        if (viewBuilder.headerId != -1) {
            layoutIds.add(viewBuilder.itemId);
        }
        if (viewBuilder.headerId != -1) {
            layoutIds.add(viewBuilder.headerId);
        }
        if (viewBuilder.footerId != -1) {
            layoutIds.add(viewBuilder.footerId);
        }
        adapter = new SuperAdapter(getContext(), layoutIds);
        setAdapter(adapter);
    }

    /**
     * 更新数据
     */
    public void updateData() {
        if (viewBuilder.headerId != -1) {
            data.add(new LayoutWrapper(viewBuilder.headerId, dataBuilder.headerData, viewBuilder.headerHolder));
        }
        for (int i = 0; i < dataBuilder.itemData.size(); i++) {
            T t = dataBuilder.itemData.get(i);
            data.add(new LayoutWrapper(viewBuilder.itemId, t, viewBuilder.itemHolder));
        }
        if (viewBuilder.footerId != -1) {
            data.add(new LayoutWrapper(viewBuilder.footerId, dataBuilder.footerData, viewBuilder.footerHolder));
        }
        adapter.setData(data);
    }

    /**
     * 视图建造者
     */
    public class ViewBuilder {

        public int itemId = -1;
        public int headerId = -1;
        public int footerId = -1;

        public DataHolder<T> itemHolder;
        public DataHolder<H> headerHolder;
        public DataHolder<F> footerHolder;

        public ViewBuilder itemId(int itemId) {
            this.itemId = itemId;
            return this;
        }

        public ViewBuilder headerId(int headerId) {
            this.headerId = headerId;
            return this;
        }

        public ViewBuilder footerId(int footerId) {
            this.footerId = footerId;
            return this;
        }

        public ViewBuilder itemHolder(DataHolder<T> itemHolder) {
            this.itemHolder = itemHolder;
            return this;
        }

        public ViewBuilder headerHolder(DataHolder<H> headerHolder) {
            this.headerHolder = headerHolder;
            return this;
        }

        public ViewBuilder footerHolder(DataHolder<F> footerHolder) {
            this.footerHolder = footerHolder;
            return this;
        }

        public void build() {
            updateViewType();
        }
    }

    /**
     * 数据建造者
     */
    public class DataBuilder {

        public List<T> itemData = new ArrayList<T>();
        public H headerData;
        public F footerData;

        public DataBuilder() {
        }

        public DataBuilder item(List<T> itemData) {
            this.itemData = itemData;
            return this;
        }

        public DataBuilder header(H headerData) {
            this.headerData = headerData;
            return this;
        }

        public DataBuilder footerData(F footerData) {
            this.footerData = footerData;
            return this;
        }

        public void update() {
            updateData();
        }
    }

}
