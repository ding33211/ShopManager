package com.soubu.goldensteward.view.fragment;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.delegate.MarketingFragmentDelegate;

/**
 * Created by dingsigang on 16-10-18.
 */
public class MarketingFragment extends FragmentPresenter<MarketingFragmentDelegate> {
    @Override
    protected Class getDelegateClass() {
        return MarketingFragmentDelegate.class;
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + getString(R.string.customer_service_phone_number)));
                startActivity(intent);
            }
        }, R.id.tv_contact_customer_service);
    }
}
