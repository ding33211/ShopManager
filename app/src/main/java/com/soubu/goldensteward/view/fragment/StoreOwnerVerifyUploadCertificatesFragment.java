package com.soubu.goldensteward.view.fragment;

import android.content.Context;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.delegate.StoreOwnerVerifyUploadCertificatesFragmentDelegate;

/**
 * Created by lakers on 16/10/28.
 */

public class StoreOwnerVerifyUploadCertificatesFragment extends FragmentPresenter<StoreOwnerVerifyUploadCertificatesFragmentDelegate>
        implements View.OnClickListener{
    OnClickNextStepListener mOnClickNextStep;
    Context mContext;

    @Override
    protected Class<StoreOwnerVerifyUploadCertificatesFragmentDelegate> getDelegateClass() {
        return StoreOwnerVerifyUploadCertificatesFragmentDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.btn_next_step);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof OnClickNextStepListener) {
            mOnClickNextStep = (OnClickNextStepListener) context;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next_step:
                if(mOnClickNextStep != null){
                    mOnClickNextStep.onClickStep2();
                }
                break;

        }
    }

    public interface OnClickNextStepListener {
        void onClickStep2();
    }



}
