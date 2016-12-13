package com.soubu.goldensteward.support.utils;

import android.util.Log;

import java.text.SimpleDateFormat;

/**
 * 作者：余天然 on 2016/12/9 下午8:36
 */
public class SoutUtil {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");


    public static void print(String msg) {
        performPrint(msg);
    }

    //打印异常信息
    public static void printException(Throwable throwable) {
        print(Log.getStackTraceString(throwable));
    }

    //执行打印
    private static void performPrint(String msg) {
        String threadName = Thread.currentThread().getName();
        String lineIndicator = getLineIndicator();
        System.out.println(dateFormat.format(System.currentTimeMillis()) + " " + threadName + " " + lineIndicator + " " + msg);
    }

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
