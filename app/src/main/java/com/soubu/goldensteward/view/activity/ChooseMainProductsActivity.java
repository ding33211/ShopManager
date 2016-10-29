package com.soubu.goldensteward.view.activity;

import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.adapter.BaseRecyclerViewAdapter;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.ChooseMainProductsActivityDelegate;
import com.soubu.goldensteward.module.TagInFlowLayout;
import com.soubu.goldensteward.utils.ShowWidgetUtil;
import com.soubu.goldensteward.widget.flowlayout.FlowLayoutController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lakers on 16/10/29.
 */

public class ChooseMainProductsActivity extends ActivityPresenter<ChooseMainProductsActivityDelegate> {
    List<List<TagInFlowLayout>> mTags = new ArrayList<>();

    @Override
    protected Class<ChooseMainProductsActivityDelegate> getDelegateClass() {
        return ChooseMainProductsActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.choose_main_products);
    }

    @Override
    protected void initData() {
        super.initData();
        initCategory();
    }


    private void initCategory(){
        String[] category = getResources().getStringArray(R.array.main_products_category);
        List<String> list = Arrays.asList(category);
        viewDelegate.setCategory(list);

        List<TagInFlowLayout> list1 = new ArrayList<>();
        TagInFlowLayout tag = new TagInFlowLayout();
        tag.setContent("毛衫");
        tag.setId(1);
        tag.setParentId(0);
        tag.setSelected(false);
        list1.add(tag);
        tag = new TagInFlowLayout();
        tag.setContent("针织牛仔");
        tag.setId(1);
        tag.setParentId(0);
        tag.setSelected(false);
        list1.add(tag);
        viewDelegate.refreshTags(list1);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnCategoryItemSelectListener(new BaseRecyclerViewAdapter.OnRvItemClickListener() {
            @Override
            public void onClick(int position) {
                switch (position){
                    case 0:

                }
            }
        });
        viewDelegate.setOnAddItemClickListener(new FlowLayoutController.OnClickAddItemListener() {
            @Override
            public void onClickAdd() {
                ShowWidgetUtil.showCustomInputDialog(ChooseMainProductsActivity.this, R.string.custom_product_name, R.string.custom_product_rule, new ShowWidgetUtil.OnClickCustomInputConfirm() {
                    @Override
                    public void onConfirm(String content) {
                        TagInFlowLayout tag = new TagInFlowLayout();
                        tag.setContent(content);
                        tag.setCanDel(true);
                        viewDelegate.addTag(tag);
                    }
                });
            }
        });
    }
}
