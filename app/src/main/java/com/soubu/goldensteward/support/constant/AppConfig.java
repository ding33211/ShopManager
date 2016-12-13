package com.soubu.goldensteward.support.constant;

import android.content.Context;

import com.soubu.goldensteward.support.utils.WindowUtil;

import java.io.File;

/**
 * Created by lakers on 16/11/3.
 */

public class AppConfig {

    public static float IMAGE_MAX_WIDTH = 720;
    public static float IMAGE_MAX_HEIGHT = 1280;
    public static String CACHE_DIR = "";
    public static int mWidth = 0;
    public static int mHeight = 0;

    public static void init(Context context) {
        File cacheDir = context.getCacheDir();
        CACHE_DIR = cacheDir.getParent() + File.separator + cacheDir.getName();
        mWidth = WindowUtil.getDeviceWidth(context);
        mHeight = WindowUtil.getDeviceHeight(context);
    }
}
