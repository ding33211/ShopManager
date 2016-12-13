package com.soubu.goldensteward.ui.login;

import android.text.TextUtils;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.base.BaseApplication;
import com.soubu.goldensteward.support.bean.server.UserServerParams;
import com.soubu.goldensteward.support.constant.SpKey;
import com.soubu.goldensteward.support.helper.UserManager;
import com.soubu.goldensteward.support.utils.SPUtil;
import com.soubu.goldensteward.support.utils.ShowWidgetUtil;
import com.soubu.goldensteward.support.web.core.BaseResponse;
import com.soubu.goldensteward.support.web.core.BaseSubscriber;
import com.soubu.goldensteward.support.web.core.BaseTransformer;
import com.soubu.goldensteward.support.web.mvp.BasePresenter;

import rx.Observable;

/**
 * 作者：余天然 on 2016/12/12 上午11:39
 */
public class LoginPresenter extends BasePresenter<LoginView> {

    private UserServerParams mParams = new UserServerParams();//用户信息参数

    @Override
    public void initData() {
        String phone = SPUtil.getValue(SpKey.USER_PHONE, "");
        if (!TextUtils.isEmpty(phone)) {
            getView().refreshPhone(phone);
        }
    }

    public void responseLogin(BaseResponse<UserServerParams> response) {
        BaseResponse<UserServerParams>.Entity<UserServerParams> result = response.getResult();

        SPUtil.putValue(SpKey.TOKEN, result.getToken());

        UserServerParams params = result.getData();

        SPUtil.putValue(SpKey.USER_PHONE, params.getPhone());

        gotoNext(params);
    }

    private void gotoNext(UserServerParams params) {
        int certification = Integer.valueOf(params.getCertification());
        int child_state = Integer.valueOf(params.getChild_status());
        if (certification == -1) {
            getView().gotoStoreOwnerVerify(0);
        } else if (certification == 0) {
            if (child_state == -1 || child_state == 2) {
                getView().gotoStoreOwnerVerify(2);
            } else {
                getView().gotoStoreOwnerVerify(3);
            }
        } else {
            if (child_state == -1 || child_state == 2) {
                getView().gotoStoreOwnerVerify(2);
            } else if (child_state == 0) {
                getView().gotoStoreOwnerVerify(3);
            } else {
                save(params);
                getView().gotoHome();
            }
        }
    }

    public boolean checkComplete(String phone, String password) {
        if (TextUtils.isEmpty(phone)) {
            ShowWidgetUtil.showShort(R.string.please_input_your_phone_number);
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            ShowWidgetUtil.showShort(R.string.please_input_password);
            return false;
        }
        mParams.setPhone(phone);
        mParams.setPassword(password);
        return true;
    }

    public void login(String phone, String password) {
        if (checkComplete(phone, password)) {
            BaseApplication.getWebModel()
                    .login(mParams)
                    .sendTo(new BaseSubscriber<BaseResponse<UserServerParams>>(getView()) {
                        @Override
                        public void onSuccess(BaseResponse<UserServerParams> response) {
                            responseLogin(response);
                        }
                    });
        }
    }

    public void save(UserServerParams params) {
        Observable.just(params)
                .map(var -> UserManager.saveUserInfo(var))
                .compose(new BaseTransformer<>())
                .subscribe();
    }
}
