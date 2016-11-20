package com.soubu.goldensteward.view.activity;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.ProtocolActivityDelegate;

/**
 * Created by lakers on 16/11/19.
 */

public class ProtocolActivity extends ActivityPresenter<ProtocolActivityDelegate> {
    @Override
    protected Class<ProtocolActivityDelegate> getDelegateClass() {
        return ProtocolActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.register_protocol);
    }
}
