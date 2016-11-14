package com.soubu.goldensteward.view.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.RegisterOrForgetPwdActivityDelegate;
import com.soubu.goldensteward.module.Constant;
import com.soubu.goldensteward.module.server.BaseResp;
import com.soubu.goldensteward.module.server.UserServerParams;
import com.soubu.goldensteward.server.RetrofitRequest;
import com.soubu.goldensteward.utils.RegularUtil;
import com.soubu.goldensteward.utils.ShowWidgetUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        viewDelegate.setOnClickListener(this, R.id.tv_send_verify_code, R.id.btn_next_step, R.id.iv_clear, R.id.iv_clear_pwd, R.id.iv_transfer_pwd);
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
        }
    }

    @Override
    protected void initData() {
        super.initData();
        mParams = new UserServerParams();
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
                if (RegularUtil.isMobile(phone)) {
                    mVSendCode = v;
                    sendVerifyCode(phone);
                } else {
                    ShowWidgetUtil.showShort(R.string.wrong_phone);
                }
                break;
            case R.id.btn_next_step:
                if (viewDelegate.checkComplete(mParams)) {
                    if (mType == TYPE_REGISTER) {
                        verifyPhoneCode();
                    } else {
                        RetrofitRequest.getInstance().forgetPassword(mParams);
                    }
                }


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
                break;

        }
    }

    private void sendVerifyCode(String phone) {
        mParams.setPhone(phone);
        RetrofitRequest.getInstance().getVerifyCode(mParams);
    }

    private void verifyPhoneCode() {
        RetrofitRequest.getInstance().checkCode(mParams);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(BaseResp resp) {
        if (resp.getResult() instanceof UserServerParams) {
            String msg = resp.getMsg();
            if (TextUtils.equals(msg, "验证成功")) {
                Intent intent = new Intent(this, RegisterSupplierActivity.class);
                intent.putExtra(Constant.EXTRA_PARAMS, mParams);
                startActivity(intent);
            } else {
                if (TextUtils.equals(msg, "发送成功")) {
                    mVSendCode.setEnabled(false);
                    ShowWidgetUtil.showVerifyCodeTimerStart((TextView) viewDelegate.get(R.id.tv_send_verify_code));
                    ShowWidgetUtil.showShort(msg);
                }
            }
        }
    }

}
