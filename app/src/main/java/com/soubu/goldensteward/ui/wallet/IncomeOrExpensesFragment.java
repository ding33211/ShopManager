package com.soubu.goldensteward.ui.wallet;

import com.soubu.goldensteward.support.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.support.delegate.RecyclerViewFragmentDelegate;
import com.soubu.goldensteward.support.bean.Constant;
import com.soubu.goldensteward.support.bean.server.IncomeOrExpensesServerParams;

import java.util.List;

/**
 * Created by dingsigang on 16-10-20.
 */
public class IncomeOrExpensesFragment extends FragmentPresenter<RecyclerViewFragmentDelegate> {

    @Override
    protected Class<RecyclerViewFragmentDelegate> getDelegateClass() {
        return RecyclerViewFragmentDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        int type = getArguments().getInt(Constant.EXTRA_TYPE);
        viewDelegate.setAdapter(new IncomeOrExpensesRvAdapter(type, getActivity()));
    }

    public void setData(List<IncomeOrExpensesServerParams> list){
        viewDelegate.setData(list);
    }
}
