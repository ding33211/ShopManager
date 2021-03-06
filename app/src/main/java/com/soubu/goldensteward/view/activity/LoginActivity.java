package com.soubu.goldensteward.view.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.soubu.goldensteward.GoldenStewardApplication;
import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.LoginActivityDelegate;
import com.soubu.goldensteward.module.BaseEventBusResp;
import com.soubu.goldensteward.module.Constant;
import com.soubu.goldensteward.module.EventBusConfig;
import com.soubu.goldensteward.module.server.BaseDataArray;
import com.soubu.goldensteward.module.server.BaseDataObject;
import com.soubu.goldensteward.module.server.BaseResp;
import com.soubu.goldensteward.module.server.UserServerParams;
import com.soubu.goldensteward.server.RetrofitRequest;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by lakers on 16/10/25.
 */

public class LoginActivity extends ActivityPresenter<LoginActivityDelegate> implements View.OnClickListener {
    private boolean mDisplayPwd;
    private UserServerParams mParams;


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
    protected void initData() {
        super.initData();
        String phone = GoldenStewardApplication.getContext().getPhone();
        mParams = new UserServerParams();
        if (!TextUtils.isEmpty(phone)) {
            viewDelegate.refreshPhone(phone);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
                if (viewDelegate.checkComplete(mParams)) {
                    RetrofitRequest.getInstance().login(mParams);
                }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void login(BaseEventBusResp resp) {
        BaseResp resp1 = (BaseResp) resp.getObject();
        int code = resp.getCode();
        if (code == EventBusConfig.LOGIN) {
            BaseDataObject data = (BaseDataObject) resp1.getResult();
            if (data.getData() instanceof UserServerParams) {
                GoldenStewardApplication.getContext().setToken(data.getToken());
                final UserServerParams params = (UserServerParams) data.getData();
                GoldenStewardApplication.getContext().setPhone(params.getPhone());
                int certification = Integer.valueOf(params.getCertification());
                int child_state = Integer.valueOf(params.getChild_status());
                Intent intent;
                if (certification == -1) {
                    intent = new Intent(this, StoreOwnerVerifyActivity.class);
                    startActivity(intent);
                } else if (certification == 0) {
                    intent = new Intent(this, StoreOwnerVerifyActivity.class);
                    if (child_state == -1 || child_state == 2) {
                        intent.putExtra(Constant.EXTRA_INDEX, 2);
                    } else {
                        intent.putExtra(Constant.EXTRA_INDEX, 3);
                    }
                    startActivity(intent);
                } else {
                    intent = new Intent(this, StoreOwnerVerifyActivity.class);
                    if (child_state == -1 || child_state == 2) {
                        intent.putExtra(Constant.EXTRA_INDEX, 2);
                        startActivity(intent);
                    } else if (child_state == 0) {
                        intent.putExtra(Constant.EXTRA_INDEX, 3);
                        startActivity(intent);
                    } else {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                GoldenStewardApplication.getContext().saveUserInfo(params);
                            }
                        }).start();
                        intent = new Intent(this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

//
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        GoldenStewardApplication.getContext().saveUserInfo(params);
//                    }
//                }).start();
//                Intent intent = new Intent(this, HomeActivity.class);
//                startActivity(intent);
//                finish();
            }
        }
    }

    @Override
    public boolean keyDownTwiceFinish() {
        return true;
    }
}
