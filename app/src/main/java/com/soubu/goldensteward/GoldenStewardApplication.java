package com.soubu.goldensteward;

import android.app.Application;

import com.soubu.goldensteward.sdk.eventbus.MyEventBusIndex;
import com.soubu.goldensteward.utils.ShowWidgetUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by dingsigang on 16-10-18.
 */
public class GoldenStewardApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
        ShowWidgetUtil.register(this);
    }
}
