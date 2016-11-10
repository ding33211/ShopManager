package com.soubu.goldensteward.view.activity;

import android.text.TextUtils;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.OperationReportSpecActivityDelegate;
import com.soubu.goldensteward.module.Constant;
import com.soubu.goldensteward.module.server.BaseDataArray;
import com.soubu.goldensteward.module.server.BaseResp;
import com.soubu.goldensteward.module.server.OrderDataArray;
import com.soubu.goldensteward.module.server.OrderServerParams;
import com.soubu.goldensteward.module.server.ProductInOrderListServerParams;
import com.soubu.goldensteward.module.server.ShopVisitorServerParams;
import com.soubu.goldensteward.module.server.TurnOverServerParams;
import com.soubu.goldensteward.module.server.VisitFriendsServerParams;
import com.soubu.goldensteward.module.server.WithCountDataArray;
import com.soubu.goldensteward.server.RetrofitRequest;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
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
    private ArrayList<Integer> mColorList;

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
                RetrofitRequest.getInstance().getProductVisit();
                viewDelegate.initReturnRateRecyclerView();
                viewDelegate.setColorInfo(getString(R.string.return_rate), getString(R.string.return_rate_unit));
                break;
        }
        viewDelegate.setLineViewBottomSize(7);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getDataSuccess(BaseResp resp) {
        if (resp.getResult() instanceof OrderDataArray) {
            if (((BaseDataArray) resp.getResult()).getData() instanceof OrderServerParams[]) {
                OrderServerParams[] params = (OrderServerParams[]) ((BaseDataArray) resp.getResult()).getData();
                OrderDataArray result = (OrderDataArray) resp.getResult();
                if (params.length > 0) {
                    if (!mHaveInitTab) {
                        String[] titles = new String[]{getString(R.string.all) + params.length, getString(R.string.pending_payment) + result.getWait_pay(),
                                getString(R.string.pending_shipped) + result.getWait_send(), getString(R.string.refund_appeal) + result.getOther()};
                        viewDelegate.refreshTabTitle(titles);
                        mHaveInitTab = true;
                    }
                    viewDelegate.initTurnOverVolumeRecyclerView(params);
                }
            }
        }
        if (resp.getResult() instanceof WithCountDataArray) {
            if (((WithCountDataArray) resp.getResult()).getData() instanceof VisitFriendsServerParams[]) {
                WithCountDataArray result = (WithCountDataArray) resp.getResult();
                viewDelegate.initLabel(getString(R.string.contact_friends) + result.getCount());
                viewDelegate.initStoreVisitorRecyclerView((VisitFriendsServerParams[]) result.getData());
            }
            if (((WithCountDataArray) resp.getResult()).getData() instanceof ProductInOrderListServerParams[]) {
                WithCountDataArray result = (WithCountDataArray) resp.getResult();
                viewDelegate.initLabel(getString(R.string.products_on_sale) + result.getCount());
                viewDelegate.initProductAccessRecyclerView((ProductInOrderListServerParams[]) result.getData());
            }

        }

        if (resp.getResult() instanceof BaseDataArray) {
            if (((BaseDataArray) resp.getResult()).getData() instanceof TurnOverServerParams[]) {
                TurnOverServerParams[] params = (TurnOverServerParams[]) ((BaseDataArray) resp.getResult()).getData();
                initTurnOverData(params);
            }
            if (((BaseDataArray) resp.getResult()).getData() instanceof ShopVisitorServerParams[]) {
                ShopVisitorServerParams[] params = (ShopVisitorServerParams[]) ((BaseDataArray) resp.getResult()).getData();
                if (params.length > 0 && TextUtils.isEmpty(params[0].getVisit_count())) {
                    initReturnRateData(params);
                } else if (params.length > 0) {
                    initShopVisitData(params);
                }
            }
        }

    }

    private void initTurnOverData(TurnOverServerParams[] params) {
        ArrayList<Integer> monthList = new ArrayList<>();
        for (TurnOverServerParams param : params) {
            mLastMonthBottom.add(new Date(Long.valueOf(param.getDate()) * 1000));
            monthList.add(Integer.valueOf(param.getPrice()));
        }
        initLineView(monthList);
    }

    private void initShopVisitData(ShopVisitorServerParams[] params) {
        ArrayList<Integer> monthList = new ArrayList<>();
        for (ShopVisitorServerParams param : params) {
            mLastMonthBottom.add(new Date(Long.valueOf(param.getDate()) * 1000));
            monthList.add(Integer.valueOf(param.getVisit_count()));
        }
        initLineView(monthList);
    }

    private void initReturnRateData(ShopVisitorServerParams[] params) {
        ArrayList<Integer> monthList = new ArrayList<>();
        for (ShopVisitorServerParams param : params) {
            mLastMonthBottom.add(new Date(Long.valueOf(param.getDate()) * 1000));
            monthList.add(Integer.valueOf(param.getReturn_rates().substring(0, param.getReturn_rates().length() - 1)));
        }
        initLineView(monthList);
    }

    private void initLineView(ArrayList<Integer> monthList) {
        ArrayList<Integer> weekList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            mLastWeekBottom.add(mLastMonthBottom.get(i));
            weekList.add(monthList.get(i));
        }
        Collections.reverse(mLastMonthBottom);
        Collections.reverse(mLastWeekBottom);
        Collections.reverse(monthList);
        Collections.reverse(weekList);
        mLastWeekData.add(weekList);
        mLastMonthData.add(monthList);
        viewDelegate.setBottomTextList(mLastWeekBottom);
        viewDelegate.setBarDataList(mLastWeekData, 20, mColorList);
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
                    viewDelegate.setBottomTextList(mLastWeekBottom);
                    viewDelegate.setBarDataList(mLastWeekData, 20, mColorList);
                    mIsWeek = true;
                }
                break;
            case R.id.tv_last_month:
                if (mIsWeek) {
                    viewDelegate.clickLastMonth();
                    viewDelegate.setLineViewBottomSize(31);
                    viewDelegate.setBottomTextList(mLastMonthBottom);
                    viewDelegate.setBarDataList(mLastMonthData, 20, mColorList);
                    mIsWeek = false;
                }
                break;
        }
    }
}
