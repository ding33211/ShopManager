package com.soubu.goldensteward.ui.market;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.bean.server.ActivitySpecServerParams;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.support.utils.ShowWidgetUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-12-6.
 */

public class ActivitySpecActivity extends ActivityPresenter<ActivitySpecActivityDelegate> {

    private static final int REQUEST_SIGN_UP = 1001;

    @Override
    protected Class<ActivitySpecActivityDelegate> getDelegateClass() {
        return ActivitySpecActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.activity_spec);
    }

    @Override
    protected void initData() {
        super.initData();
        List<ActivitySpecServerParams.Content> list = new ArrayList<>();
        ActivitySpecServerParams params = new ActivitySpecServerParams();
        ActivitySpecServerParams.Content content = params.new Content();
        content.setTitle("活动介绍");
        content.setContent("odiajsdioa 哦低价撒旦激动哦披萨迪莫吗懂啊没事没的破碎偶怕高抛破代发搜谱破卡我怕没武器前面迫切我磨破我们大");
        list.add(content);
        content = params.new Content();
        content.setTitle("活动介绍");
        content.setContent("odiajsdioa 哦低价撒旦激动哦披萨迪莫吗懂啊没事没的破碎偶怕高抛破代发搜谱破卡我怕没武器前面迫切我磨破我们大");
        list.add(content);
        content = params.new Content();
        content.setTitle("活动介绍");
        content.setContent("odiajsdioa 哦低价撒旦激动哦披萨迪莫吗懂啊没事没的破碎偶怕高抛破代发搜谱破卡我怕没武器前面迫切我磨破我们大");
        list.add(content);
        content = params.new Content();
        content.setTitle("活动介绍");
        content.setContent("odiajsdioa 哦低价撒旦激动哦披萨迪莫吗懂啊没事没的破碎偶怕高抛破代发搜谱破卡我怕没武器前面迫切我磨破我们大");
        list.add(content);
        content = params.new Content();
        content.setTitle("活动介绍");
        content.setContent("odiajsdioa 哦低价撒旦激动哦披萨迪莫吗懂啊没事没的破碎偶怕高抛破代发搜谱破卡我怕没武器前面迫切我磨破我们大");
        list.add(content);
        viewDelegate.setActivitySpecContent(list);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] stores = new String[]{"店铺1大阿达啊大大大大的， 18937465589", "店铺2大大大大大奥德， 18811556695"};
                ShowWidgetUtil.showMultiItemDialog(ActivitySpecActivity.this, R.string.please_choose_store, stores, false, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ActivitySpecActivity.this, SignUpActivity.class);
                        startActivityForResult(intent, REQUEST_SIGN_UP);
                    }
                });
            }
        }, R.id.btn_sign_up_now);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SIGN_UP) {
                finish();
            }
        }
    }
}
