package com.soubu.goldensteward.view.fragment;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.adapter.InformationRecyclerViewAdapter;
import com.soubu.goldensteward.base.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.delegate.RecyclerViewFragmentDelegate;
import com.soubu.goldensteward.module.RecyclerViewItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-10-19.
 */
public class CompanyInformationFragment extends FragmentPresenter<RecyclerViewFragmentDelegate> {

    private List<RecyclerViewItem> mList;


    @Override
    protected Class<RecyclerViewFragmentDelegate> getDelegateClass() {
        return RecyclerViewFragmentDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
    }


    @Override
    protected void initData() {
        super.initData();
        mList = new ArrayList<>();
        RecyclerViewItem item = new RecyclerViewItem();
        item.setTitleRes(R.string.company_logo);
        item.setItemType(InformationRecyclerViewAdapter.TYPE_ITEM_AVATAR);
        mList.add(item);
        item = new RecyclerViewItem();
        item.setTitleRes(R.string.company_name);
        item.setItemType(InformationRecyclerViewAdapter.TYPE_ITEM_CAN_CHOOSE);
        item.setContent("全盛纺织有限公司");
        mList.add(item);
        item = new RecyclerViewItem();
        item.setTitleRes(R.string.main_industry);
        item.setItemType(InformationRecyclerViewAdapter.TYPE_ITEM_CAN_NOT_CHOOSE);
        item.setContent("面料");
        mList.add(item);
        item = new RecyclerViewItem();
        item.setTitleRes(R.string.management_model);
        item.setContent("生产商");
        item.setItemType(InformationRecyclerViewAdapter.TYPE_ITEM_CAN_NOT_CHOOSE);
        mList.add(item);
        item = new RecyclerViewItem();
        item.setTitleRes(R.string.company_address);
        item.setContent("宁波市社么什么区什么什么路来着呢");
        item.setItemType(InformationRecyclerViewAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        item = new RecyclerViewItem();
        item.setTitleRes(R.string.annual_turnover);
        item.setContent("600万-1000万");
        item.setItemType(InformationRecyclerViewAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        item = new RecyclerViewItem();
        item.setTitleRes(R.string.employees_num);
        item.setContent("50-100人");
        item.setItemType(InformationRecyclerViewAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        item = new RecyclerViewItem();
        item.setTitleRes(R.string.main_products);
        item.setContent("什么类,什么类,什么类,什么类,什么类,什么类,什么类,什么类,什么类,什么类,什么类,什么类");
        item.setItemType(InformationRecyclerViewAdapter.TYPE_ITEM_CONTENT_MULTILINE);
        mList.add(item);
        item = new RecyclerViewItem();
        item.setTitleRes(R.string.main_products);
        item.setContent("本公司什么什么什么类,什么类,什么类,什么类,什么类,什么类,什么类,什么类,什么类,什么类,什么类,什么类");
        item.setItemType(InformationRecyclerViewAdapter.TYPE_ITEM_COMPANY_PROFILE);
        mList.add(item);
        viewDelegate.setData(mList);
    }
}
