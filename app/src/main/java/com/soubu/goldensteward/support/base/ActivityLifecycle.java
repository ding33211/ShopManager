package com.soubu.goldensteward.support.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

/**
 * 作者：余天然 on 2016/12/12 下午2:40
 */
public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    //当前上下文,用以显式当前dialog
    private Context sNowContext;

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
