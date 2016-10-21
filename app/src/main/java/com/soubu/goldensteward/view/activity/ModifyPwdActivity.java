package com.soubu.goldensteward.view.activity;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.ModifyPwdActivityDelegate;

/**
 * Created by dingsigang on 16-10-20.
 */
public class ModifyPwdActivity extends ActivityPresenter<ModifyPwdActivityDelegate> implements View.OnClickListener{
    private boolean mDisplayPwd;

    @Override
    protected Class<ModifyPwdActivityDelegate> getDelegateClass() {
        return ModifyPwdActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.modify_pay_password);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.iv_clear, R.id.iv_transfer_pwd);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_clear:
                ((EditText) viewDelegate.get(R.id.et_original_pwd)).setText("");
                break;
            case R.id.iv_transfer_pwd:
                if (!mDisplayPwd) {
                    ((EditText) viewDelegate.get(R.id.et_original_pwd)).setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ((ImageView) viewDelegate.get(R.id.iv_transfer_pwd)).setImageResource(R.drawable.password_show);
                    mDisplayPwd = true;
                } else {
                    ((EditText) viewDelegate.get(R.id.et_original_pwd)).setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ((ImageView) viewDelegate.get(R.id.iv_transfer_pwd)).setImageResource(R.drawable.password_hide);
                    mDisplayPwd = false;
                }
                break;
        }
    }
}
