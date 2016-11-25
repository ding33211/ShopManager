package com.soubu.goldensteward.view.activity;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.ProductClassificationActivityDelegate;

/**
 * Created by dingsigang on 16-11-25.
 */

public class ProductClassificationActivity extends ActivityPresenter<ProductClassificationActivityDelegate> {
    @Override
    protected Class<ProductClassificationActivityDelegate> getDelegateClass() {
        return ProductClassificationActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.product_classification);
    }
}
