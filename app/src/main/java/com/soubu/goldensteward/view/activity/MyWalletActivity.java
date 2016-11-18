package com.soubu.goldensteward.view.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.MyWalletActivityDelegate;
import com.soubu.goldensteward.module.BaseEventBusResp;
import com.soubu.goldensteward.module.Constant;
import com.soubu.goldensteward.module.EventBusConfig;
import com.soubu.goldensteward.module.server.BaseDataObject;
import com.soubu.goldensteward.module.server.BaseResp;
import com.soubu.goldensteward.module.server.WalletHomeInfoServerParams;
import com.soubu.goldensteward.server.RetrofitRequest;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-10-20.
 */
public class MyWalletActivity extends ActivityPresenter<MyWalletActivityDelegate> implements View.OnClickListener {
    @Override
    protected Class<MyWalletActivityDelegate> getDelegateClass() {
        return MyWalletActivityDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        viewDelegate.getToolbar().setBackgroundResource(R.color.orange_wallet);
        RetrofitRequest.getInstance().getMyWalletInfo();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getWalletInfoSuccess(BaseEventBusResp resp) {
        BaseResp resp1 = (BaseResp) resp.getObject();
        int code = resp.getCode();
        if (code == EventBusConfig.GET_MY_WALLET_INFO) {
            WalletHomeInfoServerParams params = (WalletHomeInfoServerParams) ((BaseDataObject) resp1.getResult()).getData();
            initWalletInfo(params);
        }
    }

    private void initWalletInfo(WalletHomeInfoServerParams params) {
        viewDelegate.refreshMoney(params.getAll_money(), params.getPending_payment(), params.getWithdrawals_sum());
        Resources resources = getResources();
        List<String> titleList = new ArrayList<>();
        titleList.add(resources.getString(R.string.accumulated_income_yuan));
        titleList.add(resources.getString(R.string.today_income_yuan));
        titleList.add(resources.getString(R.string.last_week_income_yuan));
        titleList.add(resources.getString(R.string.last_month_income_yuan));
        List<String> subTitleList = new ArrayList<>();
        subTitleList.add(params.getAll_income());
        subTitleList.add(params.getToday_income());
        subTitleList.add(params.getWeek_income());
        subTitleList.add(params.getMonth_income());
        viewDelegate.setData(titleList, subTitleList);
    }


    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.my_wallet);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
//        viewDelegate.setRightMenuOne(R.drawable.wallet_help, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        viewDelegate.setOnClickListener(this, R.id.ll_withdraw, R.id.ll_income_and_expenses, R.id.ll_security_center);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.ll_withdraw:
                intent = new Intent(this, WithDrawActivity.class);
                break;
            case R.id.ll_income_and_expenses:
                intent = new Intent(this, IncomeAndExpensesActivity.class);
                break;
            case R.id.ll_security_center:
                intent = new Intent(this, SecurityCenterActivity.class);
                break;
        }
        if (null != intent) {
            startActivity(intent);
        }
    }
}
