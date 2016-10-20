package com.soubu.goldensteward.view.activity;

import android.content.Intent;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.MyWalletActivityDelegate;
import com.soubu.goldensteward.module.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-10-20.
 */
public class MyWalletActivity extends ActivityPresenter<MyWalletActivityDelegate> implements View.OnClickListener{
    @Override
    protected Class<MyWalletActivityDelegate> getDelegateClass() {
        return MyWalletActivityDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        List<Integer> viewTypeList = new ArrayList<>();
        viewTypeList.add(Constant.GRID_TYPE_ACCUMULATED_INCOME);
        viewTypeList.add(Constant.GRID_TYPE_TODAY_INCOME);
        viewTypeList.add(Constant.GRID_TYPE_LAST_WEEK_INCOME);
        viewTypeList.add(Constant.GRID_TYPE_LAST_MONTH_INCOME);
        viewDelegate.setViewTypeList(viewTypeList);
        List<String> subTitleList = new ArrayList<>();
        subTitleList.add("5000");
        subTitleList.add("2000");
        subTitleList.add("10000");
        subTitleList.add("20000");
        viewDelegate.setSubTitleList(subTitleList);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.my_wallet);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setRightMenuOne(R.drawable.wallet_help, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        viewDelegate.setOnClickListener(this, R.id.ll_withdraw, R.id.ll_income_and_expenses);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.ll_withdraw:
                intent = new Intent(this, WithDrawActivity.class);
                break;
            case R.id.ll_income_and_expenses:
                intent = new Intent(this, IncomeAndExpensesActivity.class);
                break;
        }
        if(null != intent){
            startActivity(intent);
        }
    }
}
