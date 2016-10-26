package com.soubu.goldensteward.delegate;

import android.os.CountDownTimer;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;

/**
 * Created by lakers on 16/10/26.
 */

public class RegisterOrForgetPwdActivityDelegate extends AppDelegate {

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
        return R.layout.activity_register_or_forget_pwd;
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
}
