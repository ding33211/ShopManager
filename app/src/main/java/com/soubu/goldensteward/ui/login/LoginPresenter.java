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
import com.soubu.goldensteward.support.web.mvp.BasePresenter;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * 作者：余天然 on 2016/12/12 上午11:39
 */
public class LoginPresenter extends BasePresenter<LoginView> {

    private UserServerParams mParams;//用户信息参数

    @Override
    public void initData() {
        String phone = SPUtil.getValue(SpKey.USER_PHONE, "");
        mParams = new UserServerParams();
        if (!TextUtils.isEmpty(phone)) {
            getView().refreshPhone(phone);
        }
    }

    public void responseLogin(BaseResponse<UserServerParams> response) {
        BaseResponse<UserServerParams>.Entity<UserServerParams> result = response.getResult();

        SPUtil.putValue(SpKey.TOKEN, result.getToken());

        UserServerParams params = result.getData();

        SPUtil.putValue(SpKey.USER_PHONE, params.getPhone());

        getView().gotoNext(params);
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
                .map(params1 -> UserManager.saveUserInfo(params))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}
