package com.soubu.goldensteward.setting;

import android.content.Intent;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.setting.FindPwdActivity;
import com.soubu.goldensteward.setting.SecurityCenterActivityDelegate;
import com.soubu.goldensteward.wallet.ModifyPayPwdActivity;

/**
 * Created by dingsigang on 16-10-20.
 */
public class SecurityCenterActivity extends ActivityPresenter<SecurityCenterActivityDelegate> implements View.OnClickListener{
    @Override
    protected Class<SecurityCenterActivityDelegate> getDelegateClass() {
        return SecurityCenterActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.security_center);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.ll_modify_pwd, R.id.ll_find_pwd);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.ll_modify_pwd:
                intent = new Intent(this, ModifyPayPwdActivity.class);
                break;
            case R.id.ll_find_pwd:
                intent = new Intent(this, FindPwdActivity.class);
                break;
        }
        if(null != intent){
            startActivity(intent);
        }
    }
}
