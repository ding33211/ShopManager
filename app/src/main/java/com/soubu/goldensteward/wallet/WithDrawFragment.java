package com.soubu.goldensteward.wallet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.wallet.WithDrawFragmentDelegate;
import com.soubu.goldensteward.wallet.AddBankAccountActivity;
import com.soubu.goldensteward.widget.PayPassWordView;

/**
 * Created by dingsigang on 16-10-20.
 */
public class WithDrawFragment extends FragmentPresenter<WithDrawFragmentDelegate> implements View.OnClickListener{
    OnPayPwdMatchListener mListener;
    Context mContext;

    @Override
    protected Class<WithDrawFragmentDelegate> getDelegateClass() {
        return WithDrawFragmentDelegate.class;
    }

    @Override
    protected void initView() {
        super.initView();
        viewDelegate.get(R.id.tv_replace).setVisibility(View.GONE);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if(context instanceof OnPayPwdMatchListener){
            mListener = (OnPayPwdMatchListener) context;
        }
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.tv_replace, R.id.ll_add_bank_account, R.id.btn_confirm);

    }

    @Override
    public void onClick(final View v) {
        Intent intent = null;
        switch (v.getId()) {
//            case R.id.tv_customer_service_num:
//                intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + getString(R.string.customer_service_phone_number)));
//                break;
            case R.id.tv_replace:
            case R.id.ll_add_bank_account:
                intent = new Intent(mContext, AddBankAccountActivity.class);
                break;
            case R.id.btn_confirm:
                View customView = LayoutInflater.from(mContext).inflate(R.layout.alertdialog_input_password, null);
                View cancelView = customView.findViewById(R.id.btn_cancel);
                final PayPassWordView pwdView = (PayPassWordView) customView.findViewById(R.id.ppwv_pay_password);
                final AlertDialog dialog = new AlertDialog.Builder(mContext).setView(customView).setCancelable(false).create();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        ((InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromInputMethod(v.getWindowToken(), 0);
                    }
                });
                dialog.show();
                cancelView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                pwdView.setInputCallBack(new PayPassWordView.InputCallBack() {
                    @Override
                    public void onInputFinish(String result) {
                        if(mListener != null){
                            mListener.onPayPwdMatch();
                            dialog.dismiss();
                        }
                    }
                });
        }
        if (null != intent) {
            startActivity(intent);
        }
    }

    public interface OnPayPwdMatchListener{
        void onPayPwdMatch();
    }

}
