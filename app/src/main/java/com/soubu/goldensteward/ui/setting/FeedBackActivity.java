package com.soubu.goldensteward.ui.setting;

import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.base.BaseApplication;
import com.soubu.goldensteward.support.bean.server.FeedBackServerParams;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.support.utils.ShowWidgetUtil;
import com.soubu.goldensteward.support.web.core.BaseResponse;
import com.soubu.goldensteward.support.web.core.BaseSubscriber;

/**
 * Created by lakers on 16/10/31.
 */

public class FeedBackActivity extends ActivityPresenter<FeedBackActivityDelegate> {
    @Override
    protected Class<FeedBackActivityDelegate> getDelegateClass() {
        return FeedBackActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.suggestion_feedback);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedBackServerParams params = new FeedBackServerParams();
                if (viewDelegate.checkComplete(params)) {
                    BaseApplication.getWebModel()
                            .sendFeedBack(params)
                            .sendTo(new BaseSubscriber<BaseResponse>(FeedBackActivity.this) {
                                @Override
                                public void onSuccess(BaseResponse response) {
                                    onSendFeedBackSuccess();
                                }
                            });

                }
            }
        }, R.id.btn_submit);
    }

    public void onSendFeedBackSuccess() {
        ShowWidgetUtil.showShort(R.string.submit_success);
        finish();
    }
}
