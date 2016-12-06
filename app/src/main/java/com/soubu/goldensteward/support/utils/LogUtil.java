package com.soubu.goldensteward.support.utils;

import android.util.Log;

import com.soubu.goldensteward.BuildConfig;

import java.text.SimpleDateFormat;

/**
 * Created by yutianran on 16/2/25.
 */
public class LogUtil {

    private static int LEVEL = Log.DEBUG;//默认level
    private static String TAG = "print";//默认tag
    private static boolean IS_DEBUG = BuildConfig.DEBUG;//默认显示log

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

    //设置默认的Level
    public static void setDefaultLevel(int level) {
        LogUtil.LEVEL = level;
    }

    //设置默认的TAG
    public static void setDefaultTag(String tag) {
        LogUtil.TAG = tag;
    }

    //设置是否是Debug模式
    public static void setIsDebug(boolean isDebug) {
        LogUtil.IS_DEBUG = isDebug;
    }

    //打印
    public static void print(String msg) {
        performPrint(LEVEL, TAG, msg);
    }

    //打印方法的调用流程
    public static void printStack() {
        printException(new Exception());
    }

    //打印异常信息
    public static void printException(Throwable throwable) {
        print(Log.ERROR, "AndroidRuntime", Log.getStackTraceString(throwable));
    }

    //打印-自定义Tag
    public static void print(String tag, String msg) {
        performPrint(LEVEL, tag, msg);
    }

    //打印-自定义Level
    public static void print(int level, String msg) {
        performPrint(level, TAG, msg);
    }

    //打印-自定义Tag,自定义Level
    public static void print(int level, String tag, String msg) {
        performPrint(level, tag, msg);
    }

    //执行打印
    private static void performPrint(int level, String tag, String msg) {
        //非Debug版本，则不打印日志
        if (!IS_DEBUG) {
            return;
        }
        String threadName = Thread.currentThread().getName();
        String lineIndicator = getLineIndicator();
        Log.println(level, tag, dateFormat.format(System.currentTimeMillis()) + " " + threadName + " " + lineIndicator + " " + msg);
    }

    //获取行所在的方法指示
    //获取行所在的方法指示
    private static String getLineIndicator() {
        //3代表方法的调用深度：0-getLineIndicator，1-performPrint，2-print，3-调用该工具类的方法位置
        StackTraceElement element = ((new Exception()).getStackTrace())[3];
        StringBuffer sb = new StringBuffer("(")
                .append(element.getFileName()).append(":")
                .append(element.getLineNumber()).append(").")
                .append(element.getMethodName()).append(":");
        return sb.toString();
    }
}
