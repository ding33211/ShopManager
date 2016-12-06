package com.soubu.goldensteward.ui.wallet;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;

/**
 * Created by dingsigang on 16-10-20.
 */
public class AddBankAccountActivity extends ActivityPresenter<AddBankAccountActivityDelegate> implements View.OnClickListener{
    @Override
    protected Class<AddBankAccountActivityDelegate> getDelegateClass() {
        return AddBankAccountActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.account);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.iv_bank_help, R.id.iv_account_name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_bank_help:
                new AlertDialog.Builder(this).setMessage(R.string.bank_explain).setPositiveButton(R.string.find_help, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                break;
            case R.id.iv_account_name:
                new AlertDialog.Builder(this).setTitle(R.string.account_name_explain_title)
                        .setMessage(R.string.account_name_explain_message)
                        .setPositiveButton(R.string.got_it, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                break;
        }
    }
}
