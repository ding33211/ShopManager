package com.soubu.goldensteward.delegate;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;

/**
 * Created by lakers on 16/10/25.
 */

public class SplashActivityDelegate extends AppDelegate {
    @Override
    public int getRootLayoutId() {
        return R.layout.activity_splash;
    }

    //欢迎界面需要全屏
    @Override
    public boolean ifNeedFullScreen() {
        return true;
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }
}
