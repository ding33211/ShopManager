package com.soubu.goldensteward;

import android.app.Application;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.soubu.goldensteward.module.AppConfig;
import com.soubu.goldensteward.module.Constant;
import com.soubu.goldensteward.module.OssConst;
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
    //此处的账户名就是手机号
    private static String mName;
    public static OSS oss;

    public static final String OSS_BUCKET_HOST_ID = OssConst.END_POINT;
    private static final String accessKey = OssConst.ACCESSKEYID;
    private static final String secretKey = OssConst.ACCESSKEYSECRET;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = (GoldenStewardApplication) getApplicationContext();
        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
        ShowWidgetUtil.register(this);
        AppConfig.init(instance);
        initOSSConfig();
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


    public String getName() {
        if (TextUtils.isEmpty(mName)) {
            SharedPreferences sp = AppUtil.getDefaultSharedPreference(instance);
            mName = sp.getString(Constant.SP_KEY_USER_NAME, null);
        }
        return mName;
    }

    public void setName(String name) {
        mName = name;
        SharedPreferences sp = AppUtil.getDefaultSharedPreference(instance);
        sp.edit().putString(Constant.SP_KEY_USER_NAME, name).commit();
    }


    private void initOSSConfig() {
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKey, secretKey);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        if (BuildConfig.DEBUG) {
            OSSLog.enableLog();
        }
        oss = new OSSClient(getApplicationContext(), OSS_BUCKET_HOST_ID, credentialProvider, conf);
    }
}
