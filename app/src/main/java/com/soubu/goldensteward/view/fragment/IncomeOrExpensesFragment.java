package com.soubu.goldensteward.view.fragment;

import com.soubu.goldensteward.adapter.IncomeOrExpensesRvAdapter;
import com.soubu.goldensteward.base.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.delegate.RecyclerViewFragmentDelegate;
import com.soubu.goldensteward.module.Constant;
import com.soubu.goldensteward.module.IncomeOrExpensesRvItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-10-20.
 */
public class IncomeOrExpensesFragment extends FragmentPresenter<RecyclerViewFragmentDelegate> {
    private List<IncomeOrExpensesRvItem> mList;


    @Override
    protected Class<RecyclerViewFragmentDelegate> getDelegateClass() {
        return RecyclerViewFragmentDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        int type = getArguments().getInt(Constant.EXTRA_TYPE);
        viewDelegate.setAdapter(new IncomeOrExpensesRvAdapter(type));
        mList = new ArrayList<>();
        IncomeOrExpensesRvItem item = new IncomeOrExpensesRvItem();
        item.setAmount("100.00");
        mList.add(item);
        item = new IncomeOrExpensesRvItem();
        item.setAmount("200.00");
        mList.add(item);
        item = new IncomeOrExpensesRvItem();
        item.setAmount("300.00");
        mList.add(item);
        item = new IncomeOrExpensesRvItem();
        item.setAmount("2050.00");
        mList.add(item);
        item = new IncomeOrExpensesRvItem();
        item.setAmount("2100.00");
        mList.add(item);
        item = new IncomeOrExpensesRvItem();
        item.setAmount("2060.00");
        mList.add(item);
        item = new IncomeOrExpensesRvItem();
        item.setAmount("2800.00");
        mList.add(item);
        item = new IncomeOrExpensesRvItem();
        item.setAmount("2090.00");
        mList.add(item);
        viewDelegate.setData(mList);
    }
}
