package com.soubu.goldensteward.support.base;

import com.growingio.android.sdk.collection.Configuration;
import com.growingio.android.sdk.collection.GrowingIO;
import com.soubu.goldensteward.BuildConfig;
import com.soubu.goldensteward.sdk.eventbus.MyEventBusIndex;
import com.soubu.goldensteward.support.utils.ChannelUtil;
import com.soubu.goldensteward.support.utils.CrashHandler;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by dingsigang on 16-10-18.
 */
public class AppApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this, "583e85ee04e2056927000b1f", ChannelUtil.getChannel(this)));
        initGrowingIO();
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
}
