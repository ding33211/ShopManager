package com.soubu.goldensteward.view.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.ModifyPhoneActivityDelegate;
import com.soubu.goldensteward.utils.ShowWidgetUtil;

/**
 * Created by lakers on 16/10/31.
 */

public class ModifyPhoneActivity extends ActivityPresenter<ModifyPhoneActivityDelegate> implements View.OnClickListener {
    boolean mStep2 = false;

    @Override
    protected Class<ModifyPhoneActivityDelegate> getDelegateClass() {
        return ModifyPhoneActivityDelegate.class;
    }


    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.modify_phone);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.tv_send_verify_code, R.id.btn_confirm);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_send_verify_code:
                ShowWidgetUtil.showVerifyCodeTimerStart((TextView) v);
                break;
            case R.id.btn_confirm:
                if (!mStep2) {
                    ((Button) v).setText(R.string.modify_phone);
                    ((TextView) viewDelegate.get(R.id.tv_verify)).setText(R.string.verify_new_phone);
                    mStep2 = true;
                } else {

                }
                break;

        }
    }
}
