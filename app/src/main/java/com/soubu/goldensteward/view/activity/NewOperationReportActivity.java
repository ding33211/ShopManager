package com.soubu.goldensteward.view.activity;

import android.content.Intent;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.NewOperationReportActivityDelegate;
import com.soubu.goldensteward.utils.ConvertUtil;
import com.soubu.goldensteward.widget.linebarchart.LineView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by dingsigang on 16-11-29.
 */

public class NewOperationReportActivity extends ActivityPresenter<NewOperationReportActivityDelegate> implements View.OnClickListener {
    ArrayList<ArrayList<Integer>> mLists;

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.operation_report);
    }

    @Override
    protected void initData() {
        super.initData();
        viewDelegate.setLineViewBottomSize(7);
        ArrayList<Date> list = new ArrayList<>();
        Date date = new Date();
        list.add(date);
        list.add(date);
        list.add(date);
        list.add(date);
        list.add(date);
        list.add(date);
        list.add(date);
        viewDelegate.setBottomTextList(list, "MM.dd");
        ArrayList<Integer> colorList = new ArrayList<>();
        colorList.add(getResources().getColor(R.color.pickerview_timebtn_nor));
        colorList.add(getResources().getColor(R.color.bank_account_blue));
        colorList.add(getResources().getColor(R.color.orange_wallet_sub));
        ArrayList<Integer> data1 = new ArrayList<>();
        ArrayList<Integer> data2 = new ArrayList<>();
        ArrayList<Integer> data3 = new ArrayList<>();
        int max = 0;
        for (int i = 0; i < 7; i++) {
            int a = new Random().nextInt(100) * 100;
            data1.add(a);
            if (max < a) {
                max = a;
            }
            int b = new Random().nextInt(100) * 100;
            data2.add(b);
            if (max < b) {
                max = b;
            }
            int c = new Random().nextInt(100) * 100;
            data3.add(c);
            if (max < c) {
                max = c;
            }
        }
        mLists = new ArrayList<>();
        mLists.add(data1);
        mLists.add(data2);
        mLists.add(data3);
        int space = ConvertUtil.regulateSpace(0, max, 5);
        viewDelegate.setDataList(mLists, space, colorList);
    }

    @Override
    protected Class<NewOperationReportActivityDelegate> getDelegateClass() {
        return NewOperationReportActivityDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.bt_last_week, R.id.bt_last_month,
                R.id.bt_last_half_year, R.id.ll_order_transaction_data, R.id.ll_store_flow, R.id.ll_refund_data);
        viewDelegate.setLineIndexClickListener(new LineView.OnClickIndexListener() {
            @Override
            public void onclick(int index) {
                List<Integer> data = new ArrayList<>();
                for (List<Integer> list : mLists) {
                    data.add(list.get(index) / 100);
                }
                viewDelegate.initBottomData(data);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_last_week:
                viewDelegate.setTopBarSelected(0);
                break;
            case R.id.bt_last_month:
                viewDelegate.setTopBarSelected(1);
                break;
            case R.id.bt_last_half_year:
                viewDelegate.setTopBarSelected(2);
                break;
            case R.id.ll_order_transaction_data:
                intent = new Intent(this, OrderTransactionActivity.class);
                break;
            case R.id.ll_store_flow:
                intent = new Intent(this, StoreFlowStatisticsActivity.class);
                break;
            case R.id.ll_refund_data:
                intent = new Intent(this, RefundDataActivity.class);
                break;

        }
        if (null != intent) {
            startActivity(intent);
        }
    }
}
