package com.soubu.goldensteward.view.activity;

import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.MyWalletActivityDelegate;

/**
 * Created by dingsigang on 16-10-20.
 */
public class MyWalletActivity extends ActivityPresenter<MyWalletActivityDelegate> {
    @Override
    protected Class<MyWalletActivityDelegate> getDelegateClass() {
        return MyWalletActivityDelegate.class;
    }
}
