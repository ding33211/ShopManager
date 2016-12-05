package com.soubu.goldensteward.report;

import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.report.OperationReportSpecActivityDelegate;
import com.soubu.goldensteward.module.BaseEventBusResp;
import com.soubu.goldensteward.module.Constant;
import com.soubu.goldensteward.module.EventBusConfig;
import com.soubu.goldensteward.module.server.BaseDataArray;
import com.soubu.goldensteward.module.server.BaseResp;
import com.soubu.goldensteward.module.server.EvaluateInReturnRateServerParams;
import com.soubu.goldensteward.module.server.OrderDataArray;
import com.soubu.goldensteward.module.server.OrderServerParams;
import com.soubu.goldensteward.module.server.ProductInOrderListServerParams;
import com.soubu.goldensteward.module.server.ShopVisitorServerParams;
import com.soubu.goldensteward.module.server.TurnOverServerParams;
import com.soubu.goldensteward.module.server.VisitFriendsServerParams;
import com.soubu.goldensteward.module.server.WithCountDataArray;
import com.soubu.goldensteward.server.RetrofitRequest;
import com.soubu.goldensteward.utils.ConvertUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

/**
 * Created by dingsigang on 16-10-21.
 */
public class OperationReportSpecActivity extends ActivityPresenter<OperationReportSpecActivityDelegate> implements View.OnClickListener {
    private int mType;

