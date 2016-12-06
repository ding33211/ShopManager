package com.soubu.goldensteward.ui.wallet;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.support.bean.BaseEventBusResp;
import com.soubu.goldensteward.support.bean.Constant;
import com.soubu.goldensteward.support.bean.EventBusConfig;
import com.soubu.goldensteward.support.bean.server.BaseResp;
import com.soubu.goldensteward.support.bean.server.ModifyPwdServerParams;
import com.soubu.goldensteward.support.server.RetrofitRequest;
import com.soubu.goldensteward.support.utils.ShowWidgetUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by dingsigang on 16-10-20.
 */
public class ModifyPayPwdActivity extends ActivityPresenter<ModifyPayPwdActivityDelegate> implements View.OnClickListener {
    private boolean mDisplayOldPwd;
    private boolean mDisplayNewPwd;

    public static final int TYPE_PWD = 0x00;
    public static final int TYPE_PAY_PWD = 0x01;

    private int mType;

    @Override
    protected Class<ModifyPayPwdActivityDelegate> getDelegateClass() {
        return ModifyPayPwdActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mType = getIntent().getIntExtra(Constant.EXTRA_TYPE, TYPE_PAY_PWD);
        if (mType == TYPE_PAY_PWD) {
            viewDelegate.setTitle(R.string.modify_pay_password);
        } else {
            viewDelegate.setTitle(R.string.modify_password);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        if (mType == TYPE_PWD) {
            viewDelegate.initPwdWidget();
        }
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.iv_clear, R.id.iv_transfer_pwd, R.id.btn_confirm, R.id.iv_clear_new_pwd, R.id.iv_transfer_new_pwd);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_clear:
                ((EditText) viewDelegate.get(R.id.et_original_pwd)).setText("");
                break;
            case R.id.iv_clear_new_pwd:
                ((EditText) viewDelegate.get(R.id.et_new_pwd)).setText("");
                break;
            case R.id.iv_transfer_pwd:
                if (!mDisplayOldPwd) {
                    ((EditText) viewDelegate.get(R.id.et_original_pwd)).setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ((ImageView) viewDelegate.get(R.id.iv_transfer_pwd)).setImageResource(R.drawable.password_show);
                    mDisplayOldPwd = true;
                } else {
                    ((EditText) viewDelegate.get(R.id.et_original_pwd)).setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ((ImageView) viewDelegate.get(R.id.iv_transfer_pwd)).setImageResource(R.drawable.password_hide);
                    mDisplayOldPwd = false;
                }
                break;
            case R.id.iv_transfer_new_pwd:
                if (!mDisplayNewPwd) {
                    ((EditText) viewDelegate.get(R.id.et_new_pwd)).setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ((ImageView) viewDelegate.get(R.id.iv_transfer_new_pwd)).setImageResource(R.drawable.password_show);
                    mDisplayNewPwd = true;
                } else {
                    ((EditText) viewDelegate.get(R.id.et_new_pwd)).setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ((ImageView) viewDelegate.get(R.id.iv_transfer_new_pwd)).setImageResource(R.drawable.password_hide);
                    mDisplayNewPwd = false;
                }
                break;
            case R.id.btn_confirm:
                ModifyPwdServerParams params = new ModifyPwdServerParams();
                if (viewDelegate.checkComplete(params)) {
                    if (mType == TYPE_PWD) {
                        RetrofitRequest.getInstance().changeLoginPwd(params);
                    } else {

                    }
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void modify(BaseEventBusResp resp) {
        BaseResp resp1 = (BaseResp) resp.getObject();
        int code = resp.getCode();
        if (code == EventBusConfig.CHANGE_LOGIN_PWD) {
            ShowWidgetUtil.showShort(resp1.getMsg());
            finish();
        }
    }
}
