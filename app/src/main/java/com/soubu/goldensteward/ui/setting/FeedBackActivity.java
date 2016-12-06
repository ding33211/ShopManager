package com.soubu.goldensteward.ui.setting;

import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.support.bean.BaseEventBusResp;
import com.soubu.goldensteward.support.bean.EventBusConfig;
import com.soubu.goldensteward.support.bean.server.FeedBackServerParams;
import com.soubu.goldensteward.support.server.RetrofitRequest;
import com.soubu.goldensteward.support.utils.ShowWidgetUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
                    RetrofitRequest.getInstance().sendFeedBack(params);
                }
            }
        }, R.id.btn_submit);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSendFeedBackSuccess(BaseEventBusResp resp) {
        int code = resp.getCode();
        if (code == EventBusConfig.SEND_FEEDBACK) {
            ShowWidgetUtil.showShort(R.string.submit_success);
            finish();
        }
    }
}
