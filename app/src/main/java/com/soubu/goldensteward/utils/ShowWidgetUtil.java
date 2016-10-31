/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of Meizhi
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.soubu.goldensteward.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.soubu.goldensteward.R;

public class ShowWidgetUtil {

    public static Context sContext;


    private ShowWidgetUtil() {
    }


    public static void register(Context context) {
        sContext = context.getApplicationContext();
    }


    private static void check() {
        if (sContext == null) {
            throw new NullPointerException(
                    "Must initial call ShowWidgetUtil.register(Context context) in your " +
                            "<? " +
                            "extends Application class>");
        }
    }


    public static void showShort(int resId) {
        check();
        Toast.makeText(sContext, resId, Toast.LENGTH_SHORT).show();
    }


    public static void showShort(String message) {
        check();
        Toast.makeText(sContext, message, Toast.LENGTH_SHORT).show();
    }


    public static void showLong(int resId) {
        check();
        Toast.makeText(sContext, resId, Toast.LENGTH_LONG).show();
    }


    public static void showLong(String message) {
        check();
        Toast.makeText(sContext, message, Toast.LENGTH_LONG).show();
    }


    public static void showLongX2(String message) {
        showLong(message);
        showLong(message);
    }


    public static void showLongX2(int resId) {
        showLong(resId);
        showLong(resId);
    }


    public static void showLongX3(int resId) {
        showLong(resId);
        showLong(resId);
        showLong(resId);
    }


    public static void showLongX3(String message) {
        showLong(message);
        showLong(message);
        showLong(message);
    }


    public static void showMultiItemDialog(Activity activity, int titleRes, int arrayRes, boolean multiChoice, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setItems(arrayRes, listener);
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public interface OnClickCustomInputConfirm {
        void onConfirm(String content);
    }

    public static void showCustomInputDialog(Activity activity, int titleRes, int hintRes, final OnClickCustomInputConfirm listener){
        View customView = LayoutInflater.from(activity).inflate(R.layout.dialog_custom_view, null);
        ((TextView) customView.findViewById(R.id.tv_title)).setText(titleRes);
        final EditText etContent = (EditText) customView.findViewById(R.id.et_content);
        etContent.setHint(hintRes);
        View vCancel = customView.findViewById(R.id.btn_cancel);
        View vConfirm = customView.findViewById(R.id.btn_confirm);
        final AlertDialog dialog = new AlertDialog.Builder(activity).setView(customView).create();
        dialog.show();
        vConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etContent.getText().toString();
                if(TextUtils.isEmpty(content)){
                    ShowWidgetUtil.showShort(R.string.input_empty_error);
                    return;
                } else {
                    if(listener != null){
                        listener.onConfirm(content);
                        dialog.dismiss();
                    }
                }

            }
        });
        vCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    public static void showVerifyCodeTimerStart(final TextView textView){
        CountDownTimer timer = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText(millisUntilFinished/1000 + "秒");
            }

            @Override
            public void onFinish() {
                textView.setEnabled(true);
                textView.setTextColor(textView.getResources().getColor(R.color.colorPrimary));
                textView.setBackgroundResource(R.drawable.bg_orange_stroke_corners);
                textView.setText(R.string.get_verify_code);
            }
        };
        textView.setEnabled(false);
        textView.setTextColor(textView.getResources().getColor(R.color.subtitle_grey));
        textView.setBackgroundResource(R.drawable.bg_grey_stroke_corners);
        timer.start();
    }


//    public static void showSingleChoiceDialog(Activity activity, int titleRes, String[] items, DialogInterface.OnClickListener singleListener,
//                                              DialogInterface.OnClickListener confirmListener, DialogInterface.OnClickListener cancelListener){
//        AlertDialog dialog = new AlertDialog.Builder(activity).setTitle(titleRes)
//                .setSingleChoiceItems(items, -1, singleListener).setPositiveButton(R.string.confirm, confirmListener)
//                .setNegativeButton(R.string.cancel, cancelListener).create();
//        dialog.show();
//    }

}
