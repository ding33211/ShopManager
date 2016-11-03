package com.soubu.goldensteward.delegate;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.adapter.ChooseMainProductsCategoryRvAdapter;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;
import com.soubu.goldensteward.module.TagInFlowLayoutModule;
import com.soubu.goldensteward.widget.DividerItemDecoration;
import com.soubu.goldensteward.widget.flowlayout.FlowLayout;
import com.soubu.goldensteward.widget.flowlayout.FlowLayoutController;

import java.util.List;

/**
 * Created by lakers on 16/10/29.
 */

public class ChooseMainProductsActivityDelegate extends AppDelegate {
    RecyclerView mRvCategory;
    ChooseMainProductsCategoryRvAdapter mAdapter;
    FlowLayoutController mFlController;
    FlowLayout mFlTags;


    @Override
    public int getRootLayoutId() {
        return R.layout.activity_choose_main_products;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mRvCategory = get(R.id.rv_content);
        mAdapter = new ChooseMainProductsCategoryRvAdapter();
        mRvCategory.setAdapter(mAdapter);
        mRvCategory.addItemDecoration(new DividerItemDecoration(this.getActivity(), LinearLayoutManager.VERTICAL, 1, R.drawable.divider_grey_recyclerview));
        mRvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFlTags = get(R.id.fl_tags);
        mFlController = new FlowLayoutController(mFlTags);
    }


    public void setCategory(List<String> category) {
        mAdapter.setData(category);
        mAdapter.notifyDataSetChanged();
    }

    public void setCategorySelected(List<Integer> selectedList) {
        mAdapter.setSelectedList(selectedList);
    }

    public void refreshTags(List<TagInFlowLayoutModule> list) {
        mFlTags.removeAllViews();
        mFlController.addTags(list);
    }


    public void setOnCategoryItemSelectListener(ChooseMainProductsCategoryRvAdapter.OnCanGoOnClickListener listener) {
        mAdapter.setOnRvItemClickListener(listener);
    }

    public void setOnAddItemClickListener(FlowLayoutController.OnClickAddItemListener listener) {
        mFlController.setOnClickAddItemListener(listener);
    }

    public void setOnEventCallBackListener(FlowLayoutController.OnEventCallBackListener callBack) {
        mFlController.setOnEventCallBack(callBack);
    }

    public void addTag(TagInFlowLayoutModule tag) {
        mFlController.addTagItem(tag);
    }

    public void removeAddItem() {
        mFlController.removeAddItem();
    }

    public void addAddItem() {
        mFlController.addAddItem();
    }


    @Override
    public boolean ifNeedEventBus() {
        return true;
    }
}
