package com.soubu.goldensteward.view.activity;

import android.content.Intent;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.StorePreviewActivityDelegate;

/**
 * Created by dingsigang on 16-11-25.
 */

public class StorePreviewActivity extends ActivityPresenter<StorePreviewActivityDelegate> implements View.OnClickListener {
    @Override
    protected Class<StorePreviewActivityDelegate> getDelegateClass() {
        return StorePreviewActivityDelegate.class;
    }


    @Override
    protected void initData() {
        super.initData();
        viewDelegate.initTabLayout(new String[]{getString(R.string.store_home), getString(R.string.all_product),
                getString(R.string.new_product), getString(R.string.evaluate) + "(" + "100" + ")"});
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.iv_back, R.id.tv_product_classification);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_product_classification:
                Intent intent = new Intent(this, ProductClassificationActivity.class);
                startActivity(intent);
                break;
        }
    }
}
