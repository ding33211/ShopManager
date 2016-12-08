package com.soubu.goldensteward.ui.setting;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.google.gson.Gson;
import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.bean.BaseEventBusResp;
import com.soubu.goldensteward.support.bean.EventBusConfig;
import com.soubu.goldensteward.support.bean.server.BaseResp;
import com.soubu.goldensteward.support.bean.server.UserServerParams;
import com.soubu.goldensteward.support.constant.ApiConfig;
import com.soubu.goldensteward.support.constant.SpKey;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.support.net.RetrofitRequest;
import com.soubu.goldensteward.support.utils.GlideUtils;
import com.soubu.goldensteward.support.utils.SPUtil;
import com.soubu.goldensteward.support.utils.ShowWidgetUtil;
import com.soubu.goldensteward.support.web.core.BaseHeader;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        String phone = SPUtil.getValue(SpKey.USER_PHONE, "");
        mParams.setPhone(phone);
        viewDelegate.initPhone(phone.substring(0, 3) + "****" + phone.substring(7));
        loadImageCode();
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.tv_send_verify_code, R.id.btn_confirm, R.id.iv_image_code);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_send_verify_code:
                if (!mStep2) {
                    RetrofitRequest.getInstance().getOldPhoneVerifyCode(viewDelegate.getImageCode());
                } else {
                    if (viewDelegate.checkNewPhone(mParams)) {
                        mParams.setType("3");
                        RetrofitRequest.getInstance().getVerifyCode(mParams);
                    }
                }
                break;
            case R.id.btn_confirm:
                if (!mStep2) {
                    if (viewDelegate.checkOldPhone(mParams)) {
                        RetrofitRequest.getInstance().checkOldPhone(mParams);
                    }
                } else {
                    if (viewDelegate.checkOldPhone(mParams)) {
                        RetrofitRequest.getInstance().changePhone(mParams);
                    }
                }
                break;
            case R.id.iv_image_code:
                loadImageCode();
                break;

        }
    }

    private void loadImageCode() {
        BaseHeader entity = new BaseHeader();
        String head = new Gson().toJson(entity);
        GlideUtils.loadImage(this, (ImageView) viewDelegate.get(R.id.iv_image_code), new GlideUrl(ApiConfig.API_HOST + "Other/get_verify_image?timestamp=" + System.currentTimeMillis()
                , new LazyHeaders.Builder().addHeader("SHOP_MANAGER_AGENT", head).build()), 0, R.drawable.common_btn_imagecode_fail);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void serverSuccess(BaseEventBusResp resp) {
        BaseResp resp1 = (BaseResp) resp.getObject();
        int code = resp.getCode();
        switch (code) {
            case EventBusConfig.CHECK_OLD_PHONE:
                if (TextUtils.equals("验证成功", resp1.msg)) {
                    mStep2 = true;
                    viewDelegate.initSecondStep();
                    loadImageCode();
                }
                break;
            case EventBusConfig.GET_OLD_PHONE_VERIFY_CODE:
            case EventBusConfig.GET_VERIFY_CODE:
                if (TextUtils.equals("发送成功", resp1.msg)) {
                    ShowWidgetUtil.showShort(resp1.msg);
                    ShowWidgetUtil.showVerifyCodeTimerStart((TextView) viewDelegate.get(R.id.tv_send_verify_code));
                }
                break;
            case EventBusConfig.CHANGE_PHONE:
                SPUtil.putValue(SpKey.USER_PHONE, mParams.getPhone());
                ShowWidgetUtil.showShort(R.string.modify_success);
                finish();
                break;
        }
    }
}
