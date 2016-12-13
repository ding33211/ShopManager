package com.soubu.goldensteward;

import android.content.Context;

import com.soubu.goldensteward.support.constant.BaseConfig;
import com.soubu.goldensteward.support.constant.SpKey;
import com.soubu.goldensteward.support.utils.LogUtil;
import com.soubu.goldensteward.support.utils.SPUtil;
import com.soubu.goldensteward.support.utils.ShowWidgetUtil;
import com.soubu.goldensteward.ui.login.LoginPresenter;
import com.soubu.goldensteward.ui.login.LoginView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowLog;
import org.robolectric.shadows.ShadowToast;

import static com.google.common.truth.Truth.assertThat;

/**
 * 作者：余天然 on 2016/12/12 下午8:23
 */
@RunWith(RobolectricTestRunner.class)
public class LoginPresenterTest {

    LoginView view;
    LoginPresenter presenter;
    Context context;

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
        BaseConfig.IS_TEST = true;
        context = RuntimeEnvironment.application;
        ShowWidgetUtil.register(context);
        SPUtil.init(context);

        view = Mockito.mock(LoginView.class);
        presenter = new LoginPresenter();
        presenter.attachView(view);

        LogUtil.print("");
    }

    @Test
    public void loginPhoneIsEmpty() {
        String phone = "";
        String password = "123456";
        presenter.login(phone, password);

        String toastMsg = ShadowToast.getTextOfLatestToast();
//        assertThat(toastMsg).isEqualTo("请输入您的手机号");
        assertThat(toastMsg).isEqualTo(context.getResources().getString(R.string.please_input_your_phone_number));
    }

    @Test
    public void loginPasswordIsEmpty() {
        String phone = "13120931234";
        String password = "";
        presenter.login(phone, password);

        String toastMsg = ShadowToast.getTextOfLatestToast();
        assertThat(toastMsg).isEqualTo("请输入密码");
    }

    @Test
    public void loginSuccess() throws InterruptedException {
        String phone = "15221234567";
        String password = "s111111";
        presenter.login(phone, password);

        String value = SPUtil.getValue(SpKey.USER_PHONE, "");
        assertThat(value).isEqualTo("15221234567");
    }

    @Test
    public void loginFailure() {
        String phone = "15221618022";
        String password = "s111222";
        presenter.login(phone, password);

        String toastMsg = ShadowToast.getTextOfLatestToast();
        assertThat(toastMsg).isEqualTo("账号不存在");
    }
}
