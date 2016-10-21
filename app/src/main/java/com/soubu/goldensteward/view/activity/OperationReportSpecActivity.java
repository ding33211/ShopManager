package com.soubu.goldensteward.view.activity;

import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.OperationReportSpecActivityDelegate;
import com.soubu.goldensteward.module.Constant;

import java.util.ArrayList;

/**
 * Created by dingsigang on 16-10-21.
 */
public class OperationReportSpecActivity extends ActivityPresenter<OperationReportSpecActivityDelegate> implements View.OnClickListener{
    private int mType;

    public static final int TYPE_TURNOVER = 0x00;


    @Override
    protected Class<OperationReportSpecActivityDelegate> getDelegateClass() {
        return OperationReportSpecActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mType = getIntent().getIntExtra(Constant.EXTRA_TYPE, 0);
        switch (mType){
            case TYPE_TURNOVER:
                viewDelegate.setTitle(R.string.turnover_volume);
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
        for(int i = 0; i < 31; i++){
            list2.add((int)(Math.random() * 50));
        }
        ArrayList<ArrayList<Integer>> barList = new ArrayList<>();
        barList.add(list2);
        ArrayList<Integer> colorList = new ArrayList<>();
        colorList.add(getResources().getColor(R.color.colorPrimary));
        viewDelegate.setBarDataList(barList, 5, colorList);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.tv_last_week, R.id.tv_last_month);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_last_week:
                viewDelegate.clickLastWeek();
                break;
            case R.id.tv_last_month:
                viewDelegate.clickLastMonth();
                break;
        }
    }
}
