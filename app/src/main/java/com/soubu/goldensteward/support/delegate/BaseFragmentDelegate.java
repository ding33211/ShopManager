package com.soubu.goldensteward.support.delegate;


import com.soubu.goldensteward.support.mvp.view.AppDelegate;

/**
 * fragment视图层代理机制
 */
public abstract class BaseFragmentDelegate extends AppDelegate {

    @Override
    public boolean ifNeedHideToolBar() {
        return true;
    }
}
