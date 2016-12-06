package com.soubu.goldensteward.ui.setting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import com.soubu.goldensteward.support.base.GoldenStewardApplication;
import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.ui.login.LoginActivity;
import com.soubu.goldensteward.support.bean.Constant;
import com.soubu.goldensteward.support.utils.ActivityContainer;
import com.soubu.goldensteward.ui.wallet.ModifyPayPwdActivity;

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
        viewDelegate.setOnClickListener(this, R.id.ll_feedback, R.id.ll_modify_pwd, R.id.ll_modify_phone, R.id.ll_about, R.id.btn_logout);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.setting);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
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
            case R.id.btn_logout:
                new AlertDialog.Builder(this).setTitle(R.string.alert).setMessage(R.string.logout_alert_message).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GoldenStewardApplication.getContext().setToken("");
                        GoldenStewardApplication.getContext().clearUser();
                        ActivityContainer.getInstance().finishAllActivity();
                        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
