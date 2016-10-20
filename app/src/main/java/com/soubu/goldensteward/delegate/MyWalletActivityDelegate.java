package com.soubu.goldensteward.delegate;

import android.widget.GridView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.adapter.CustomGridViewAdapter;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;

import java.util.List;

/**
 * Created by dingsigang on 16-10-20.
 */
public class MyWalletActivityDelegate extends AppDelegate {
    GridView mIncomeGridView;
    CustomGridViewAdapter mAdapter;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_my_wallet;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mIncomeGridView = get(R.id.gv_container);
        mAdapter = new CustomGridViewAdapter(getActivity());
        mIncomeGridView.setAdapter(mAdapter);
    }

    public void setViewTypeList(List<Integer> list){
        mAdapter.setViewTypeList(list);
    }

    public void setSubTitleList(List<String> list){
        mAdapter.setSubTitleList(list);
    }

}
