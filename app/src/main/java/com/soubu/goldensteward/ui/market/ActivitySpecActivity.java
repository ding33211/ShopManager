package com.soubu.goldensteward.ui.market;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.base.BaseApplication;
import com.soubu.goldensteward.support.bean.server.ActivitySpecServerParams;
import com.soubu.goldensteward.support.bean.server.SubAccountInActivityServerParams;
import com.soubu.goldensteward.support.constant.IntentKey;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.support.utils.ShowWidgetUtil;
import com.soubu.goldensteward.support.web.core.BaseResponse;
import com.soubu.goldensteward.support.web.core.BaseSubscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dingsigang on 16-12-6.
 */

public class ActivitySpecActivity extends ActivityPresenter<ActivitySpecActivityDelegate> {

    private static final int REQUEST_SIGN_UP = 1001;
    private int mId = -1;

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
        Map<String, Integer> map = new HashMap<>();
        mId = getIntent().getIntExtra(IntentKey.EXTRA_ACTIVITY_ID, -1);
        if(mId != -1){
            map.put("id", mId);
            BaseApplication.getWebModel().getActivitySpec(map).sendTo(new BaseSubscriber<BaseResponse<ActivitySpecServerParams>>(this) {
                @Override
                public void onSuccess(BaseResponse<ActivitySpecServerParams> response) {
                    viewDelegate.setActivitySpecContent(response.getResult().getData());
                }
            });
        }

    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseApplication.getWebModel().getSubAccountInActivity().sendTo(new BaseSubscriber<BaseResponse<List<SubAccountInActivityServerParams>>>(ActivitySpecActivity.this) {
                    @Override
                    public void onSuccess(BaseResponse<List<SubAccountInActivityServerParams>> response) {
                        List<SubAccountInActivityServerParams> list = response.getResult().getData();
                        List<String> accounts = new ArrayList<String>();
                        for(SubAccountInActivityServerParams params : list){
                            String a = params.getName() + ", " + params.getPhone();
                            accounts.add(a);
                        }
                        String[] stores = accounts.toArray(new String[]{});
                        ShowWidgetUtil.showMultiItemDialog(ActivitySpecActivity.this, R.string.please_choose_store, stores, false, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(mId != -1){
                                    Intent intent = new Intent(ActivitySpecActivity.this, SignUpActivity.class);
                                    intent.putExtra(IntentKey.EXTRA_ACCOUNT_ID, list.get(which).getUid());
                                    intent.putExtra(IntentKey.EXTRA_ACTIVITY_ID, mId);
                                    startActivityForResult(intent, REQUEST_SIGN_UP);
                                }

                            }
                        });
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
