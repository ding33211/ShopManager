package com.soubu.goldensteward.ui.register;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.support.bean.BaseEventBusResp;
import com.soubu.goldensteward.support.bean.Constant;
import com.soubu.goldensteward.support.bean.EventBusConfig;
import com.soubu.goldensteward.support.bean.TagInFlowLayoutModule;
import com.soubu.goldensteward.support.bean.server.BaseDataArray;
import com.soubu.goldensteward.support.bean.server.BaseResp;
import com.soubu.goldensteward.support.bean.MainProductParams;
import com.soubu.goldensteward.support.bean.server.MainProductTagServerParams;
import com.soubu.goldensteward.support.server.RetrofitRequest;
import com.soubu.goldensteward.support.utils.ShowWidgetUtil;
import com.soubu.goldensteward.support.widget.flowlayout.FlowLayoutController;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lakers on 16/10/29.
 */

public class ChooseMainProductsActivity extends ActivityPresenter<ChooseMainProductsActivityDelegate> implements View.OnClickListener {
    List<List<TagInFlowLayoutModule>> mTags = new ArrayList<>();
    MainProductParams[] mChooseParams;
    private Integer mCurrentPosition = 0;
    //存放选中的大类的index
    List<Integer> mSelectedBigTagList = new ArrayList<>();

    //每个大类中增加的标签数
    List<Integer> mAddList;

    //每个大类中选中的标签数目
    List<Integer> mSelectedList;

