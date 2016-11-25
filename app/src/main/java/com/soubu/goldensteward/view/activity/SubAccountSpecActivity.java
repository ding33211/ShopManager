package com.soubu.goldensteward.view.activity;

import android.content.res.Resources;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.SubAccountActivityDelegate;
import com.soubu.goldensteward.module.BaseEventBusResp;
import com.soubu.goldensteward.module.Constant;
import com.soubu.goldensteward.module.EventBusConfig;
import com.soubu.goldensteward.module.server.BaseDataObject;
import com.soubu.goldensteward.module.server.BaseResp;
import com.soubu.goldensteward.module.server.SubAccountServerParams;
import com.soubu.goldensteward.server.RetrofitRequest;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by lakers on 16/10/31.
 */

public class SubAccountSpecActivity extends ActivityPresenter<SubAccountActivityDelegate> {
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
        String id = getIntent().getStringExtra(Constant.EXTRA_SUB_ACCOUNT_ID);
        if (!TextUtils.isEmpty(id)) {
            RetrofitRequest.getInstance().getSubAccountDetail(id);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetSubAccountSuccess(BaseEventBusResp resp) {
        BaseResp resp1 = (BaseResp) resp.getObject();
        int code = resp.getCode();
        if (code == EventBusConfig.GET_SUB_ACCOUNT_DETAIL) {
            SubAccountServerParams params = (SubAccountServerParams) ((BaseDataObject) resp1.getResult()).getData();
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
    }
}
