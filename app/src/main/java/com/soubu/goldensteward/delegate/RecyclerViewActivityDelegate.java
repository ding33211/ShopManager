package com.soubu.goldensteward.delegate;

/**
 * Created by dingsigang on 16-10-21.
 */
public class RecyclerViewActivityDelegate extends RecyclerViewFragmentDelegate {
    @Override
    public boolean ifNeedHideToolBar() {
        return false;
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }
}
