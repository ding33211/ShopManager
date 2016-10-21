package com.soubu.goldensteward.delegate;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;

/**
 * Created by dingsigang on 16-10-20.
 */
public class FindPwdActivityDelegate extends AppDelegate{

    TextView mSendVerifyCode;

    CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            mSendVerifyCode.setText(millisUntilFinished/1000 + "秒");
        }

        @Override
        public void onFinish() {
            mSendVerifyCode.setEnabled(true);
            mSendVerifyCode.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
            mSendVerifyCode.setBackgroundResource(R.drawable.bg_orange_stroke_corners);
            mSendVerifyCode.setText(R.string.get_verify_code);
        }
    };

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_find_pwd;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mSendVerifyCode = get(R.id.tv_send_verify_code);
    }

    public void startTimer(){
        mSendVerifyCode.setTextColor(getActivity().getResources().getColor(R.color.subtitle_grey));
        mSendVerifyCode.setBackgroundResource(R.drawable.bg_grey_stroke_corners);
        timer.start();
    }

    public void clickVerify(){
        get(R.id.ll_security_verify).setVisibility(View.GONE);
        get(R.id.ll_find_pwd).setVisibility(View.VISIBLE);
        ((TextView)get(R.id.btn_confirm)).setText(R.string.confirm);
        ((TextView)get(R.id.tv_security_verification)).setTextColor(getActivity().getResources().getColor(R.color.line_color));
        ((TextView)get(R.id.tv_find_pay_pwd)).setTextColor(getActivity().getResources().getColor(R.color.subtitle_grey));
        get(R.id.tv_find_pwd_help).setVisibility(View.GONE);
    }

}
