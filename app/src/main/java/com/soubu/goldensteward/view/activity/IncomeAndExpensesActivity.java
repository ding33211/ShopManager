package com.soubu.goldensteward.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.adapter.IncomeOrExpensesRvAdapter;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.TabViewpagerActivityDelegate;
import com.soubu.goldensteward.module.BaseEventBusResp;
import com.soubu.goldensteward.module.Constant;
import com.soubu.goldensteward.module.EventBusConfig;
import com.soubu.goldensteward.module.server.BaseDataArray;
import com.soubu.goldensteward.module.server.BaseResp;
import com.soubu.goldensteward.module.server.IncomeOrExpensesServerParams;
import com.soubu.goldensteward.module.server.UserServerParams;
import com.soubu.goldensteward.server.RetrofitRequest;
import com.soubu.goldensteward.view.fragment.IncomeOrExpensesFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dingsigang on 16-10-20.
 */
public class IncomeAndExpensesActivity extends ActivityPresenter<TabViewpagerActivityDelegate> {
    List<IncomeOrExpensesServerParams> mList;
    IncomeOrExpensesFragment mIncomeFragment;
    IncomeOrExpensesFragment mExpensesFragment;

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
        mList = new ArrayList<>();
        RetrofitRequest.getInstance().getMyIncome();
        RetrofitRequest.getInstance().getMyExpense();
        List<Fragment> fragments = new ArrayList<>();
        mIncomeFragment = new IncomeOrExpensesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.EXTRA_TYPE, IncomeOrExpensesRvAdapter.TYPE_INCOME);
        mIncomeFragment.setArguments(bundle);
        mExpensesFragment = new IncomeOrExpensesFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt(Constant.EXTRA_TYPE, IncomeOrExpensesRvAdapter.TYPE_EXPENSES);
        mExpensesFragment.setArguments(bundle2);
        fragments.add(mIncomeFragment);
        fragments.add(mExpensesFragment);
        String[] titles = new String[]{getString(R.string.income), getString(R.string.expenses)};
        viewDelegate.initFragment(fragments, titles);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeSuccess(BaseEventBusResp resp) {
        BaseResp resp1 = (BaseResp) resp.getObject();
        int code = resp.getCode();
        if (code == EventBusConfig.GET_MY_INCOME || code == EventBusConfig.GET_MY_EXPENSES) {
            IncomeOrExpensesServerParams[] params = (IncomeOrExpensesServerParams[]) ((BaseDataArray) resp1.getResult()).getData();
            initIncomeOrExpenses(params);
        }

    }

    private void initIncomeOrExpenses(IncomeOrExpensesServerParams[] params) {
        if (params.length == 0) {
            return;
        }
        //支出没有产品名称
        if (params[0].getName() == null) {
            mExpensesFragment.setData(Arrays.asList(params));
        } else {
            mIncomeFragment.setData(Arrays.asList(params));
        }
    }
}
