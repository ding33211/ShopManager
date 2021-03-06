package com.soubu.goldensteward.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.StoreOwnerVerifyActivityDelegate;
import com.soubu.goldensteward.module.BaseEventBusResp;
import com.soubu.goldensteward.module.Constant;
import com.soubu.goldensteward.module.EventBusConfig;
import com.soubu.goldensteward.module.server.BaseResp;
import com.soubu.goldensteward.module.server.MergeServerParams;
import com.soubu.goldensteward.module.server.VerificationServerParams;
import com.soubu.goldensteward.server.RetrofitRequest;
import com.soubu.goldensteward.utils.ShowWidgetUtil;
import com.soubu.goldensteward.view.fragment.StoreOwnerVerifyBaseInfoFragment;
import com.soubu.goldensteward.view.fragment.StoreOwnerVerifyStoreMergeFragment;
import com.soubu.goldensteward.view.fragment.StoreOwnerVerifyUploadCertificatesFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by lakers on 16/10/27.
 */

public class StoreOwnerVerifyActivity extends ActivityPresenter<StoreOwnerVerifyActivityDelegate>
        implements StoreOwnerVerifyBaseInfoFragment.OnClickNextStepListener,
        StoreOwnerVerifyUploadCertificatesFragment.OnClickNextStepListener,
        StoreOwnerVerifyStoreMergeFragment.OnClickFinishListener {

    VerificationServerParams mParams;


    @Override
    protected Class<StoreOwnerVerifyActivityDelegate> getDelegateClass() {
        return StoreOwnerVerifyActivityDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        mParams = new VerificationServerParams();
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.store_owner_verify);
        int index = getIntent().getIntExtra(Constant.EXTRA_INDEX, 0);
        viewDelegate.setCurrentIndex(index);
    }


    @Override
    public void onClickStep1(VerificationServerParams params) {
        mParams.deltaCopy(params);
        if (!TextUtils.isEmpty(mParams.getFile_type())) {
            viewDelegate.setFileType(mParams.getFile_type());
        }
        viewDelegate.clickNextStep();
    }

    @Override
    public void onBackPressed() {
        if (!viewDelegate.backPopFragment()) {
            new AlertDialog.Builder(this).setMessage(R.string.make_sure_return_to_login).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(StoreOwnerVerifyActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).setNegativeButton(R.string.wrong_click, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
        }
    }

    @Override
    public void onClickStep2(VerificationServerParams params) {
        mParams.deltaCopy(params);
        RetrofitRequest.getInstance().submitCertification(mParams);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void gotoMerge(BaseEventBusResp resp) {
        int code = resp.getCode();
        if (code == EventBusConfig.SUBMIT_CERTIFICATION || code == EventBusConfig.SUBMIT_MERGE_CHILD ) {
            viewDelegate.clickNextStep();
        }
    }

    @Override
    public void onClickFinish(MergeServerParams params) {
        RetrofitRequest.getInstance().submitMergeChild(params);
    }
}
