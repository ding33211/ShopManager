package com.soubu.goldensteward.ui.report;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.support.bean.BaseEventBusResp;
import com.soubu.goldensteward.support.bean.EventBusConfig;
import com.soubu.goldensteward.support.bean.server.BaseDataArray;
import com.soubu.goldensteward.support.bean.server.BaseResp;
import com.soubu.goldensteward.support.bean.server.OrderServerParams;
import com.soubu.goldensteward.support.bean.server.ShopVisitorServerParams;
import com.soubu.goldensteward.support.net.RetrofitRequest;
import com.soubu.goldensteward.support.utils.ConvertUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by dingsigang on 16-11-30.
 */

public class RefundDataActivity extends ActivityPresenter<RefundDataActivityDelegate> {

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

    @Override
    protected Class<RefundDataActivityDelegate> getDelegateClass() {
        return RefundDataActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.refund_data);
    }

    @Override
    protected void initData() {
        super.initData();
        mLastWeekBottom = new ArrayList<>();
        mLastWeekData = new ArrayList<>();
        mLastMonthBottom = new ArrayList<>();
        mLastMonthData = new ArrayList<>();
        mLastWeekContent = new ArrayList<>();
        mLastMonthContent = new ArrayList<>();
        mColorList = new ArrayList<>();
        mColorList.add(getResources().getColor(R.color.colorPrimary));
        RetrofitRequest.getInstance().getReturnRates();
        RetrofitRequest.getInstance().getOrderList(3);
        viewDelegate.setLineViewBottomSize(7);

    }

    @Override
    protected void initView() {
        super.initView();
        viewDelegate.initTabLayout(new String[]{getString(R.string.refund_order)});
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getDataSuccess(BaseEventBusResp resp) {
        BaseResp resp1 = (BaseResp) resp.getObject();
        int code = resp.getCode();
        switch (code) {
            case EventBusConfig.GET_RETURN_RATES:
                ShopVisitorServerParams[] params3 = (ShopVisitorServerParams[]) ((BaseDataArray) resp1.getResult()).getData();
                initReturnRateData(params3);
                break;
            case EventBusConfig.GET_ORDER_LIST:
                OrderServerParams[] params1 = (OrderServerParams[]) ((BaseDataArray) resp1.getResult()).getData();
                if (params1.length > 0) {
                    viewDelegate.initTurnOverVolumeRecyclerView(params1);
                }
                break;
        }

    }

    private void initReturnRateData(ShopVisitorServerParams[] params) {
        ArrayList<Integer> monthList = new ArrayList<>();
        ArrayList<String> monthContentList = new ArrayList<>();
        int max = 0;
        for (ShopVisitorServerParams param : params) {
            mLastMonthBottom.add(new Date(Long.valueOf(param.getDate()) * 1000));
            float a = Float.valueOf(param.getRefund());
            int value = (int) (a * 100);
            if (value > max) {
                max = value;
            }
            monthList.add(value);
            monthContentList.add(param.getRefund());
        }
        mMonthSpace = ConvertUtil.regulateSpace(0, max, 5);
        initLineView(monthList, monthContentList, "yy-MM");
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
}
