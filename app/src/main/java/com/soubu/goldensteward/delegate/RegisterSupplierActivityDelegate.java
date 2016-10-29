package com.soubu.goldensteward.delegate;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.adapter.RegisterSupplierRvAdapter;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;
import com.soubu.goldensteward.module.RegisterRvItem;

import java.util.List;

/**
 * Created by lakers on 16/10/27.
 */

public class RegisterSupplierActivityDelegate extends AppDelegate {
    RecyclerView mRvContent;
    List<RegisterRvItem> mList1;
    List<RegisterRvItem> mList2;
    RegisterSupplierRvAdapter mAdapter;


    @Override
    public int getRootLayoutId() {
        return R.layout.activity_register_supplier;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mRvContent = get(R.id.rv_content);
        mRvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new RegisterSupplierRvAdapter(getActivity());
        mRvContent.setAdapter(mAdapter);
    }


    public void setStep1Data(List<RegisterRvItem> list){
        mList1 = list;
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }

    public void setStep2Data(List<RegisterRvItem> list){
        mList2 = list;
    }

    public void clickNextStep(){
        ((TextView)get(R.id.tv_label)).setText(R.string.company_info);
        ((Button)get(R.id.btn_next_step)).setText(R.string.finish_register);
        mAdapter.setData(mList2);
        mAdapter.notifyDataSetChanged();
    }

    public void onClickBackOnSecondStep(){
        ((TextView)get(R.id.tv_label)).setText(R.string.account_info);
        ((Button)get(R.id.btn_next_step)).setText(R.string.next_step);
        mAdapter.setData(mList1);
        mAdapter.notifyDataSetChanged();
    }

    public boolean ifStep2Init(){
        return mList2 != null;
    }

}
