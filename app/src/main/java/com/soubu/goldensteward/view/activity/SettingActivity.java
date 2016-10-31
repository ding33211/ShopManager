package com.soubu.goldensteward.view.activity;

import android.content.Intent;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.SettingActivityDelegate;
import com.soubu.goldensteward.module.Constant;

/**
 * Created by lakers on 16/10/31.
 */

public class SettingActivity extends ActivityPresenter<SettingActivityDelegate> implements View.OnClickListener {
    @Override
    protected Class<SettingActivityDelegate> getDelegateClass() {
        return SettingActivityDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.ll_feedback, R.id.ll_modify_pwd, R.id.ll_modify_phone, R.id.ll_about, R.id.ll_share, R.id.btn_logout);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.setting);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.ll_feedback:
                intent = new Intent(this, FeedBackActivity.class);
                break;
            case R.id.ll_modify_pwd:
                intent = new Intent(this, ModifyPayPwdActivity.class);
                intent.putExtra(Constant.EXTRA_TYPE, ModifyPayPwdActivity.TYPE_PWD);
                break;
            case R.id.ll_modify_phone:
                intent = new Intent(this, ModifyPhoneActivity.class);
                break;
            case R.id.ll_about:
                intent = new Intent(this, AboutActivity.class);
                break;
            case R.id.ll_share:
                break;
            case R.id.btn_logout:
                break;
        }
        if(intent != null){
            startActivity(intent);
        }
    }
}
