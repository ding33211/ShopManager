package com.soubu.goldensteward.base;

import android.support.v7.app.AppCompatActivity;

/**
 * 基类activity,放公有方法
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        //友盟埋点初始化
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }
}
