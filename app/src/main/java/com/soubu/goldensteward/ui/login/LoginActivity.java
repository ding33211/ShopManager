package com.soubu.goldensteward.ui.login;

import android.content.Intent;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.base.BaseApplication;
import com.soubu.goldensteward.support.bean.Constant;
import com.soubu.goldensteward.support.bean.server.UserServerParams;
import com.soubu.goldensteward.support.constant.SpKey;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.support.utils.LogUtil;
import com.soubu.goldensteward.support.utils.SPUtil;
import com.soubu.goldensteward.support.web.core.BaseResponse;
import com.soubu.goldensteward.support.web.core.BaseSubscriber;
import com.soubu.goldensteward.ui.home.HomeActivity;
import com.soubu.goldensteward.ui.register.RegisterOrForgetPwdActivity;
import com.soubu.goldensteward.ui.register.StoreOwnerVerifyActivity;

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
        String phone = SPUtil.getValue(SpKey.USER_PHONE, "");
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
                    // TODO: 重构登录接口
                    BaseApplication.getWebModel()
                            .login(mParams)
                            .sendTo(new BaseSubscriber<BaseResponse<UserServerParams>>(this) {
                                @Override
                                public void onSuccess(BaseResponse<UserServerParams> response) {
                                    login(response);
                                }
                            });
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

    public void login(BaseResponse<UserServerParams> response) {
        BaseResponse<UserServerParams>.Entity<UserServerParams> result = response.getResult();

        SPUtil.putValue(SpKey.TOKEN, result.getToken());

        UserServerParams params = result.getData();

        SPUtil.putValue(SpKey.USER_PHONE, params.getPhone());

        int certification = Integer.valueOf(params.getCertification());
        int child_state = Integer.valueOf(params.getChild_status());
        Intent intent;

        LogUtil.print("certification=" + certification);
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
                        BaseApplication.getContext().saveUserInfo(params);
                    }
                }).start();
                intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }

    }

    @Override
    public boolean keyDownTwiceFinish() {
        return true;
    }
}
