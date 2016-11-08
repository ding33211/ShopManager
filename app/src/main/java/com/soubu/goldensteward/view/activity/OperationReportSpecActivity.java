package com.soubu.goldensteward.view.activity;

import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.OperationReportSpecActivityDelegate;
import com.soubu.goldensteward.module.Constant;
import com.soubu.goldensteward.module.server.BaseDataArray;
import com.soubu.goldensteward.module.server.BaseResp;
import com.soubu.goldensteward.module.server.TurnOverServerParams;
import com.soubu.goldensteward.server.RetrofitRequest;
import com.soubu.goldensteward.utils.ConvertUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
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

        switch (mType) {
            case TYPE_TURNOVER:
                String[] titles = new String[]{getString(R.string.all), getString(R.string.pending_payment), getString(R.string.pending_shipped), getString(R.string.refund_appeal)};
                RetrofitRequest.getInstance().getTurnOver();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getDataSuccess(BaseResp resp) {
        if (resp.getResult() instanceof BaseDataArray) {
            if (((BaseDataArray) resp.getResult()).getData() instanceof TurnOverServerParams[]) {
                TurnOverServerParams[] params = (TurnOverServerParams[]) ((BaseDataArray) resp.getResult()).getData();
                initTurnOverData(params);
            }
        }
    }

    private void initTurnOverData(TurnOverServerParams[] params){
        ArrayList<String> test = new ArrayList<String>();
        ArrayList<Integer> list2 = new ArrayList<>();
        for(TurnOverServerParams param : params){
            test.add(ConvertUtil.dateToMMPointDD(new Date(Long.valueOf(param.getDate()) * 1000)));
            list2.add(Integer.valueOf(param.getPrice()));
        }
        viewDelegate.setBottomTextList(test);
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