    public static final int TYPE_TURNOVER = 0x00;
    public static final int TYPE_STORE_VISITOR = 0x01;
    public static final int TYPE_PRODUCT_ACCESS = 0x02;
    public static final int TYPE_REFUND_RATE = 0x03;

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
    //是否已经初始化了tab
    private boolean mHaveInitTab = false;


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
        mLastWeekBottom = new ArrayList<>();
        mLastWeekData = new ArrayList<>();
        mLastMonthBottom = new ArrayList<>();
        mLastMonthData = new ArrayList<>();
        mLastWeekContent = new ArrayList<>();
        mLastMonthContent = new ArrayList<>();
        mColorList = new ArrayList<>();
        mColorList.add(getResources().getColor(R.color.colorPrimary));
        switch (mType) {
            case TYPE_TURNOVER:
                RetrofitRequest.getInstance().getTurnOver();
                RetrofitRequest.getInstance().getOrderList(0);
                viewDelegate.setColorInfo(getString(R.string.turnover_volume), getString(R.string.turnover_volume_unit));
                break;
            case TYPE_STORE_VISITOR:
                RetrofitRequest.getInstance().getShopVisit();
                RetrofitRequest.getInstance().getVisitFriends();
                viewDelegate.setColorInfo(getString(R.string.store_visitor), getString(R.string.store_visitor_unit));
                break;
            case TYPE_PRODUCT_ACCESS:
                RetrofitRequest.getInstance().getProductVisit();
                RetrofitRequest.getInstance().getProductListOnSale();
                viewDelegate.setColorInfo(getString(R.string.product_access), getString(R.string.product_access_unit));
                break;
            case TYPE_REFUND_RATE:
                RetrofitRequest.getInstance().getReturnRates();
                RetrofitRequest.getInstance().getAllEvaluateInReturnRate();
                viewDelegate.initReturnRateView();

                viewDelegate.setColorInfo(getString(R.string.return_rate), getString(R.string.return_rate_unit));
                break;
        }
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
            case EventBusConfig.GET_ORDER_LIST:
                OrderServerParams[] params1 = (OrderServerParams[]) ((BaseDataArray) resp1.getResult()).getData();
                OrderDataArray result = (OrderDataArray) resp1.getResult();
                if (params1.length > 0) {
                    if (!mHaveInitTab) {
                        String[] titles = new String[]{getString(R.string.all) + params1.length, getString(R.string.pending_payment) + result.getWait_pay(),
                                getString(R.string.pending_shipped) + result.getWait_send(), getString(R.string.refund_appeal) + result.getOther()};
                        viewDelegate.refreshTabTitle(titles);
                        mHaveInitTab = true;
                    }
                    viewDelegate.initTurnOverVolumeRecyclerView(params1);
                }
                break;
            case EventBusConfig.GET_PRODUCT_VISIT:
            case EventBusConfig.GET_SHOP_VISIT:
                ShopVisitorServerParams[] params2 = (ShopVisitorServerParams[]) ((BaseDataArray) resp1.getResult()).getData();
                initShopVisitData(params2);
                break;
            case EventBusConfig.GET_VISIT_FRIENDS:
                WithCountDataArray result1 = (WithCountDataArray) resp1.getResult();
                viewDelegate.initLabel(getString(R.string.contact_friends) + "(共" + result1.getCount() + "人)");
                viewDelegate.initStoreVisitorRecyclerView((VisitFriendsServerParams[]) result1.getData());
                break;
            case EventBusConfig.GET_PRODUCT_LIST_ON_SALE:
                WithCountDataArray result2 = (WithCountDataArray) resp1.getResult();
                viewDelegate.initLabel(getString(R.string.products_on_sale) + "(共" + result2.getCount() + "件)");
                viewDelegate.initProductAccessRecyclerView((ProductInOrderListServerParams[]) result2.getData());
                break;
            case EventBusConfig.GET_RETURN_RATES:
                ShopVisitorServerParams[] params3 = (ShopVisitorServerParams[]) ((BaseDataArray) resp1.getResult()).getData();
                initReturnRateData(params3);
                break;
            case EventBusConfig.GET_ALL_EVALUATE_IN_RETURN_RATES:
                WithCountDataArray result3 = (WithCountDataArray) resp1.getResult();
                EvaluateInReturnRateServerParams[] params4 = (EvaluateInReturnRateServerParams[]) result3.getData();
                viewDelegate.initReturnRateRecyclerView(Arrays.asList(params4));
                viewDelegate.initLabel(getString(R.string.all_evaluate) + "(共" + result3.getCount() + "条)");

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
            if(value > max){
                max = value;
            }
            monthList.add(value);
            monthContentList.add(param.getPrice());
        }
        mMonthSpace = ConvertUtil.regulateSpace(0, max, 5);
        initLineView(monthList, monthContentList, "MM.dd");
    }

    private void initShopVisitData(ShopVisitorServerParams[] params) {
        ArrayList<Integer> monthList = new ArrayList<>();
        ArrayList<String> monthContentList = new ArrayList<>();
        int max = 0;
        for (ShopVisitorServerParams param : params) {
            mLastMonthBottom.add(new Date(Long.valueOf(param.getDate()) * 1000));
            float a = Float.valueOf(param.getVisit_count());
            int value = (int) (a * 100);
            if (value > max) {
                max = value;
            }
            monthList.add(value);
            monthContentList.add(param.getVisit_count());

        }
        mMonthSpace = ConvertUtil.regulateSpace(0, max, 5);
        initLineView(monthList, monthContentList, "MM.dd");
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

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.tv_last_week, R.id.tv_last_month);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_last_week:
                if (!mIsWeek) {
                    viewDelegate.clickLastWeek();
                    viewDelegate.setLineViewBottomSize(7);
                    viewDelegate.setBottomTextList(mLastWeekBottom, "MM.dd");
                    viewDelegate.setBarDataList(mLastWeekData, mWeekSpace, mColorList, mLastWeekContent);
                    mIsWeek = true;
                }
                break;
            case R.id.tv_last_month:
                if (mIsWeek) {
                    viewDelegate.clickLastMonth();
                    viewDelegate.setLineViewBottomSize(31);
                    viewDelegate.setBottomTextList(mLastMonthBottom, "MM.dd");
                    viewDelegate.setBarDataList(mLastMonthData, mMonthSpace, mColorList, mLastMonthContent);
                    mIsWeek = false;
                }
                break;
        }
    }
}
