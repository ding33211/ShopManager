package com.soubu.goldensteward.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.bugtags.library.Bugtags;
import com.soubu.goldensteward.R;
import com.soubu.goldensteward.server.ServerErrorUtil;
import com.soubu.goldensteward.utils.ActivityContainer;
import com.soubu.goldensteward.utils.ShowWidgetUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 基类activity,放公有方法
 */
public class BaseActivity extends AppCompatActivity {

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
        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + getString(R.string.customer_service_phone_number)));
        startActivity(intent);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //注：回调 3
        Bugtags.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }

}
