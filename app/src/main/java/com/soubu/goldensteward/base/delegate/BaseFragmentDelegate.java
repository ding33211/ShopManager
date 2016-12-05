package com.soubu.goldensteward.base.delegate;


import com.soubu.goldensteward.base.mvp.view.AppDelegate;

/**
 * fragment视图层代理机制
 */
public abstract class BaseFragmentDelegate extends AppDelegate {

    @Override
    public boolean ifNeedHideToolBar() {
        return true;
    }
}
