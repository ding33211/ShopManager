package com.soubu.goldensteward.ui.market;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.base.BaseActivity;

/**
 * Created by dingsigang on 16-12-6.
 */

public class SignUpMvpActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_mvp);

        SignUpFragment fragment1 = new SignUpFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.main_fragcontainer, fragment1).commit();
    }

    @Override
    public void initWidget() {

    }
}
