package com.soubu.goldensteward.view.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.delegate.HomeFragmentDelegate;
import com.soubu.goldensteward.module.Constant;
import com.soubu.goldensteward.view.activity.InformationActivity;
import com.soubu.goldensteward.view.activity.MyWalletActivity;
import com.soubu.goldensteward.view.activity.OperationReportActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-10-18.
 */
public class HomeFragment extends FragmentPresenter<HomeFragmentDelegate> {
    @Override
    protected Class getDelegateClass() {
        return HomeFragmentDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        List<String> titleList = new ArrayList<>();
        Resources resources = getActivity().getResources();
        titleList.add(resources.getString(R.string.today_visitor_num));
        titleList.add(resources.getString(R.string.today_product_visit));
        titleList.add(resources.getString(R.string.today_order_num));
        titleList.add(resources.getString(R.string.today_return_rate));
        List<String> subTitleList = new ArrayList<>();
        subTitleList.add("0");
        subTitleList.add("0");
        subTitleList.add("0");
        subTitleList.add("0");
        viewDelegate.setTodayData(null, titleList, subTitleList);

        List<String> actionTitleList = new ArrayList<>();
        actionTitleList.add(resources.getString(R.string.my_wallet));
        actionTitleList.add(resources.getString(R.string.operation_report));
        actionTitleList.add(resources.getString(R.string.my_customer));
        actionTitleList.add(resources.getString(R.string.setting));
        List<String> actionSubTitleList = new ArrayList<>();
        actionSubTitleList.add("￥" + "100");
        actionSubTitleList.add(resources.getString(R.string.product_access_etc));
        actionSubTitleList.add(resources.getString(R.string.transaction_record_display));
        actionSubTitleList.add(resources.getString(R.string.real_name_authentication_etc));
        List<Integer> iconList = new ArrayList<>();
        iconList.add(R.drawable.home_wallet);
        iconList.add(R.drawable.home_chart);
        iconList.add(R.drawable.home_customer);
        iconList.add(R.drawable.home_setting);
        viewDelegate.setActionData(iconList, actionTitleList, actionSubTitleList);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.get(R.id.rl_company).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), InformationActivity.class);
                startActivity(intent);
            }
        });

        viewDelegate.setActionOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position){
                    case 0:
                        intent = new Intent(getActivity(), MyWalletActivity.class);
                        break;
                    case 1:
                        intent = new Intent(getActivity(), OperationReportActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });
    }
}
