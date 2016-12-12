package com.soubu.goldensteward.ui.register;

import android.content.Intent;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.google.gson.Gson;
import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.base.BaseApplication;
import com.soubu.goldensteward.support.bean.Constant;
import com.soubu.goldensteward.support.bean.server.UserServerParams;
import com.soubu.goldensteward.support.constant.ApiConfig;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.support.utils.GlideUtils;
import com.soubu.goldensteward.support.utils.ShowWidgetUtil;
import com.soubu.goldensteward.support.web.core.BaseHeader;
import com.soubu.goldensteward.support.web.core.BaseResponse;
import com.soubu.goldensteward.support.web.core.BaseSubscriber;

/**
 * Created by lakers on 16/10/26.
 */

public class RegisterOrForgetPwdActivity extends ActivityPresenter<RegisterOrForgetPwdActivityDelegate> implements View.OnClickListener {
    public static final int TYPE_REGISTER = 0x00;
    public static final int TYPE_FORGET_PWD = 0x01;

    private int mType;
    private UserServerParams mParams;
    private View mVSendCode;
    private boolean mDisplayPwd;

    @Override
    protected Class<RegisterOrForgetPwdActivityDelegate> getDelegateClass() {
        return RegisterOrForgetPwdActivityDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.tv_send_verify_code, R.id.btn_next_step,
                R.id.iv_clear, R.id.iv_clear_pwd, R.id.iv_transfer_pwd, R.id.tv_protocol, R.id.iv_image_code);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mType = getIntent().getIntExtra(Constant.EXTRA_TYPE, 0);
        if (mType == TYPE_REGISTER) {
            viewDelegate.setTitle(R.string.register);
        } else {
            viewDelegate.setTitle(R.string.forget_password);
            ((Button) viewDelegate.get(R.id.btn_next_step)).setText(R.string.find_pwd);
            viewDelegate.get(R.id.ll_register_desc).setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        mParams = new UserServerParams();
        loadImageCode();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_clear:
                ((EditText) viewDelegate.get(R.id.et_phone)).setText("");
                break;
            case R.id.iv_clear_pwd:
                ((EditText) viewDelegate.get(R.id.et_pwd)).setText("");
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
            case R.id.tv_send_verify_code:
                String phone = ((EditText) viewDelegate.get(R.id.et_phone)).getText().toString();
                mVSendCode = v;
                sendVerifyCode(phone);
                break;
            case R.id.btn_next_step:
                if (viewDelegate.checkComplete(mParams)) {
                    if (mType == TYPE_REGISTER) {
                        verifyPhoneCode();
                    } else {
                        forgetPwd();
                    }
                }
                break;
            case R.id.tv_protocol:
                Intent intent = new Intent(this, ProtocolActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_image_code:
                loadImageCode();
                break;
        }
    }


    private void loadImageCode() {
        BaseHeader entity = new BaseHeader();
        String head = new Gson().toJson(entity);
        GlideUtils.loadImage(this, (ImageView) viewDelegate.get(R.id.iv_image_code), new GlideUrl(ApiConfig.API_HOST + "Other/get_verify_image?timestamp=" + System.currentTimeMillis()
                , new LazyHeaders.Builder().addHeader("SHOP_MANAGER_AGENT", head).build()), 0, R.drawable.common_btn_imagecode_fail);
    }

    private void sendVerifyCode(String phone) {
        mParams.setPhone(phone);
        if (mType == TYPE_REGISTER) {
            mParams.setType("1");
        } else {
            mParams.setType("2");
        }
        mParams.setImage_code(viewDelegate.getImageCode());

        getVerifyCode();
    }

    // Todo：获取验证码
    private void getVerifyCode() {
        BaseApplication.getWebModel()
                .getVerifyCode(mParams)
                .sendTo(new BaseSubscriber<BaseResponse<Object>>(this) {
                    @Override
                    public void onSuccess(BaseResponse<Object> response) {
                        responseGetVerifyCode(response);
                    }
                });
    }

    // Todo: 验证短信验证码
    private void verifyPhoneCode() {
        BaseApplication.getWebModel()
                .checkCode(mParams)
                .sendTo(new BaseSubscriber<BaseResponse<Object>>(this) {
                    @Override
                    public void onSuccess(BaseResponse<Object> response) {
                        responseCheckCode(response);
                    }
                });
    }

    // Todo: 忘记密码
    private void forgetPwd() {
        BaseApplication.getWebModel()
                .forgetPassword(mParams)
                .sendTo(new BaseSubscriber<BaseResponse<Object>>(this) {
                    @Override
                    public void onSuccess(BaseResponse<Object> response) {
                        responseForgetPwd(response);
                    }
                });
    }

    private void responseGetVerifyCode(BaseResponse<Object> response) {
        String msg = response.getMsg();
        if (TextUtils.equals(msg, "发送成功")) {
            mVSendCode.setEnabled(false);
            ShowWidgetUtil.showVerifyCodeTimerStart((TextView) viewDelegate.get(R.id.tv_send_verify_code));
            ShowWidgetUtil.showShort(msg);
        }
    }

    private void responseCheckCode(BaseResponse<Object> response) {
        String msg = response.getMsg();
        if (TextUtils.equals(msg, "验证成功")) {
            Intent intent = new Intent(this, RegisterSupplierActivity.class);
            intent.putExtra(Constant.EXTRA_PARAMS, mParams);
            startActivity(intent);
        }
    }

    private void responseForgetPwd(BaseResponse<Object> response) {
        ShowWidgetUtil.showShort(R.string.modify_success);
        finish();
    }


}
