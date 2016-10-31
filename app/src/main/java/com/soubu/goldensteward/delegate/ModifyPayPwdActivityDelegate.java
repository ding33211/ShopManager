package com.soubu.goldensteward.delegate;

import android.widget.EditText;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;

/**
 * Created by dingsigang on 16-10-20.
 */
public class ModifyPayPwdActivityDelegate extends AppDelegate {
    EditText etOldPwd;
    EditText etNewPwd;
    EditText etAgainNewPwd;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_modify_pay_pwd;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        etOldPwd = get(R.id.et_original_pwd);
        etNewPwd = get(R.id.et_new_pwd);
        etAgainNewPwd = get(R.id.et_new_pwd_again);
    }

    public void initPwdWidget() {
        etOldPwd.setHint(R.string.please_input_original_pwd);
        etNewPwd.setHint(R.string.please_input_new_pwd);
        etAgainNewPwd.setHint(R.string.please_input_new_pwd_again);
    }
}
