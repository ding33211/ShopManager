package com.soubu.goldensteward.ui.register;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.support.bean.server.VerificationServerParams;
import com.soubu.goldensteward.support.utils.ShowWidgetUtil;

/**
 * Created by lakers on 16/10/28.
 */

public class StoreOwnerVerifyBaseInfoFragment extends FragmentPresenter<StoreOwnerVerifyBaseInfoFragmentDelegate> implements View.OnClickListener {
    OnClickNextStepListener mOnClickNextStep;
    Context mContext;
    VerificationServerParams mParams;


    @Override
    protected void initData() {
        super.initData();
        mParams = new VerificationServerParams();
    }

    @Override
    protected Class<StoreOwnerVerifyBaseInfoFragmentDelegate> getDelegateClass() {
        return StoreOwnerVerifyBaseInfoFragmentDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.btn_next_step, R.id.rl_company_type, R.id.rl_certificates_type);
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
        switch (v.getId()) {
            case R.id.btn_next_step:
                if (viewDelegate.checkComplete(mParams)) {
                    if (mOnClickNextStep != null) {
                        mOnClickNextStep.onClickStep1(mParams);
                    }
                }
                break;
            case R.id.rl_company_type:
                ShowWidgetUtil.showMultiItemDialog(getActivity(), R.string.company_type, R.array.company_type, false, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewDelegate.refreshContent(R.id.tv_company_type, getActivity().getResources().getStringArray(R.array.company_type)[which]);
                        mParams.setCom_type(which + 1 + "");
                        if (which == 0) {
                            viewDelegate.refreshOperator(R.string.operator_name, R.string.please_fill_operator_name);
                        } else {
                            viewDelegate.refreshOperator(R.string.enterprise_name, R.string.please_fill_enterprise_name);
                        }
                    }
                });
                break;
            case R.id.rl_certificates_type:
                ShowWidgetUtil.showMultiItemDialog(getActivity(), R.string.company_type, R.array.certification_type, false, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewDelegate.refreshContent(R.id.tv_certificate_type, getActivity().getResources().getStringArray(R.array.certification_type)[which]);
                        mParams.setFile_type(which + 1 + "");
                    }
                });
                break;

        }
    }

    public interface OnClickNextStepListener {
        void onClickStep1(VerificationServerParams params);
    }

}
