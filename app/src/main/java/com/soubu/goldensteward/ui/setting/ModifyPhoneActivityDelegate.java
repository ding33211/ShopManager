package com.soubu.goldensteward.ui.setting;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.soubu.goldensteward.support.base.GoldenStewardApplication;
import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.mvp.view.AppDelegate;
import com.soubu.goldensteward.support.bean.server.UserServerParams;
import com.soubu.goldensteward.support.utils.ShowWidgetUtil;


/**
 * Created by lakers on 16/10/31.
 */

public class ModifyPhoneActivityDelegate extends AppDelegate {

    EditText mEtPhone;
    EditText mEtImageCode;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_find_pwd;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mEtPhone = get(R.id.et_phone);
        mEtImageCode = get(R.id.et_image_code);
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


    public boolean checkNewPhone(UserServerParams params) {
        String newPhone = mEtPhone.getText().toString();
        if (TextUtils.isEmpty(newPhone)) {
            ShowWidgetUtil.showShort(R.string.please_input_new_phone);
            return false;
        }
        if(TextUtils.equals(newPhone, GoldenStewardApplication.getContext().getPhone())){
            ShowWidgetUtil.showShort(R.string.same_to_old_phone);
            return false;
        }
        params.setPhone(newPhone);
        params.setImage_code(getImageCode());
        return true;
    }

    public String getImageCode(){
        return mEtImageCode.getText().toString();
    }

    public void initSecondStep() {
        mEtPhone.setEnabled(true);
        mEtPhone.setHint(R.string.please_input_new_phone);
        mEtPhone.setText("");
        ShowWidgetUtil.stopVerifyCodeTimer();
//        TextView tvSend = get(R.id.tv_send_verify_code);
//        tvSend.setEnabled(true);
//        tvSend.setTextColor(tvSend.getResources().getColor(R.color.colorPrimary));
//        tvSend.setBackgroundResource(R.drawable.bg_orange_stroke_corners);
//        tvSend.setText(R.string.get_verify_code);
        EditText etCode = get(R.id.et_code);
        etCode.setText("");
        ((Button) get(R.id.btn_confirm)).setText(R.string.modify_phone);
        ((TextView) get(R.id.tv_verify)).setText(R.string.verify_new_phone);
    }
}
