package com.soubu.goldensteward.view.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.soubu.goldensteward.GoldenStewardApplication;
import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.ModifyPhoneActivityDelegate;
import com.soubu.goldensteward.module.server.BaseResp;
import com.soubu.goldensteward.module.server.UserServerParams;
import com.soubu.goldensteward.module.server.WalletHomeInfoServerParams;
import com.soubu.goldensteward.server.RetrofitRequest;
import com.soubu.goldensteward.utils.ShowWidgetUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.baidu.location.h.j.v;

/**
 * Created by lakers on 16/10/31.
 */

public class ModifyPhoneActivity extends ActivityPresenter<ModifyPhoneActivityDelegate> implements View.OnClickListener {
    boolean mStep2 = false;
    UserServerParams mParams;

    @Override
    protected Class<ModifyPhoneActivityDelegate> getDelegateClass() {
        return ModifyPhoneActivityDelegate.class;
    }


    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.modify_phone);
        mParams = new UserServerParams();
    }

    @Override
    protected void initData() {
        super.initData();
        String phone = GoldenStewardApplication.getContext().getPhone();
        mParams.setPhone(phone);
        viewDelegate.initPhone(phone.substring(0, 3) + "****" + phone.substring(7));
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
                RetrofitRequest.getInstance().getVerifyCode(mParams);
                break;
            case R.id.btn_confirm:
                if (!mStep2) {
                    if (viewDelegate.checkOldPhone(mParams)) {
                        RetrofitRequest.getInstance().checkOldPhone(mParams);
                    }
                } else {

                }
                break;

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void serverSuccess(BaseResp resp) {
        if (resp.getResult() instanceof UserServerParams) {
            if (TextUtils.equals("发送成功", resp.msg)) {
                ShowWidgetUtil.showShort(resp.msg);
                ShowWidgetUtil.showVerifyCodeTimerStart((TextView) viewDelegate.get(R.id.tv_send_verify_code));
            }
        }
        if (resp.getResult() instanceof WalletHomeInfoServerParams) {
            if (TextUtils.equals("验证成功", resp.msg)) {



                mStep2 = true;
            }
        }
    }
}
