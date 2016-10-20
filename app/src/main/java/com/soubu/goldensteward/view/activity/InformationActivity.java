package com.soubu.goldensteward.view.activity;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.InformationActivityDelegate;

/**
 * Created by dingsigang on 16-10-19.
 */
public class InformationActivity extends ActivityPresenter<InformationActivityDelegate> {

    @Override
    protected Class<InformationActivityDelegate> getDelegateClass() {
        return InformationActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.information);
    }
}
