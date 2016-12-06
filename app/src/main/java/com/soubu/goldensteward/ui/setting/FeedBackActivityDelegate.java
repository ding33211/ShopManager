package com.soubu.goldensteward.ui.setting;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.mvp.view.AppDelegate;
import com.soubu.goldensteward.support.bean.server.FeedBackServerParams;
import com.soubu.goldensteward.support.utils.ShowWidgetUtil;

/**
 * Created by lakers on 16/10/31.
 */

public class FeedBackActivityDelegate extends AppDelegate implements View.OnClickListener {
    Button[] buttons;
    int mLastClickIndex = 0;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        buttons = new Button[]{get(R.id.btn_feedback_1), get(R.id.btn_feedback_2), get(R.id.btn_feedback_3)
                , get(R.id.btn_feedback_4), get(R.id.btn_feedback_5), get(R.id.btn_feedback_6)};
        setOnClickListener(this, R.id.btn_feedback_1, R.id.btn_feedback_2, R.id.btn_feedback_3, R.id.btn_feedback_4, R.id.btn_feedback_5, R.id.btn_feedback_6);
    }


    @Override
    public void onClick(View v) {
        buttons[mLastClickIndex].setSelected(false);
        switch (v.getId()) {
            case R.id.btn_feedback_1:
                mLastClickIndex = 0;
                break;
            case R.id.btn_feedback_2:
                mLastClickIndex = 1;
                break;
            case R.id.btn_feedback_3:
                mLastClickIndex = 2;
                break;
            case R.id.btn_feedback_4:
                mLastClickIndex = 3;
                break;
            case R.id.btn_feedback_5:
                mLastClickIndex = 4;
                break;
            case R.id.btn_feedback_6:
                mLastClickIndex = 5;
                break;
        }
        v.setSelected(true);
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }

    public boolean checkComplete(FeedBackServerParams params) {
        if (mLastClickIndex == 0) {
            ShowWidgetUtil.showShort(R.string.please_choose_feedback_type);
            return false;
        }
        EditText etContent = get(R.id.et_content);
        if (TextUtils.isEmpty(etContent.getText())) {
            ShowWidgetUtil.showShort(R.string.please_input_your_valuable_advice);
            return false;
        }
        params.setType(mLastClickIndex + 1 + "");
        params.setContent(etContent.getText().toString());
        return true;
    }
}
