package com.soubu.goldensteward.view.activity;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.FeedBackActivityDelegate;

/**
 * Created by lakers on 16/10/31.
 */

public class FeedBackActivity extends ActivityPresenter<FeedBackActivityDelegate> {
    @Override
    protected Class<FeedBackActivityDelegate> getDelegateClass() {
        return FeedBackActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.suggestion_feedback);
    }
}
