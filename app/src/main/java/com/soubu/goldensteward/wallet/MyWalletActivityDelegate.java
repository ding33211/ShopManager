package com.soubu.goldensteward.wallet;

import android.widget.GridView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.home.HomeGridViewAdapter;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;

import java.util.List;

/**
 * Created by dingsigang on 16-10-20.
 */
public class MyWalletActivityDelegate extends AppDelegate {
    GridView mIncomeGridView;
    HomeGridViewAdapter mAdapter;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_my_wallet;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mIncomeGridView = get(R.id.gv_container);
        mAdapter = new HomeGridViewAdapter(getActivity());
        mIncomeGridView.setAdapter(mAdapter);
    }

    public void setData(List<String> titleList, List<String> subTitleList){
        mAdapter.setData(null, titleList, subTitleList);
    }

    public void refreshMoney(String total, String pending, String canWithDraw){
        ((TextView) get(R.id.tv_total)).setText(total);
        ((TextView) get(R.id.tv_pending)).setText(pending);
        ((TextView) get(R.id.tv_withdraw)).setText(canWithDraw);

    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }
}
