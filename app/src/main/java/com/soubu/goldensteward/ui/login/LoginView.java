package com.soubu.goldensteward.ui.login;

import com.soubu.goldensteward.support.web.mvp.BaseView;

/**
 * 作者：余天然 on 2016/12/12 下午12:50
 */
public interface LoginView extends BaseView {

    void refreshPhone(String phone);

    void gotoStoreOwnerVerify(int index);

    void gotoHome();
}
