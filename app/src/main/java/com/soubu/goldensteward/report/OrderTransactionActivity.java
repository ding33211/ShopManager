package com.soubu.goldensteward.report;

import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.report.OrderTransactionActivityDelegate;
import com.soubu.goldensteward.module.BaseEventBusResp;
import com.soubu.goldensteward.module.EventBusConfig;
import com.soubu.goldensteward.module.server.BaseDataArray;
import com.soubu.goldensteward.module.server.BaseResp;
import com.soubu.goldensteward.module.server.TurnOverServerParams;
import com.soubu.goldensteward.server.RetrofitRequest;
import com.soubu.goldensteward.utils.ConvertUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by dingsigang on 16-11-29.
 */

public class OrderTransactionActivity extends ActivityPresenter<OrderTransactionActivityDelegate> implements View.OnClickListener {

    private ArrayList<Date> mLastWeekBottom;
    private ArrayList<Date> mLastMonthBottom;
    private ArrayList<ArrayList<Integer>> mLastWeekData;
    private ArrayList<ArrayList<Integer>> mLastMonthData;
    private ArrayList<ArrayList<String>> mLastWeekContent;
    private ArrayList<ArrayList<String>> mLastMonthContent;
    private ArrayList<Integer> mColorList;

    //近一周和近一月的间隔
    private int mWeekSpace;
    private int mMonthSpace;

    //是否是近一周状态
    private boolean mIsWeek = true;

    @Override
    protected Class<OrderTransactionActivityDelegate> getDelegateClass() {
        return OrderTransactionActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.order_transaction_data);
    }

    @Override
    protected void initData() {
        super.initData();
        viewDelegate.setPie1Data("252", "134", "54", getString(R.string.order), getString(R.string.proportion));
        viewDelegate.setPie2Data("2000", "9500", "36000", getString(R.string.transaction), getString(R.string.proportion));
        mLastWeekBottom = new ArrayList<>();
        mLastWeekData = new ArrayList<>();
        mLastMonthBottom = new ArrayList<>();
        mLastMonthData = new ArrayList<>();
        mLastWeekContent = new ArrayList<>();
        mLastMonthContent = new ArrayList<>();
        mColorList = new ArrayList<>();
        mColorList.add(getResources().getColor(R.color.colorPrimary));
        RetrofitRequest.getInstance().getTurnOver();
        viewDelegate.setLineViewBottomSize(7);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getDataSuccess(BaseEventBusResp resp) {
        BaseResp resp1 = (BaseResp) resp.getObject();
        int code = resp.getCode();
        switch (code) {
            case EventBusConfig.GET_TURNOVER:
                TurnOverServerParams[] params = (TurnOverServerParams[]) ((BaseDataArray) resp1.getResult()).getData();
                initTurnOverData(params);
                break;
        }

    }

    private void initTurnOverData(TurnOverServerParams[] params) {
        ArrayList<Integer> monthList = new ArrayList<>();
        ArrayList<String> monthContentList = new ArrayList<>();
        int max = 0;
        for (TurnOverServerParams param : params) {
            mLastMonthBottom.add(new Date(Long.valueOf(param.getDate()) * 1000));
            float a = Float.valueOf(param.getPrice());
            int value = (int) (a * 100);
            if (value > max) {
                max = value;
            }
            monthList.add(value);
            monthContentList.add(param.getPrice());
        }
        mMonthSpace = ConvertUtil.regulateSpace(0, max, 5);
        initLineView(monthList, monthContentList, "MM.dd");
    }

    private void initLineView(ArrayList<Integer> monthList, ArrayList<String> monthContentList, String format) {
        ArrayList<Integer> weekList = new ArrayList<>();
        ArrayList<String> weekContentList = new ArrayList<>();
        int max = 0;
        for (int i = 0; i < 7; i++) {
            mLastWeekBottom.add(mLastMonthBottom.get(i));
            int value = monthList.get(i);
            if (value > max) {
                max = value;
            }
            weekList.add(value);
            if (monthContentList != null) {
                weekContentList.add(monthContentList.get(i));
            }
        }
        mWeekSpace = ConvertUtil.regulateSpace(0, max, 5);
        Collections.reverse(mLastMonthBottom);
        Collections.reverse(mLastWeekBottom);
        Collections.reverse(monthList);
        Collections.reverse(weekList);
        if (monthContentList != null) {
            Collections.reverse(monthContentList);
            Collections.reverse(weekContentList);
            mLastWeekContent.add(weekContentList);
            mLastMonthContent.add(monthContentList);
        }
        mLastWeekData.add(weekList);
        mLastMonthData.add(monthList);
        viewDelegate.setBottomTextList(mLastWeekBottom, format);
        viewDelegate.setBarDataList(mLastWeekData, mWeekSpace, mColorList, mLastWeekContent);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.bt_last_week, R.id.bt_last_month);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_last_week:
                if (!mIsWeek) {
                    viewDelegate.setTopBarSelected(0);
                    viewDelegate.setLineViewBottomSize(7);
                    viewDelegate.setBottomTextList(mLastWeekBottom, "MM.dd");
                    viewDelegate.setBarDataList(mLastWeekData, mWeekSpace, mColorList, mLastWeekContent);
                    mIsWeek = true;
                }
                break;
            case R.id.bt_last_month:
                if (mIsWeek) {
                    viewDelegate.setTopBarSelected(1);
                    viewDelegate.setLineViewBottomSize(31);
                    viewDelegate.setBottomTextList(mLastMonthBottom, "MM.dd");
                    viewDelegate.setBarDataList(mLastMonthData, mMonthSpace, mColorList, mLastMonthContent);
                    mIsWeek = false;
                }
                break;
        }
    }
}
