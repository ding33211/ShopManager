package com.soubu.goldensteward.view.activity;


import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.HomeActivityDelegate;

/**
 * Created by dingsigang on 16-10-17.
 */
public class HomeActivity extends ActivityPresenter<HomeActivityDelegate> {
    @Override
    protected Class getDelegateClass() {
        return HomeActivityDelegate.class;
    }
}
