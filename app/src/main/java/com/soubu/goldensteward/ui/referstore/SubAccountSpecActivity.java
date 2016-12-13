package com.soubu.goldensteward.ui.referstore;

import android.content.Intent;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.base.BaseApplication;
import com.soubu.goldensteward.support.constant.IntentKey;
import com.soubu.goldensteward.support.bean.server.SubAccountServerParams;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.support.web.core.BaseResponse;
import com.soubu.goldensteward.support.web.core.BaseSubscriber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lakers on 16/10/31.
 */

public class SubAccountSpecActivity extends ActivityPresenter<SubAccountActivityDelegate> implements View.OnClickListener {
    @Override
    protected Class<SubAccountActivityDelegate> getDelegateClass() {
        return SubAccountActivityDelegate.class;
    }


    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.spec_info);
//        viewDelegate.setSettingMenuListener(R.menu.sub_account_spec, new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                return false;
//            }
//        });
    }

    @Override
    protected void initData() {
        super.initData();
        String id = getIntent().getStringExtra(IntentKey.EXTRA_SUB_ACCOUNT_ID);
        if (!TextUtils.isEmpty(id)) {
            Map<String, String> map = new HashMap<>();
            map.put("id", id);
            BaseApplication.getWebModel()
                    .getSubAccountDetail(map)
                    .sendTo(new BaseSubscriber<BaseResponse<SubAccountServerParams>>(this) {
                        @Override
                        public void onSuccess(BaseResponse<SubAccountServerParams> response) {
                            SubAccountServerParams params = response.getResult().getData();
                            viewDelegate.setTop(params.getPortrait(), params.getName(), params.getPhone());
                            List<String> titleList = new ArrayList<>();
                            Resources resources = getResources();
                            titleList.add(resources.getString(R.string.today_offer_num));
                            titleList.add(resources.getString(R.string.today_order_num));
                            titleList.add(resources.getString(R.string.today_turnover));
                            titleList.add("");
                            List<String> subTitleList = new ArrayList<>();
                            subTitleList.add(params.getOffer_count());
                            subTitleList.add(params.getOrder_count());
                            subTitleList.add(params.getIncome());
                            subTitleList.add("");
                            viewDelegate.setTodayData(null, titleList, subTitleList);
                            List<String> timeList = new ArrayList<>();
                            timeList.addAll(Arrays.asList(params.getWeek_login()));
                            Collections.reverse(timeList);
                            viewDelegate.setLoginList(timeList);
                        }
                    });
        }
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.ll_go_to_store_contract);
    }

    @Override
    public void onClick(View view) {
        switch ((view.getId())) {
            case R.id.ll_go_to_store_contract:
                Intent intent = new Intent(this, StoreContractActivity.class);
                startActivity(intent);
                break;
        }
    }
}
