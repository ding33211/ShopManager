package com.soubu.goldensteward.delegate;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;

/**
 * Created by lakers on 16/10/25.
 */

public class LoginActivityDelegate extends AppDelegate {
    @Override
    public int getRootLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public boolean ifNeedFullScreen() {
        return true;
    }
}
