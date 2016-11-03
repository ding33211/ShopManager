package com.soubu.goldensteward.view.activity;

import android.text.TextUtils;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.StoreOwnerVerifyActivityDelegate;
import com.soubu.goldensteward.module.server.BaseData;
import com.soubu.goldensteward.module.server.BaseResp;
import com.soubu.goldensteward.module.server.VerificationServerParams;
import com.soubu.goldensteward.server.RetrofitRequest;
import com.soubu.goldensteward.view.fragment.StoreOwnerVerifyBaseInfoFragment;
import com.soubu.goldensteward.view.fragment.StoreOwnerVerifyStoreMergeFragment;
import com.soubu.goldensteward.view.fragment.StoreOwnerVerifyUploadCertificatesFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.baidu.location.h.j.v;

/**
 * Created by lakers on 16/10/27.
 */

public class StoreOwnerVerifyActivity extends ActivityPresenter<StoreOwnerVerifyActivityDelegate>
        implements StoreOwnerVerifyBaseInfoFragment.OnClickNextStepListener,
        StoreOwnerVerifyUploadCertificatesFragment.OnClickNextStepListener,
        StoreOwnerVerifyStoreMergeFragment.OnClickFinishListener{

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
    }


    @Override
    public void onClickStep1(VerificationServerParams params) {
        mParams.deltaCopy(params);
        if(!TextUtils.isEmpty(mParams.getFile_type())){
            viewDelegate.setFileType(mParams.getFile_type());
        }
        viewDelegate.clickNextStep();
    }

    @Override
    public void onBackPressed() {
        if(!viewDelegate.backPopFragment()){
            super.onBackPressed();
        }
    }

    @Override
    public void onClickStep2(VerificationServerParams params) {
        mParams.deltaCopy(params);
        RetrofitRequest.getInstance().submitCertification(mParams);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void gotoMerge(BaseResp resp){
        BaseResp resp1 = resp;
        if(resp.getResult() instanceof VerificationServerParams){
            viewDelegate.clickNextStep();
        }
    }

    @Override
    public void onClickFinish() {
        viewDelegate.clickNextStep();
    }
}
