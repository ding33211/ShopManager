package com.soubu.goldensteward.support.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;


import com.soubu.goldensteward.R;

import permissions.dispatcher.PermissionRequest;


/**
 * Created by dingsigang on 16-8-8.
 */
public class PermissionUtil {

    //前往设置界面的requestcode
    public static final int REQUEST_PERMISSION_SETTING = 1001;


    /**
     * 告知用户为什么要申请这个权限
     * @param context
     * @param messageResId
     * @param request
     */
    public static void showPermissionExplainDialog(Context context, int messageResId, final PermissionRequest request){
        new AlertDialog.Builder(context)
                .setPositiveButton(R.string.button_allow, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton(R.string.button_deny, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(messageResId)
                .show();
    }

    /**
     * 让用户去设置界面开启权限
     * @param activity
     * @param messageResId
     * @param ifNeedFinishActivity
     */
    public static void showGoToSettingDialog(final Activity activity, int messageResId, final boolean ifNeedFinishActivity){
        new AlertDialog.Builder(activity)
                .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                        intent.setData(uri);
                        activity.startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        if(ifNeedFinishActivity){
                            activity.finish();
                        }
                    }
                })
                .setCancelable(false)
                .setMessage(messageResId)
                .show();
    }



}
