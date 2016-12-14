package com.soubu.goldensteward.ui.referstore;

import android.content.Intent;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;

/**
 * Created by dingsigang on 16-12-12.
 */

public class StoreContractActivity extends ActivityPresenter<StoreContractActivityDelegate> implements View.OnClickListener {

    @Override
    protected Class<StoreContractActivityDelegate> getDelegateClass() {
        return StoreContractActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.store_contact);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.ll_card_holder, R.id.ll_search);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_card_holder:
                Intent intent = new Intent(this, CardHolderActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_search:
                Intent intent1 = new Intent(this, SearchContactActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
