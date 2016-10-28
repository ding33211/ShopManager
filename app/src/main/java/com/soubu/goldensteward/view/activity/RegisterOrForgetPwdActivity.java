package com.soubu.goldensteward.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.RegisterOrForgetPwdActivityDelegate;
import com.soubu.goldensteward.module.Constant;

/**
 * Created by lakers on 16/10/26.
 */

public class RegisterOrForgetPwdActivity extends ActivityPresenter<RegisterOrForgetPwdActivityDelegate> implements View.OnClickListener{
    public static final int TYPE_REGISTER = 0x00;
    public static final int TYPE_FORGET_PWD = 0x01;

    private int mType;


    @Override
    protected Class<RegisterOrForgetPwdActivityDelegate> getDelegateClass() {
        return RegisterOrForgetPwdActivityDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.tv_send_verify_code, R.id.btn_next_step);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mType = getIntent().getIntExtra(Constant.EXTRA_TYPE, 0);
        if(mType == TYPE_REGISTER){
            viewDelegate.setTitle(R.string.supplier_register);
        } else {
            viewDelegate.setTitle(R.string.forget_password);
            ((Button) viewDelegate.get(R.id.btn_next_step)).setText(R.string.find_pwd);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_send_verify_code:
                v.setEnabled(false);
                viewDelegate.startTimer();
                break;
            case R.id.btn_next_step:
                if(mType == TYPE_REGISTER){
                    Intent intent = new Intent(this, RegisterSupplierActivity.class);
                    startActivity(intent);
                    //现在没有关联店铺这个东西了
//                    new AlertDialog.Builder(this).setTitle(R.string.alert).setMessage(R.string.phone_has_been_registered)
//                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                }
//                            }).setPositiveButton(R.string.refer_store, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            viewDelegate.gotoReferStore();
//                        }
//                    }).show();
                }
                break;

        }
    }
}
