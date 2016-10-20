package com.soubu.goldensteward.view.fragment;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.adapter.InformationRvAdapter;
import com.soubu.goldensteward.base.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.delegate.RecyclerViewFragmentDelegate;
import com.soubu.goldensteward.module.InformationRvItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-10-19.
 */
public class PersonalInformationFragment extends FragmentPresenter<RecyclerViewFragmentDelegate> {

    private List<InformationRvItem> mList;

    @Override
    protected Class<RecyclerViewFragmentDelegate> getDelegateClass() {
        return RecyclerViewFragmentDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        viewDelegate.setAdapter(new InformationRvAdapter());

        mList = new ArrayList<>();
        InformationRvItem item = new InformationRvItem();
        item.setTitleRes(R.string.store_name);
        item.setContent("全盛纺织");
        item.setItemType(InformationRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        item = new InformationRvItem();
        item.setTitleRes(R.string.contact);
        item.setItemType(InformationRvAdapter.TYPE_ITEM_CAN_NOT_CHOOSE);
        item.setContent("王祖贤");
        mList.add(item);
        item = new InformationRvItem();
        item.setTitleRes(R.string.position);
        item.setItemType(InformationRvAdapter.TYPE_ITEM_CAN_NOT_CHOOSE);
        item.setContent("总经理");
        mList.add(item);
        item = new InformationRvItem();
        item.setTitleRes(R.string.mobile);
        item.setContent("13666666666");
        item.setItemType(InformationRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        item = new InformationRvItem();
        item.setTitleRes(R.string.email);
        item.setContent("1123333@163.com");
        item.setItemType(InformationRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        item = new InformationRvItem();
        item.setTitleRes(R.string.phone);
        item.setContent("021-22663362");
        item.setItemType(InformationRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        viewDelegate.setData(mList);
    }
}
