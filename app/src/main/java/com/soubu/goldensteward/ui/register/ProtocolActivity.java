package com.soubu.goldensteward.ui.register;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;

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
