package com.soubu.goldensteward.ui.login;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.bean.Constant;
import com.soubu.goldensteward.support.bean.server.UserServerParams;
import com.soubu.goldensteward.support.web.mvp.BaseMvpActivity;
import com.soubu.goldensteward.ui.home.HomeActivity;
import com.soubu.goldensteward.ui.register.RegisterOrForgetPwdActivity;
import com.soubu.goldensteward.ui.register.StoreOwnerVerifyActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lakers on 16/10/25.
 */
public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginView {
    @BindView(R.id.et_your_phone)
    EditText etYourPhone;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.iv_transfer_pwd)
    ImageView ivTransferPwd;

    private boolean mDisplayPwd;//是否显示密码

    @Override
    protected int createLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void initWidget() {
        etYourPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    ivClear.setVisibility(View.INVISIBLE);
                } else {
                    ivClear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.iv_clear, R.id.iv_transfer_pwd, R.id.btn_login, R.id.tv_register, R.id.tv_forget_pwd})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_clear:
                etYourPhone.setText("");
                break;
            case R.id.iv_transfer_pwd:
                switchDisplayPwd();
                break;
            case R.id.btn_login:
                String phone = etYourPhone.getText().toString();
                String password = etPwd.getText().toString();
                getPresenter().login(phone, password);
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

    private void switchDisplayPwd() {
        if (!mDisplayPwd) {
            etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            ivTransferPwd.setImageResource(R.drawable.password_show);
            mDisplayPwd = true;
        } else {
            etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            ivTransferPwd.setImageResource(R.drawable.password_hide);
            mDisplayPwd = false;
        }
    }

    public void refreshPhone(String phone) {
        etYourPhone.setText(phone);
        etYourPhone.clearFocus();
        etPwd.requestFocus();
    }

    @Override
    public void gotoNext(UserServerParams params) {
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
                getPresenter().save(params);
                intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public boolean ifNeedHideToolBar() {
        return true;
    }

    @Override
    public boolean keyDownTwiceFinish() {
        return true;
    }

}
