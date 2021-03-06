package com.soubu.goldensteward.view.activity;

import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.FeedBackActivityDelegate;
import com.soubu.goldensteward.module.BaseEventBusResp;
import com.soubu.goldensteward.module.EventBusConfig;
import com.soubu.goldensteward.module.server.FeedBackServerParams;
import com.soubu.goldensteward.server.RetrofitRequest;
import com.soubu.goldensteward.utils.ShowWidgetUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import retrofit2.Retrofit;

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
