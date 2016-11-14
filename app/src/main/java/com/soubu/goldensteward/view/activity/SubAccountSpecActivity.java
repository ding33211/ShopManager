package com.soubu.goldensteward.view.activity;

import android.content.res.Resources;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.SubAccountActivityDelegate;

import java.util.ArrayList;
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
        viewDelegate.setSettingMenuListener(R.menu.sub_account_spec, new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        List<String> titleList = new ArrayList<>();
        Resources resources = getResources();
        titleList.add(resources.getString(R.string.today_turnover));
        titleList.add(resources.getString(R.string.today_order_num));
        titleList.add(resources.getString(R.string.today_chat_num));
        titleList.add(resources.getString(R.string.today_offer_num));
        List<String> subTitleList = new ArrayList<>();
        subTitleList.add("0");
        subTitleList.add("0");
        subTitleList.add("0");
        subTitleList.add("0");
        viewDelegate.setTodayData(null, titleList, subTitleList);

        List<String> timeList = new ArrayList<>();
        timeList.add("2016-09-03  17:54:20");
        timeList.add("2016-09-03  17:54:20");
        timeList.add("2016-09-03  17:54:20");
        timeList.add("2016-09-03  17:54:20");
        timeList.add("2016-09-03  17:54:20");
        timeList.add("2016-09-03  17:54:20");
        timeList.add("2016-09-03  17:54:20");
        timeList.add("2016-09-03  17:54:20");
        timeList.add("2016-09-03  17:54:20");
        timeList.add("2016-09-03  17:54:20");
        timeList.add("2016-09-03  17:54:20");
        timeList.add("2016-09-03  17:54:20");
        viewDelegate.setLoginList(timeList);
    }
}
