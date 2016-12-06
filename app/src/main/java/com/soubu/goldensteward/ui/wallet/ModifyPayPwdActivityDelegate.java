package com.soubu.goldensteward.ui.wallet;

import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.mvp.view.AppDelegate;
import com.soubu.goldensteward.support.bean.server.ModifyPwdServerParams;
import com.soubu.goldensteward.support.utils.RegularUtil;
import com.soubu.goldensteward.support.utils.ShowWidgetUtil;

/**
 * Created by dingsigang on 16-10-20.
 */
public class ModifyPayPwdActivityDelegate extends AppDelegate {
    EditText mEtOldPwd;
    EditText mEtNewPwd;
    EditText mEtAgainNewPwd;
    //是否是修改登录密码
    boolean isModifyPwd = false;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_modify_pay_pwd;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mEtOldPwd = get(R.id.et_original_pwd);
        mEtNewPwd = get(R.id.et_new_pwd);
        mEtAgainNewPwd = get(R.id.et_new_pwd_again);
        mEtOldPwd.addTextChangedListener(new TextWatcher() {
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

        mEtNewPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    get(R.id.iv_clear_new_pwd).setVisibility(View.INVISIBLE);
                } else {
                    get(R.id.iv_clear_new_pwd).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void initPwdWidget() {
        isModifyPwd = true;
        mEtOldPwd.setHint(R.string.please_input_original_pwd);
        mEtOldPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mEtOldPwd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        mEtNewPwd.setHint(R.string.please_input_new_pwd);
        mEtNewPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mEtNewPwd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        get(R.id.ll_pwd_again).setVisibility(View.GONE);
        get(R.id.v_again_line).setVisibility(View.GONE);
//        mEtAgainNewPwd.setHint(R.string.please_input_new_pwd_again);
//        mEtAgainNewPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        mEtAgainNewPwd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
    }


    public boolean checkComplete(ModifyPwdServerParams params) {
        String oldPwd = mEtOldPwd.getText().toString();
        if (TextUtils.isEmpty(oldPwd)) {
            ShowWidgetUtil.showShort(R.string.please_input_original_pwd);
            return false;
        }
        String newPwd = mEtNewPwd.getText().toString();
        if (TextUtils.isEmpty(newPwd)) {
            ShowWidgetUtil.showShort(R.string.please_input_new_pwd);
            return false;
        }
        if (!isModifyPwd) {
            String againNewPwd = mEtAgainNewPwd.getText().toString();
            if (TextUtils.isEmpty(againNewPwd)) {
                ShowWidgetUtil.showShort(R.string.please_input_new_pwd_again);
                return false;
            }
            if (!TextUtils.equals(newPwd, againNewPwd)) {
                ShowWidgetUtil.showShort(R.string.pwd_not_equal);
                return false;
            }
        }
        if (!RegularUtil.isPassword(newPwd)) {
            ShowWidgetUtil.showShort(R.string.wrong_pwd);
            return false;
        }
        params.setOld_password(oldPwd);
        params.setNew_password(newPwd);
        return true;
    }


    @Override
    public boolean ifNeedEventBus() {
        return true;
    }
}
