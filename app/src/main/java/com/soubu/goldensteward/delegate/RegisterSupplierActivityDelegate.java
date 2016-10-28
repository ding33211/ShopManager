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
    RegisterSupplierRvAdapter mAdapter1;
    RegisterSupplierRvAdapter mAdapter2;


    @Override
    public int getRootLayoutId() {
        return R.layout.activity_register_supplier;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mRvContent = get(R.id.rv_content);
        mRvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter1 = new RegisterSupplierRvAdapter(getActivity());
        mAdapter2 = new RegisterSupplierRvAdapter(getActivity());
        mRvContent.setAdapter(mAdapter1);
    }


    public void setStep1Data(List<RegisterRvItem> list){
        mAdapter1.setData(list);
        mAdapter1.notifyDataSetChanged();
    }

    public void setStep2Data(List<RegisterRvItem> list){
        mAdapter2.setData(list);
    }

    public void clickNextStep(){
        ((TextView)get(R.id.tv_label)).setText(R.string.company_info);
        ((Button)get(R.id.btn_next_step)).setText(R.string.finish_register);
        mAdapter2.notifyDataSetChanged();
        mRvContent.setAdapter(mAdapter2);
    }

    public void onClickBackOnSecondStep(){
        ((TextView)get(R.id.tv_label)).setText(R.string.account_info);
        ((Button)get(R.id.btn_next_step)).setText(R.string.next_step);
        mAdapter1.notifyDataSetChanged();
        mRvContent.setAdapter(mAdapter1);
    }

    public boolean ifStep2Empty(){
        return mAdapter2.getItemCount() == 0;
    }

}
