package com.soubu.goldensteward.support.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-11-16.
 */

public class ActivityContainer {
    private ActivityContainer() {
    }

    private static ActivityContainer instance = new ActivityContainer();
    private static List<Activity> activityStack = new ArrayList<Activity>();

    public static ActivityContainer getInstance() {
        return instance;
    }

    public void addActivity(Activity aty) {
        activityStack.add(aty);
    }

    public void removeActivity(Activity aty) {
        activityStack.remove(aty);
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

}
