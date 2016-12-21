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

    private ViewBuilder<T, H, F> viewBuilder;//视图建造者
    private DataBuilder<T, H, F> dataBuilder;//数据建造者

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
        viewBuilder = new ViewBuilder<T, H, F>();
        dataBuilder = new DataBuilder<T, H, F>();
    }

    public DataBuilder<T, H, F> getDataBuilder() {
        return dataBuilder;
    }

    public ViewBuilder<T, H, F> getViewBuilder() {
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
            data.add(new LayoutWrapper(viewBuilder.itemId, dataBuilder.itemData.get(i), viewBuilder.itemHolder));
        }
        if (viewBuilder.footerId != -1) {
            data.add(new LayoutWrapper(viewBuilder.footerId, dataBuilder.footerData, viewBuilder.footerHolder));
        }
        adapter.setData(data);
    }

    /**
     * 视图建造者
     */
    public class ViewBuilder<VT, VH, VF> {

        public int itemId = -1;
        public int headerId = -1;
        public int footerId = -1;

        public DataHolder<VT> itemHolder;
        public DataHolder<VH> headerHolder;
        public DataHolder<VF> footerHolder;

        public ViewBuilder<VT, VH, VF> itemId(int itemId) {
            this.itemId = itemId;
            return this;
        }

        public ViewBuilder<VT, VH, VF> headerId(int headerId) {
            this.headerId = headerId;
            return this;
        }

        public ViewBuilder<VT, VH, VF> footerId(int footerId) {
            this.footerId = footerId;
            return this;
        }

        public ViewBuilder<VT, VH, VF> itemHolder(DataHolder<VT> itemHolder) {
            this.itemHolder = itemHolder;
            return this;
        }

        public ViewBuilder<VT, VH, VF> headerHolder(DataHolder<VH> headerHolder) {
            this.headerHolder = headerHolder;
            return this;
        }

        public ViewBuilder<VT, VH, VF> footerHolder(DataHolder<VF> footerHolder) {
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
    public class DataBuilder<DT, DH, DF> {

        public List<DT> itemData = new ArrayList<>();
        public DH headerData;
        public DF footerData;

        public DataBuilder<DT, DH, DF> item(List<DT> itemData) {
            this.itemData = itemData;
            return this;
        }

        public DataBuilder<DT, DH, DF> header(DH headerData) {
            this.headerData = headerData;
            return this;
        }

        public DataBuilder<DT, DH, DF> footerData(DF footerData) {
            this.footerData = footerData;
            return this;
        }

        public void update() {
            updateData();
        }
    }

}
