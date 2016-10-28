package com.soubu.goldensteward.view.activity;

import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.LoginActivityDelegate;
import com.soubu.goldensteward.module.Constant;

import static com.soubu.goldensteward.module.Constant.EXTRA_TYPE;

/**
 * Created by lakers on 16/10/25.
 */

public class LoginActivity extends ActivityPresenter<LoginActivityDelegate> implements View.OnClickListener{
    private boolean mDisplayPwd;


    @Override
    protected Class<LoginActivityDelegate> getDelegateClass() {
        return LoginActivityDelegate.class;
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.iv_clear, R.id.iv_transfer_pwd, R.id.btn_login, R.id.tv_register, R.id.tv_forget_pwd);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_clear:
                ((EditText) viewDelegate.get(R.id.et_your_phone)).setText("");
                break;
            case R.id.iv_transfer_pwd:
                if (!mDisplayPwd) {
                    ((EditText) viewDelegate.get(R.id.et_pwd)).setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ((ImageView) viewDelegate.get(R.id.iv_transfer_pwd)).setImageResource(R.drawable.password_show);
                    mDisplayPwd = true;
                } else {
                    ((EditText) viewDelegate.get(R.id.et_pwd)).setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ((ImageView) viewDelegate.get(R.id.iv_transfer_pwd)).setImageResource(R.drawable.password_hide);
                    mDisplayPwd = false;
                }
                break;
            case R.id.btn_login:
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_register:
                Intent intent1 = new Intent(this, RegisterOrForgetPwdActivity.class);
                intent1.putExtra(Constant.EXTRA_TYPE, RegisterOrForgetPwdActivity.TYPE_REGISTER);
                startActivity(intent1);
                break;
            case R.id.tv_forget_pwd:
                Intent intent2 = new Intent(this, RegisterOrForgetPwdActivity.class);
                intent2.putExtra(Constant.EXTRA_TYPE, RegisterOrForgetPwdActivity.TYPE_FORGET_PWD);
                startActivity(intent2);
                break;



        }
    }
}
