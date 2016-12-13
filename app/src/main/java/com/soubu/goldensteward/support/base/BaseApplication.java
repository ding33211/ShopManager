package com.soubu.goldensteward.support.base;

import android.app.Application;
import android.content.Context;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.bugtags.library.Bugtags;
import com.soubu.goldensteward.BuildConfig;
import com.soubu.goldensteward.support.bean.OssConst;
import com.soubu.goldensteward.support.constant.AppConfig;
import com.soubu.goldensteward.support.helper.UserManager;
import com.soubu.goldensteward.support.utils.SPUtil;
import com.soubu.goldensteward.support.utils.ShowWidgetUtil;
import com.soubu.goldensteward.support.web.IWebModel;
import com.soubu.goldensteward.support.web.core.WebClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by dingsigang on 16-10-18.
 */
public class BaseApplication extends Application {
    private static BaseApplication sInstance;
    public static OSS oss;
    public static final String OSS_BUCKET_HOST_ID = OssConst.END_POINT;
    private static final String accessKey = OssConst.ACCESSKEYID;
    private static final String secretKey = OssConst.ACCESSKEYSECRET;

    private IWebModel webModel;
    private ActivityLifecycle activityLifecycle;
    private Scheduler scheduler;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        AppConfig.init(sInstance);
        UserManager.init(this);
        SPUtil.init(this);
        ShowWidgetUtil.register(this);
        initWeb();
        initActivityCycle();
        initOSSConfig();
        initScheduler();
        initBugtags();
    }

    private void initScheduler() {
        //核心有2个线程，最大线程数量为20，存活时间60s
        ExecutorService customThreadExecutor = new ThreadPoolExecutor(2, 20, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        scheduler = Schedulers.from(customThreadExecutor);
    }

    private String getData() {
        return null;
    }

    public static Scheduler getScheduler() {
        return getContext().scheduler;
    }

    private void initActivityCycle() {
        activityLifecycle = new ActivityLifecycle();
        this.registerActivityLifecycleCallbacks(activityLifecycle);//注册
    }

    public Context getNowContext() {
        return activityLifecycle.getNowContext();
    }

    private void initWeb() {
        WebClient webClient = new WebClient();
        webModel = webClient.getRetrofit().create(IWebModel.class);
    }

    public static IWebModel getWebModel() {
        return getContext().webModel;
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


    public void finishAllActivity() {
        activityLifecycle.finishAllActivity();
    }
}
