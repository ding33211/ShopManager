package com.soubu.goldensteward.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.adapter.IncomeOrExpensesRvAdapter;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.OperationReportSpecActivityDelegate;
import com.soubu.goldensteward.module.Constant;
import com.soubu.goldensteward.view.fragment.IncomeOrExpensesFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-10-21.
 */
public class OperationReportSpecActivity extends ActivityPresenter<OperationReportSpecActivityDelegate> implements View.OnClickListener {
    private int mType;

    public static final int TYPE_TURNOVER = 0x00;
    public static final int TYPE_STORE_VISITOR = 0x01;
    public static final int TYPE_PRODUCT_ACCESS = 0x02;
    public static final int TYPE_REFUND_RATE = 0x03;


    @Override
    protected Class<OperationReportSpecActivityDelegate> getDelegateClass() {
        return OperationReportSpecActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();

        mType = getIntent().getIntExtra(Constant.EXTRA_TYPE, 0);
        switch (mType) {
            case TYPE_TURNOVER:
                viewDelegate.setTitle(R.string.turnover_volume);
                break;
            case TYPE_STORE_VISITOR:
                viewDelegate.setTitle(R.string.store_visitor);
                break;
            case TYPE_PRODUCT_ACCESS:
                viewDelegate.setTitle(R.string.product_access);
                break;
            case TYPE_REFUND_RATE:
                viewDelegate.setTitle(R.string.return_rate);
                break;
        }
    }

    @Override
    protected void initData() {
        super.initData();
        ArrayList<String> test = new ArrayList<String>();
        for (int i = 0; i < 31; i++) {
            test.add("9." + (i + 1));
        }
        viewDelegate.setBottomTextList(test);
//        ArrayList<Integer> list = new ArrayList<>();
//        for(int i = 0; i < 12; i++){
//            list.add((int)(Math.random() * 1000));
//            Log.e("1111111", list.get(i) + "");
//        }
//        ArrayList<ArrayList<Integer>> lineList = new ArrayList<>();
//        lineList.add(list);
//        viewDelegate.setLineDataList(lineList, 200);
        ArrayList<Integer> list2 = new ArrayList<>();
        for (int i = 0; i < 31; i++) {
            list2.add((int) (Math.random() * 50));
        }
        ArrayList<ArrayList<Integer>> barList = new ArrayList<>();
        barList.add(list2);
        ArrayList<Integer> colorList = new ArrayList<>();
        colorList.add(getResources().getColor(R.color.colorPrimary));
        viewDelegate.setBarDataList(barList, 5, colorList);

//        List<Fragment> fragments = new ArrayList<>();
//        Fragment incomeFragment = new IncomeOrExpensesFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt(Constant.EXTRA_TYPE, IncomeOrExpensesRvAdapter.TYPE_INCOME);
//        incomeFragment.setArguments(bundle);
//        Fragment expensesFragment = new IncomeOrExpensesFragment();
//        Bundle bundle2 = new Bundle();
//        bundle.putInt(Constant.EXTRA_TYPE, IncomeOrExpensesRvAdapter.TYPE_EXPENSES);
//        expensesFragment.setArguments(bundle2);
//        fragments.add(incomeFragment);
//        fragments.add(expensesFragment);
//        fragments.add(incomeFragment);
//        fragments.add(expensesFragment);
        switch (mType){
            case TYPE_TURNOVER:
                String[] titles = new String[]{getString(R.string.all), getString(R.string.pending_payment), getString(R.string.pending_shipped), getString(R.string.refund_appeal)};
                viewDelegate.initTurnOverVolumeRecyclerView(titles);
                break;
            case TYPE_STORE_VISITOR:
                viewDelegate.initStoreVisitorRecyclerView();
                break;
            case TYPE_PRODUCT_ACCESS:
                viewDelegate.initProductAccessRecyclerView();
                break;
            case TYPE_REFUND_RATE:
                viewDelegate.initReturnRateRecyclerView();
                break;
        }


    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.tv_last_week, R.id.tv_last_month);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_last_week:
                viewDelegate.clickLastWeek();
                break;
            case R.id.tv_last_month:
                viewDelegate.clickLastMonth();
                break;
        }
    }
}