    @Override
    protected Class<ChooseMainProductsActivityDelegate> getDelegateClass() {
        return ChooseMainProductsActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.choose_main_products);
        Parcelable[] parcelables = getIntent().getParcelableArrayExtra(Constant.EXTRA_PARAMS);
        if (parcelables != null) {
            mChooseParams = new MainProductParams[parcelables.length];
            for (int i = 0; i < parcelables.length; i++) {
                mChooseParams[i] = (MainProductParams) parcelables[i];
            }
        }

    }

    @Override
    protected void initData() {
        super.initData();
        requestMainProductTag();
        mTags = new ArrayList<>();
        mAddList = new ArrayList<>();
        mSelectedList = new ArrayList<>();
    }

    private void requestMainProductTag() {
        RetrofitRequest.getInstance().getMainTag();
    }


    private void initTagView(MainProductTagServerParams[] params) {
//        String[] category = getResources().getStringArray(R.array.main_products_category);
        List<String> bigTags = new ArrayList<>();
        List<TagInFlowLayoutModule> smallTags;
        List<Integer> selectedSmallTagList = new ArrayList<>();
        List<String> selectedSmallTagStringLis = new ArrayList<>();
        for (MainProductTagServerParams param : params) {
            int customCount = 0;
            int selectCount = 0;
            smallTags = new ArrayList<>();
            int bigTagId = param.getId();
            bigTags.add(param.getName());
            if (mChooseParams != null) {
                for (MainProductParams choose : mChooseParams) {
                    int chooseId = choose.getId();
                    if (!mSelectedBigTagList.contains(chooseId - 1)) {
                        mSelectedBigTagList.add(chooseId - 1);
                    }
                    if (chooseId == bigTagId) {
                        selectedSmallTagList.clear();
                        selectedSmallTagStringLis.clear();
                        for (int a : choose.getSys_tags()) {
                            selectedSmallTagList.add(a);
                            selectCount++;
                        }
                        for (String b : choose.getCustom_tags()) {
                            customCount++;
                            selectCount++;
                            selectedSmallTagStringLis.add(b);
                        }
                        break;
                    }
                }
            }
            for (MainProductTagServerParams.Level level : param.getNext_level()) {
                TagInFlowLayoutModule tag = new TagInFlowLayoutModule();
                tag.setContent(level.getName());
                tag.setId(level.getId());
                tag.setParentId(bigTagId);
                if (selectedSmallTagList.contains(level.getId())) {
                    tag.setSelected(true);
                } else {
                    tag.setSelected(false);
                }
                smallTags.add(tag);
            }
            for (String tag : selectedSmallTagStringLis) {
                TagInFlowLayoutModule customTag = new TagInFlowLayoutModule();
                customTag.setContent(tag);
                customTag.setParentId(bigTagId);
                customTag.setSelected(true);
                smallTags.add(customTag);
            }
            mTags.add(smallTags);
            mAddList.add(customCount);
            mSelectedList.add(selectCount);
        }
        viewDelegate.setCategorySelected(mSelectedBigTagList);
        viewDelegate.setCategory(bigTags);
        int count = mAddList.get(mCurrentPosition);
        if (mSelectedBigTagList.size() != 0) {
            viewDelegate.setSelectedPosition(mSelectedBigTagList.get(0));
            viewDelegate.refreshTags(mTags.get(mSelectedBigTagList.get(0)), count != 3);
        } else {
            viewDelegate.refreshTags(mTags.get(0), count != 3);
        }

    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnCategoryItemSelectListener(new ChooseMainProductsCategoryRvAdapter.OnCanGoOnClickListener() {
            @Override
            public boolean onClick(int position) {
                viewDelegate.setCategorySelected(mSelectedBigTagList);
                if (mSelectedBigTagList.size() < 2 || mSelectedBigTagList.contains(position)) {
                    mCurrentPosition = position;
                    int count = mAddList.get(mCurrentPosition);
                    viewDelegate.refreshTags(mTags.get(position), count != 3);
                    return true;
                } else {
                    ShowWidgetUtil.showShort(R.string.much_more_big_tag);
                    return false;
                }

            }
        });
        viewDelegate.setOnAddItemClickListener(new FlowLayoutController.OnClickAddItemListener() {
            @Override
            public void onClickAdd() {
                ShowWidgetUtil.showCustomInputDialog(ChooseMainProductsActivity.this, R.string.custom_product_name, R.string.custom_product_rule, 7, new ShowWidgetUtil.OnClickCustomInputConfirm() {
                    @Override
                    public void onConfirm(String content) {
                        TagInFlowLayoutModule tag = new TagInFlowLayoutModule();
                        tag.setContent(content);
                        tag.setCanDel(true);
                        tag.setParentId(mTags.get(mCurrentPosition).get(0).getParentId());
                        viewDelegate.addTag(tag);
                        mTags.get(mCurrentPosition).add(tag);
                        int count = mAddList.get(mCurrentPosition);
                        if (count == 2) {
                            viewDelegate.removeAddItem();
                        }
                        count++;
                        mAddList.set(mCurrentPosition, count);
                    }
                });
            }
        });

        viewDelegate.setOnEventCallBackListener(new FlowLayoutController.OnEventCallBackListener<TagInFlowLayoutModule>() {
            @Override
            public void onAdd(TagInFlowLayoutModule tag) {

            }

            @Override
            public void onDelete(TagInFlowLayoutModule tag) {
                mTags.get(mCurrentPosition).remove(tag);
                int count = mAddList.get(mCurrentPosition);
                if (count == 3) {
                    viewDelegate.addAddItem();
                }
                count--;
                mAddList.set(mCurrentPosition, count);
            }

            @Override
            public boolean onSelected(TagInFlowLayoutModule tag) {
                //每个大类最多只能选择8个小标签
                int count = mSelectedList.get(mCurrentPosition);
                if (count == 8) {
                    ShowWidgetUtil.showShort(R.string.much_more_small_tag);
                    return false;
                }
                if (count++ == 0) {
                    if (!mSelectedBigTagList.contains(mCurrentPosition)) {
                        mSelectedBigTagList.add(mCurrentPosition);
                    }
                }
                mSelectedList.set(mCurrentPosition, count);
                return true;
            }

            @Override
            public void onUnSelected(TagInFlowLayoutModule tag) {
                int count = mSelectedList.get(mCurrentPosition);
                if (--count == 0) {
                    if (mSelectedBigTagList.contains(mCurrentPosition)) {
                        mSelectedBigTagList.remove(mCurrentPosition);
                    }
                }
                mSelectedList.set(mCurrentPosition, count);
            }
        });

        viewDelegate.setOnClickListener(this, R.id.btn_confirm);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMainTag(BaseEventBusResp resp) {
        BaseResp resp1 = (BaseResp) resp.getObject();
        int code = resp.getCode();
        if (code == EventBusConfig.GET_MAIN_TAG) {
            BaseDataArray data = (BaseDataArray) resp1.getResult();
            Object o = data.getData();
            if (o instanceof MainProductTagServerParams[]) {
                initTagView((MainProductTagServerParams[]) o);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                mChooseParams = new MainProductParams[mSelectedBigTagList.size()];
                StringBuilder builder = new StringBuilder();
                //需要对此排序
                Collections.sort(mSelectedBigTagList);
                for (int i = 0; i < mSelectedBigTagList.size(); i++) {
                    MainProductParams param = new MainProductParams();
                    int id = mSelectedBigTagList.get(i) + 1;
                    param.setId(id);
                    List<Integer> systemList = new ArrayList<>();
                    List<String> customList = new ArrayList<>();
                    for (TagInFlowLayoutModule tag : mTags.get(id - 1)) {
                        if (tag.isSelected()) {
                            if (tag.getId() == 0) {
                                customList.add(tag.getContent());
                            } else {
                                systemList.add(tag.getId());
                            }
                            builder.append(tag.getContent() + ",");
                        }
                    }

                    int[] systemIds = new int[systemList.size()];
                    for (int a = 0; a < systemList.size(); a++) {
                        systemIds[a] = systemList.get(a);
                    }
                    param.setSys_tags(systemIds);
//                    String[] customTags = new String[customList.size()];
                    String[] customTags = customList.toArray(new String[customList.size()]);
//                    for(int b = 0; b < customList.size(); b++){
//                        customTags[b] = customList.get(b);
//                    }
                    param.setCustom_tags(customTags);
                    mChooseParams[i] = param;
                }
                Intent data = new Intent();
                data.putExtra(Constant.EXTRA_PARAMS, mChooseParams);
                if (builder.length() > 2) {
                    String result = builder.substring(0, builder.length() - 1);
                    data.putExtra(Constant.EXTRA_RESULT, result);
                }
                setResult(RESULT_OK, data);
                finish();
                break;
        }
    }
}
