package com.soubu.goldensteward.delegate;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;
import com.soubu.goldensteward.module.server.UserServerParams;
import com.soubu.goldensteward.utils.ShowWidgetUtil;

/**
 * Created by lakers on 16/10/25.
 */

public class LoginActivityDelegate extends AppDelegate {
    EditText mEtPhone;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mEtPhone = get(R.id.et_your_phone);
        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s)){
                    get(R.id.iv_clear).setVisibility(View.INVISIBLE);
                } else {
                    get(R.id.iv_clear).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean ifNeedHideToolBar() {
        return true;
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }

    public boolean checkComplete(UserServerParams params){
        String phone = mEtPhone.getText().toString();
        if(TextUtils.isEmpty(phone)){
            ShowWidgetUtil.showShort(R.string.please_input_your_phone_number);
            return false;
        }
        String password = ((EditText) get(R.id.et_pwd)).getText().toString();
        if(TextUtils.isEmpty(password)){
            ShowWidgetUtil.showShort(R.string.please_input_password);
            return false;
        }
        params.setPhone(phone);
        params.setPassword(password);
        return true;
    }
}
