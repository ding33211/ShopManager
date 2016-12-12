package com.soubu.goldensteward.support.base;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.bugtags.library.Bugtags;
import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.net.ServerErrorUtil;
import com.soubu.goldensteward.support.utils.ActivityContainer;
import com.soubu.goldensteward.support.utils.PermissionUtil;
import com.soubu.goldensteward.support.utils.ShowWidgetUtil;
import com.soubu.goldensteward.support.web.mvp.BaseView;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * 基类activity,放公有方法
 */
@RuntimePermissions
public abstract class BaseActivity extends RxAppCompatActivity implements BaseView {

    public boolean mEventBusJustForThis = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityContainer.getInstance().addActivity(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //友盟埋点初始化
//        MobclickAgent.onResume(this);
        Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ShowWidgetUtil.dismissProgressDialog();
        Bugtags.onPause(this);
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityContainer.getInstance().removeActivity(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void throwError(Integer errorCode) {
        if (!mEventBusJustForThis) {
            return;
        } else {
            mEventBusJustForThis = false;
        }
        ServerErrorUtil.handleServerError(errorCode);
    }


    //是否需要退出
    private boolean mNeedQuit = false;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mNeedQuit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyDownTwiceFinish()) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (!mNeedQuit) {
                    mNeedQuit = true;
                    ShowWidgetUtil.showShort(R.string.click_again_to_quit);
                    // 利用handler延迟发送更改状态信息
                    mHandler.sendEmptyMessageDelayed(0, 2000);
                } else {
                    ActivityContainer.getInstance().finishAllActivity();
                    System.exit(0);
                }
            }
        } else {
            super.onKeyDown(keyCode, event);
        }
        return true;
    }

    public boolean keyDownTwiceFinish() {
        return false;
    }

    public void onClickCustomerServicePhone(View view) {
        BaseActivityPermissionsDispatcher.loadWithCheck(this);
    }

    //需要验证权限的方法
    @NeedsPermission({Manifest.permission.CALL_PHONE})
    void load() {
        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + getString(R.string.customer_service_phone_number)));
        startActivity(intent);
    }

    //之前拒绝过这个请求,当再次请求这个权限的时候调起的方法
    //建议是对话框的方式,告知用户请求这个权限的原因
    //注意由于是在build中生成的类文件,因此每次对注释方法有有修改需要clean,rebuild.
    @OnShowRationale({Manifest.permission.CALL_PHONE})
    void showDialog(PermissionRequest request) {
        PermissionUtil.showPermissionExplainDialog(getApplicationContext(), R.string.permission_explain_phone, request);
    }

    @OnPermissionDenied({Manifest.permission.CALL_PHONE})
    void onPermissionDenied() {
        finish();
    }

    @OnNeverAskAgain({Manifest.permission.CALL_PHONE})
    void showGoToSettingDialog() {
        PermissionUtil.showGoToSettingDialog(this, R.string.permission_explain_phone, true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        BaseActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PermissionUtil.REQUEST_PERMISSION_SETTING:
                default:
                    break;
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //注：回调 3
        Bugtags.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public <T> LifecycleTransformer<T> bindLife() {
        return bindToLifecycle();
    }


}
