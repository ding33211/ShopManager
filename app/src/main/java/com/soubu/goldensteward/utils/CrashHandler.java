package com.soubu.goldensteward.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.SystemClock;

import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * 异常处理工具
 * Created by dingsigang on 16-8-2.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    private static final boolean DEBUG = true;

    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/CrashTest/log/";
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFFIX = ".trace";

    private static CrashHandler sInstance = new CrashHandler();
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private Context mContext;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return sInstance;
    }

    public void init(Context context) {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }

    /**
     * 这个是最关键的函数，当程序中有未被捕获的异常，系统将会自动调用#uncaughtException方法
     * thread为出现未捕获异常的线程，ex为未捕获的异常，有了这个ex，我们就可以得到异常信息。
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            MobclickAgent.reportError(mContext, ex);
            //导出异常信息到SD卡中
            dumpExceptionToSDCard(ex);
            uploadExceptionToServer();
            //这里可以通过网络上传异常信息到服务器，便于开发人员分析日志从而解决bug
        } catch (IOException e) {
            e.printStackTrace();
        }

        ex.printStackTrace();

        //如果系统提供了默认的异常处理器，则交给系统去结束我们的程序，否则就由我们自己结束自己
        if (mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(thread, ex);
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    private void dumpExceptionToSDCard(Throwable ex) throws IOException {
        File file = mContext.getExternalFilesDir("Crash_sobu");

        if (!file.exists()) {
            file.mkdirs();
        }

        File outFile = new File(file, getCurProcessName(mContext) + SystemClock.elapsedRealtime());

        try {
            outFile.createNewFile();
            saveCrashInfo2File(outFile, ex);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return null;
    }

    private void saveCrashInfo2File(File file, Throwable ex) throws IOException {

        StringBuffer sb = new StringBuffer();


        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(sb.toString().getBytes());
        fos.close();
    }


    private void uploadExceptionToServer() {
        //伪代码 本方法用于将错误信息上传至服务器
    }


//    private final String TAG = CrashHandler.class.getSimpleName();
//
//    private static Thread.UncaughtExceptionHandler defaultHandler = null;
//
//    private Context context = null;
//
//    public CrashHandler(Context context) {
//        this.context = context;
//    }
//
//    /**
//     * 初始化,设置该CrashHandler为程序的默认处理器
//     */
//    public static void init(CrashHandler crashHandler) {
//        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
//        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
//    }
//
//    @Override
//    public void uncaughtException(Thread thread, Throwable ex) {
//        WriteLogUtil.e(TAG, ex.toString());
//        WriteLogUtil.e(TAG, getCrashDeviceInfo());
//        WriteLogUtil.e(TAG, getCrashInfo(ex));
//        // 调用系统错误机制
//        defaultHandler.uncaughtException(thread, ex);
//    }
//
//    /**
//     * 获取应用崩溃的详细信息
//     * @param ex
//     * @return
//     */
//    public String getCrashInfo(Throwable ex){
//        Writer writer = new StringWriter();
//        PrintWriter printWriter = new PrintWriter(writer);
//        ex.setStackTrace(ex.getStackTrace());
//        ex.printStackTrace(printWriter);
//        return writer.toString();
//    }
//    public String getCrashDeviceInfo(){
//        String versionName = AppUtil.getVersionName(context);
//        String model = android.os.Build.MODEL;
//        String androidVersion = android.os.Build.VERSION.RELEASE;
//        String manufacturer = android.os.Build.MANUFACTURER;
//        return versionName + "  " +
//                model + "  " +
//                androidVersion + "  " +
//                manufacturer;
//    }

}