package com.soubu.goldensteward;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.bugtags.library.Bugtags;
import com.soubu.goldensteward.base.greendao.DBHelper;
import com.soubu.goldensteward.base.greendao.User;
import com.soubu.goldensteward.base.greendao.UserDao;
import com.soubu.goldensteward.module.AppConfig;
import com.soubu.goldensteward.module.Constant;
import com.soubu.goldensteward.module.OssConst;
import com.soubu.goldensteward.module.server.UserServerParams;
import com.soubu.goldensteward.sdk.eventbus.MyEventBusIndex;
import com.soubu.goldensteward.utils.AppUtil;
import com.soubu.goldensteward.utils.ChannelUtil;
import com.soubu.goldensteward.utils.CrashHandler;
import com.soubu.goldensteward.utils.ShowWidgetUtil;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by dingsigang on 16-10-18.
 */
public class GoldenStewardApplication extends Application implements Application.ActivityLifecycleCallbacks {
    private static GoldenStewardApplication sInstance;
    //当前上下文,用以显式当前dialog
    private Context sNowContext;

    //token以及uid做成全局参数
    private static String mToken;
    private static String mUid;
    //此处的账户名就是手机号
    private static String mPhone;
    public static OSS oss;
    private UserDao dao;
    private User user;
    public static final String OSS_BUCKET_HOST_ID = OssConst.END_POINT;
    private static final String accessKey = OssConst.ACCESSKEYID;
    private static final String secretKey = OssConst.ACCESSKEYSECRET;

    @Override
    public void onCreate() {
        super.onCreate();
        this.registerActivityLifecycleCallbacks(this);//注册
        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this, "53ad0de956240b97b80eac9d", ChannelUtil.getChannel(this)));
        sInstance = (GoldenStewardApplication) getApplicationContext();
        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
        ShowWidgetUtil.register(this);
        AppConfig.init(sInstance);
        initOSSConfig();
        //只在非debug模式下打开bugtags
        if(!BuildConfig.DEBUG){
            if (BuildConfig.IS_PRODUCT_ENV) {
                Bugtags.start("a4aa632f49691a7caa3a1c49f038dc0d", this, Bugtags.BTGInvocationEventNone);
            } else {
                Bugtags.start("4c9c0ecb1faf11a9e160449041a0254a", this, Bugtags.BTGInvocationEventBubble);
            }
        }

        if (BuildConfig.IS_PRODUCT_ENV) {
            Thread.setDefaultUncaughtExceptionHandler(CrashHandler.getInstance());
        }
    }

    // 获取ApplicationContext
    public static GoldenStewardApplication getContext() {
        return sInstance;
    }

    public String getToken() {
        if (TextUtils.isEmpty(mToken)) {
            SharedPreferences sp = AppUtil.getDefaultSharedPreference(sInstance);
            mToken = sp.getString(Constant.SP_KEY_TOKEN, "");
        }
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
        SharedPreferences sp = AppUtil.getDefaultSharedPreference(sInstance);
        sp.edit().putString(Constant.SP_KEY_TOKEN, token).commit();
    }

    public String getUid() {
        if (TextUtils.isEmpty(mUid)) {
            SharedPreferences sp = AppUtil.getDefaultSharedPreference(sInstance);
            mUid = sp.getString(Constant.SP_KEY_USER_ID, null);
        }
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
        SharedPreferences sp = AppUtil.getDefaultSharedPreference(sInstance);
        sp.edit().putString(Constant.SP_KEY_USER_ID, uid).commit();
    }


    public String getPhone() {
        if (TextUtils.isEmpty(mPhone)) {
            SharedPreferences sp = AppUtil.getDefaultSharedPreference(sInstance);
            mPhone = sp.getString(Constant.SP_KEY_USER_PHONE, null);
        }
        return mPhone;
    }

    public void setPhone(String name) {
        mPhone = name;
        SharedPreferences sp = AppUtil.getDefaultSharedPreference(sInstance);
        sp.edit().putString(Constant.SP_KEY_USER_PHONE, name).commit();
        if (dao != null && user != null) {
            user.setPhone(name);
            dao.update(user);
        }
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


    public void saveUserInfo(UserServerParams params) {
        if (dao == null) {
            dao = DBHelper.getInstance(sInstance).getUserDao();
        }
        List<User> list = dao.queryBuilder().where(UserDao.Properties.Phone.eq(params.getPhone())).list();
        user = new User();
        if (list.size() > 0) {
            user = list.get(0);
        }
        user.setName(params.getName());
        user.setAddress(params.getAddress());
        user.setCity(params.getCity());
        user.setCity_id(params.getCity_id());
        user.setProvince(params.getProvince());
        user.setProvince_id(params.getProvince_id());
        user.setCompany(params.getCompany());
        user.setCompany_profile(params.getCompany_profile());
        user.setCompany_size(params.getCompany_size());
        user.setContact_name(params.getContact_name());
        user.setFixed_telephone(params.getFixed_telephone());
        user.setJob(params.getJob());
        user.setMail(params.getMail());
        user.setMain_industry(params.getMain_industry());
        user.setMain_product(params.getMain_product());
        user.setOperation_mode(params.getOperation_mode());
        user.setPhone(params.getPhone());
        if (!TextUtils.isEmpty(params.getPortrait())) {
            user.setPortrait(params.getPortrait());
        }
        user.setTurnover(params.getTurnover());
        if (list.size() > 0) {
            dao.update(user);
        } else {
            dao.insert(user);
        }
        setPhone(params.getPhone());
    }

    public void clearUser() {
        if (dao != null && user != null) {
            dao.delete(user);
        }
    }

    public boolean initUser() {
        dao = DBHelper.getInstance(sInstance).getUserDao();
        if (!TextUtils.isEmpty(getPhone())) {
            List<User> list = dao.queryBuilder().where(UserDao.Properties.Phone.eq(getPhone())).list();
            if (list.size() > 0) {
                user = list.get(0);
                return true;
            }
        }
        return false;
    }

    public Context getNowContext() {
        return sNowContext;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (activity.getParent() != null) {
            sNowContext = activity.getParent();
        } else {
            sNowContext = activity;
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (activity.getParent() != null) {
            sNowContext = activity.getParent();
        } else {
            sNowContext = activity;
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (activity.getParent() != null) {
            sNowContext = activity.getParent();
        } else {
            sNowContext = activity;
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (sNowContext != null) {
            sNowContext = null;
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
