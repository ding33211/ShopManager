package com.soubu.goldensteward.view.activity;

import android.content.Intent;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.adapter.RegisterSupplierRvAdapter;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.RegisterSupplierActivityDelegate;
import com.soubu.goldensteward.module.RegisterRvItem;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

/**
 * Created by lakers on 16/10/27.
 */

public class RegisterSupplierActivity extends ActivityPresenter<RegisterSupplierActivityDelegate> implements View.OnClickListener{

    private boolean mGoNextStep = false;

    @Override
    protected Class<RegisterSupplierActivityDelegate> getDelegateClass() {
        return RegisterSupplierActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.supplier_register);
    }

    @Override
    protected void initData() {
        super.initData();
        initRegisterFirst();
    }

    void initRegisterFirst(){
        List<RegisterRvItem> list = new ArrayList<>();
        list.add(createItem(R.string.store_name, null, RegisterSupplierRvAdapter.TYPE_ITEM_MUST_FILL, 0, 0, 0));
        list.add(createItem(R.string.contact_name, null, RegisterSupplierRvAdapter.TYPE_ITEM_MUST_FILL, 0, 0, 0));
        list.add(createItem(R.string.position_in, null, RegisterSupplierRvAdapter.TYPE_ITEM_MUST_CHOOSE, 0,R.array.clue_status, R.array.clue_status_web));
        list.add(createItem(R.string.email, null, RegisterSupplierRvAdapter.TYPE_ITEM_CAN_FILL, InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS, 0, 0));
        list.add(createItem(R.string.phone, null, RegisterSupplierRvAdapter.TYPE_ITEM_CAN_FILL, InputType.TYPE_CLASS_PHONE, 0, 0));
        viewDelegate.setStep1Data(list);
    }

    void initRegisterSecond(){
        if(viewDelegate.ifStep2Empty()){
            List<RegisterRvItem> list = new ArrayList<>();
            list.add(createItem(R.string.company_name, null, RegisterSupplierRvAdapter.TYPE_ITEM_MUST_FILL, 0, 0, 0));
            list.add(createItem(R.string.main_industry, null, RegisterSupplierRvAdapter.TYPE_ITEM_MUST_CHOOSE, 0, R.array.clue_status, R.array.clue_status_web));
            list.add(createItem(R.string.main_products, null, RegisterSupplierRvAdapter.TYPE_ITEM_MUST_CHOOSE, 0,R.array.clue_status, R.array.clue_status_web));
            list.add(createItem(R.string.management_model, null, RegisterSupplierRvAdapter.TYPE_ITEM_MUST_CHOOSE, 0,R.array.clue_status, R.array.clue_status_web));
            list.add(createItem(R.string.location_at, null, RegisterSupplierRvAdapter.TYPE_ITEM_MUST_FILL, 0, 0, 0));
            list.add(createItem(R.string.detail_address, null, RegisterSupplierRvAdapter.TYPE_ITEM_MUST_FILL, 0, 0, 0));
            list.add(createItem(R.string.annual_turnover, null, RegisterSupplierRvAdapter.TYPE_ITEM_MUST_CHOOSE, 0,R.array.clue_status, R.array.clue_status_web));
            list.add(createItem(R.string.employees_num, null, RegisterSupplierRvAdapter.TYPE_ITEM_MUST_CHOOSE, 0,R.array.clue_status, R.array.clue_status_web));
            list.add(createItem(R.string.company_profile, getString(R.string.company_profile_desc), RegisterSupplierRvAdapter.TYPE_ITEM_MULTILINE, 0, 0, 0));
            viewDelegate.setStep2Data(list);
        } else {

        }

    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.btn_next_step);
    }

    private RegisterRvItem createItem(int titleRes, String content, int type, int editType, int arrayRes, int webArrayRes){
        RegisterRvItem item = new RegisterRvItem();
        item.setTitleRes(titleRes);
        item.setType(type);
        item.setContent(content);
        if(editType != 0){
            item.setEditType(editType);
        }
        if(arrayRes != 0){
            item.setArrayRes(arrayRes);
        }
        if(webArrayRes != 0){
            item.setWebArrayRes(webArrayRes);
        }
        return item;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next_step:
                if(!mGoNextStep){
                    initRegisterSecond();
                    viewDelegate.clickNextStep();
                    mGoNextStep = true;
                } else {
                    Intent intent = new Intent(this, StoreOwnerVerifyActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(mGoNextStep){
            viewDelegate.onClickBackOnSecondStep();
            mGoNextStep = false;
        } else {
            super.onBackPressed();
        }
    }
}
