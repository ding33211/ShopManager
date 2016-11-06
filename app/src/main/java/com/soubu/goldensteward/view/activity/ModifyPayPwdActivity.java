package com.soubu.goldensteward.view.activity;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.ModifyPayPwdActivityDelegate;
import com.soubu.goldensteward.module.Constant;
import com.soubu.goldensteward.module.server.BaseResp;
import com.soubu.goldensteward.module.server.ModifyPwdServerParams;
import com.soubu.goldensteward.server.RetrofitRequest;
import com.soubu.goldensteward.utils.ShowWidgetUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.icu.text.UnicodeSet.CASE;

/**
 * Created by dingsigang on 16-10-20.
 */
public class ModifyPayPwdActivity extends ActivityPresenter<ModifyPayPwdActivityDelegate> implements View.OnClickListener {
    private boolean mDisplayPwd;

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
        viewDelegate.setOnClickListener(this, R.id.iv_clear, R.id.iv_transfer_pwd, R.id.btn_confirm);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            case R.id.btn_confirm:
                ModifyPwdServerParams params = new ModifyPwdServerParams();
                if(viewDelegate.checkComplete(params)){
                    if(mType == TYPE_PWD){
                        RetrofitRequest.getInstance().modifyLoginPwd(params);
                    } else {

                    }
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void modify(BaseResp resp){
        if(resp.getResult() instanceof ModifyPwdServerParams){
            ShowWidgetUtil.showShort(resp.getMsg());
            finish();
        }
    }
}
