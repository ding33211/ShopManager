package com.soubu.goldensteward.support.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.bugtags.library.Bugtags;
import com.growingio.android.sdk.collection.Configuration;
import com.growingio.android.sdk.collection.GrowingIO;
import com.soubu.goldensteward.BuildConfig;
import com.soubu.goldensteward.sdk.eventbus.MyEventBusIndex;
import com.soubu.goldensteward.support.bean.AppConfig;
import com.soubu.goldensteward.support.bean.OssConst;
import com.soubu.goldensteward.support.bean.server.UserServerParams;
import com.soubu.goldensteward.support.constant.SpKey;
import com.soubu.goldensteward.support.greendao.DBHelper;
import com.soubu.goldensteward.support.greendao.User;
import com.soubu.goldensteward.support.greendao.UserDao;
import com.soubu.goldensteward.support.utils.ChannelUtil;
import com.soubu.goldensteward.support.utils.CrashHandler;
import com.soubu.goldensteward.support.utils.SPUtil;
import com.soubu.goldensteward.support.utils.ShowWidgetUtil;
import com.soubu.goldensteward.support.web.IWebModel;
import com.soubu.goldensteward.support.web.core.WebClient;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by dingsigang on 16-10-18.
 */
public class BaseApplication extends Application implements Application.ActivityLifecycleCallbacks {
    private static BaseApplication sInstance;
    //当前上下文,用以显式当前dialog
    private Context sNowContext;
    public static OSS oss;
    private UserDao dao;
    private User user;
    public static final String OSS_BUCKET_HOST_ID = OssConst.END_POINT;
    private static final String accessKey = OssConst.ACCESSKEYID;
    private static final String secretKey = OssConst.ACCESSKEYSECRET;


    private static WebClient webClient;
    private static IWebModel webModel;

    @Override
    public void onCreate() {
        super.onCreate();
        SPUtil.init(this);
        this.registerActivityLifecycleCallbacks(this);//注册
        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this, "583e85ee04e2056927000b1f", ChannelUtil.getChannel(this)));
        sInstance = (BaseApplication) getApplicationContext();
        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
        ShowWidgetUtil.register(this);
        AppConfig.init(sInstance);
        initOSSConfig();
        initBugtags();
        initGrowingIO();
        initWeb();
    }

    private void initWeb() {
        webClient = new WebClient();
        webModel = webClient.getRetrofit().create(IWebModel.class);
    }

    public static IWebModel getWebModel() {
        return webModel;
    }

    private void initGrowingIO() {
        //growingio
        GrowingIO.startWithConfiguration(this, new Configuration()
                .useID()
                .trackAllFragments()
                .setChannel(ChannelUtil.getChannel(this)));

        if (BuildConfig.IS_PRODUCT_ENV) {
            Thread.setDefaultUncaughtExceptionHandler(CrashHandler.getInstance());
        }
    }

    private void initBugtags() {
        //只在非debug模式下打开bugtags
        if (!BuildConfig.DEBUG) {
            if (BuildConfig.IS_PRODUCT_ENV) {
                Bugtags.start("a4aa632f49691a7caa3a1c49f038dc0d", this, Bugtags.BTGInvocationEventNone);
            } else {
                Bugtags.start("4c9c0ecb1faf11a9e160449041a0254a", this, Bugtags.BTGInvocationEventBubble);
            }
        }
    }

    // 获取ApplicationContext
    public static BaseApplication getContext() {
        return sInstance;
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
        SPUtil.putValue(SpKey.USER_PHONE, params.getPhone());
    }

    public void clearUser() {
        if (dao != null && user != null) {
            dao.delete(user);
        }
    }

    public boolean initUser() {
        dao = DBHelper.getInstance(sInstance).getUserDao();
        String phone = SPUtil.getValue(SpKey.USER_PHONE, "");
        if (!TextUtils.isEmpty(phone)) {
            List<User> list = dao.queryBuilder().where(UserDao.Properties.Phone.eq(phone)).list();
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
