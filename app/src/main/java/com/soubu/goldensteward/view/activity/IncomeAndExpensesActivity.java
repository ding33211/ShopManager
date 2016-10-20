package com.soubu.goldensteward.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.adapter.IncomeOrExpensesRvAdapter;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.TabViewpagerActivityDelegate;
import com.soubu.goldensteward.module.Constant;
import com.soubu.goldensteward.view.fragment.IncomeOrExpensesFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-10-20.
 */
public class IncomeAndExpensesActivity extends ActivityPresenter<TabViewpagerActivityDelegate> {
    @Override
    protected Class<TabViewpagerActivityDelegate> getDelegateClass() {
        return TabViewpagerActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.income_and_expenses);
    }

    @Override
    protected void initData() {
        super.initData();
        List<Fragment> fragments = new ArrayList<>();
        Fragment incomeFragment = new IncomeOrExpensesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.EXTRA_TYPE, IncomeOrExpensesRvAdapter.TYPE_INCOME);
        incomeFragment.setArguments(bundle);
        Fragment expensesFragment = new IncomeOrExpensesFragment();
        Bundle bundle2 = new Bundle();
        bundle.putInt(Constant.EXTRA_TYPE, IncomeOrExpensesRvAdapter.TYPE_EXPENSES);
        expensesFragment.setArguments(bundle2);
        fragments.add(incomeFragment);
        fragments.add(expensesFragment);
        String[] titles = new String[]{getString(R.string.income), getString(R.string.expenses)};
        viewDelegate.initFragment(fragments, titles);
    }
}
