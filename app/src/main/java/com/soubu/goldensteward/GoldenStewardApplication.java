package com.soubu.goldensteward;

import android.app.Application;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.soubu.goldensteward.module.Constant;
import com.soubu.goldensteward.sdk.eventbus.MyEventBusIndex;
import com.soubu.goldensteward.utils.AppUtil;
import com.soubu.goldensteward.utils.ShowWidgetUtil;

import org.greenrobot.eventbus.EventBus;

import static okhttp3.internal.Internal.instance;

/**
 * Created by dingsigang on 16-10-18.
 */
public class GoldenStewardApplication extends Application {
    private static GoldenStewardApplication instance;

    //token以及uid做成全局参数
    private static String mToken;
    private static String mUid;
    private static String mName;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = (GoldenStewardApplication) getApplicationContext();
        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
        ShowWidgetUtil.register(this);
    }

    // 获取ApplicationContext
    public static GoldenStewardApplication getContext() {
        return instance;
    }

    public String getToken() {
        if (TextUtils.isEmpty(mToken)) {
            SharedPreferences sp = AppUtil.getDefaultSharedPreference(instance);
            mToken = sp.getString(Constant.SP_KEY_TOKEN, "");
        }
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
        SharedPreferences sp = AppUtil.getDefaultSharedPreference(instance);
        sp.edit().putString(Constant.SP_KEY_TOKEN, token).commit();
    }

    public String getUid() {
        if (TextUtils.isEmpty(mUid)) {
            SharedPreferences sp = AppUtil.getDefaultSharedPreference(instance);
            mUid = sp.getString(Constant.SP_KEY_USER_ID, null);
        }
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
        SharedPreferences sp = AppUtil.getDefaultSharedPreference(instance);
        sp.edit().putString(Constant.SP_KEY_USER_ID, uid).commit();
    }
}
