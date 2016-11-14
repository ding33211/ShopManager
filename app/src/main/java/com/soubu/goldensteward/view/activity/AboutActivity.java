package com.soubu.goldensteward.view.activity;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.AboutActivityDelegate;

/**
 * Created by lakers on 16/10/31.
 */

public class AboutActivity extends ActivityPresenter<AboutActivityDelegate> {
    @Override
    protected Class<AboutActivityDelegate> getDelegateClass() {
        return AboutActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.about);
    }
}
