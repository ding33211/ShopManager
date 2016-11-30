package com.soubu.goldensteward.view.activity;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.RecyclerViewActivityDelegate;
import com.soubu.goldensteward.module.BaseEventBusResp;
import com.soubu.goldensteward.module.EventBusConfig;
import com.soubu.goldensteward.module.server.BaseDataObject;
import com.soubu.goldensteward.module.server.BaseResp;
import com.soubu.goldensteward.module.server.MessageServerParams;
import com.soubu.goldensteward.module.server.OperationReportServerParams;
import com.soubu.goldensteward.utils.ConvertUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dingsigang on 16-11-29.
 */

public class MessageActivity extends ActivityPresenter<RecyclerViewActivityDelegate> {
    List<MessageServerParams> mList;

    @Override
    protected Class<RecyclerViewActivityDelegate> getDelegateClass() {
        return RecyclerViewActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.message);
    }

    @Override
    protected void initData() {
        super.initData();
        mList = new ArrayList<>();
        MessageServerParams params = new MessageServerParams();
        params.setContent("第一条消息内容，第一条消息内容，第一条消息内容");
        params.setTime(ConvertUtil.dateToHH_mm(new Date()));
        params.setCount("23");
        params.setType("系统消息");
        mList.add(params);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getReportSuccess(BaseEventBusResp resp) {
        BaseResp resp1 = (BaseResp) resp.getObject();
        int code = resp.getCode();
        if (code == EventBusConfig.GET_MESSAGE) {
        }
    }
}
