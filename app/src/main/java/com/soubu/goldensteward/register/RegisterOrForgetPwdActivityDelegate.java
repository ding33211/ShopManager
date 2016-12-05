package com.soubu.goldensteward.register;

import android.graphics.Paint;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;
import com.soubu.goldensteward.module.server.UserServerParams;
import com.soubu.goldensteward.utils.RegularUtil;
import com.soubu.goldensteward.utils.ShowWidgetUtil;

/**
 * Created by lakers on 16/10/26.
 */

public class RegisterOrForgetPwdActivityDelegate extends AppDelegate {
    EditText mEtPhone;
    EditText mEtPwd;
    EditText mEtImageCode;
    View mCheck;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_register_or_forget_pwd;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        TextView tvProtocol = get(R.id.tv_protocol);
        tvProtocol.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mCheck = get(R.id.iv_login_check);
        mCheck.setSelected(true);
        mCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isSelected()) {
                    v.setSelected(false);
                    get(R.id.btn_next_step).setEnabled(false);
                } else {
                    v.setSelected(true);
                    get(R.id.btn_next_step).setEnabled(true);
                }
            }
        });
        mEtPhone = get(R.id.et_phone);
        mEtPwd = get(R.id.et_pwd);
        mEtImageCode = get(R.id.et_image_code);
        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    get(R.id.iv_clear).setVisibility(View.INVISIBLE);
                } else {
                    get(R.id.iv_clear).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEtPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    get(R.id.iv_clear_pwd).setVisibility(View.INVISIBLE);
                } else {
                    get(R.id.iv_clear_pwd).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public boolean ifNeedEventBus() {
        return true;
    }

    //    public void gotoReferStore(){
//        get(R.id.rl_register).setVisibility(View.GONE);
//        get(R.id.ll_refer_store).setVisibility(View.VISIBLE);
//        get(R.id.ll_register_desc).setVisibility(View.GONE);
//    }

    public String getImageCode(){
        return mEtImageCode.getText().toString();
    }

    public boolean checkComplete(UserServerParams params) {

        String phone = mEtPhone.getText().toString();
        if (!RegularUtil.isMobile(phone)) {
            ShowWidgetUtil.showShort(R.string.wrong_phone);
            return false;
        }
        String code = ((EditText) get(R.id.et_verification_code)).getText().toString();
        if (TextUtils.isEmpty(code)) {
            ShowWidgetUtil.showShort(R.string.wrong_empty_code);
            return false;
        }
        String pwd = mEtPwd.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            ShowWidgetUtil.showShort(R.string.empty_pwd);
            return false;
        }
        if (!RegularUtil.isPassword(pwd)) {
            ShowWidgetUtil.showShort(R.string.wrong_pwd);
            return false;
        }
        params.setPhone(phone);
        params.setCode(code);
        params.setPassword(pwd);
        return true;
    }
}
