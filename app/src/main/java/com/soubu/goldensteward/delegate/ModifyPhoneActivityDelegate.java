package com.soubu.goldensteward.delegate;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;
import com.soubu.goldensteward.module.server.UserServerParams;
import com.soubu.goldensteward.utils.ShowWidgetUtil;

import static com.baidu.location.h.j.p;


/**
 * Created by lakers on 16/10/31.
 */

public class ModifyPhoneActivityDelegate extends AppDelegate {

    EditText mEtPhone;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_find_pwd;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mEtPhone = get(R.id.et_phone);
        mEtPhone.setEnabled(false);
        get(R.id.ll_tab).setVisibility(View.GONE);
        get(R.id.ll_modify_phone_top).setVisibility(View.VISIBLE);
        ((Button) get(R.id.btn_confirm)).setText(R.string.next_step);
    }

    public void initPhone(String phone) {
        mEtPhone.setText(phone);
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }

    public boolean checkOldPhone(UserServerParams params) {
        EditText etCode = get(R.id.et_code);
        String code = etCode.getText().toString();
        if (TextUtils.isEmpty(code)) {
            ShowWidgetUtil.showShort(R.string.wrong_empty_code);
            return false;
        }
        params.setCode(code);
        return true;
    }

    public void initSecondStep() {
        mEtPhone.setEnabled(true);
        mEtPhone.setHint(R.string.please_input_new_phone);
        ((Button) get(R.id.btn_confirm)).setText(R.string.modify_phone);
        ((TextView) get(R.id.tv_verify)).setText(R.string.verify_new_phone);
    }
}
