package com.soubu.goldensteward.storepreview;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.storepreview.EvaluateInStorePreviewActivityDelegate;

/**
 * Created by dingsigang on 16-11-25.
 */

public class EvaluateInStorePreviewActivity extends ActivityPresenter<EvaluateInStorePreviewActivityDelegate> {
    @Override
    protected Class<EvaluateInStorePreviewActivityDelegate> getDelegateClass() {
        return EvaluateInStorePreviewActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.transaction_evaluate);
    }

    @Override
    protected void initData() {
        super.initData();
        viewDelegate.initTabLayout(new String[]{getString(R.string.all_evaluate) + "(" + "120" + ")", getString(R.string.total_transaction) + "(" + "120" + ")"});
    }
}
