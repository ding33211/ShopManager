package com.soubu.goldensteward.delegate;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;
import com.soubu.goldensteward.module.server.RegisterServerParams;
import com.soubu.goldensteward.utils.RegularUtil;
import com.soubu.goldensteward.utils.ShowWidgetUtil;

/**
 * Created by lakers on 16/10/26.
 */

public class RegisterOrForgetPwdActivityDelegate extends AppDelegate {
    View mCheck;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_register_or_forget_pwd;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mCheck = get(R.id.iv_login_check);
        mCheck.setSelected(true);
        mCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.isSelected()){
                    v.setSelected(false);
                    get(R.id.btn_next_step).setEnabled(false);
                } else {
                    v.setSelected(true);
                    get(R.id.btn_next_step).setEnabled(true);
                }
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

    public boolean checkComplete(RegisterServerParams params) {

        String phone = ((EditText) get(R.id.et_phone)).getText().toString();
        if (!RegularUtil.isMobile(phone)) {
            ShowWidgetUtil.showShort(R.string.wrong_phone);
            return false;
        }
        String code = ((EditText) get(R.id.et_verification_code)).getText().toString();
        if (TextUtils.isEmpty(code)) {
            ShowWidgetUtil.showShort(R.string.wrong_empty_code);
            return false;
        }
        String pwd = ((EditText) get(R.id.et_pwd)).getText().toString();
        if(TextUtils.isEmpty(pwd)){
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
