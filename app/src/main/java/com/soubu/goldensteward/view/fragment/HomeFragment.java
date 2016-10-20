package com.soubu.goldensteward.view.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.delegate.HomeFragmentDelegate;
import com.soubu.goldensteward.module.Contant;
import com.soubu.goldensteward.view.activity.InformationActivity;
import com.soubu.goldensteward.view.activity.MyWalletActivity;

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
        List<Integer> viewTypeList = new ArrayList<>();
        viewTypeList.add(Contant.GRID_TYPE_TODAY_VISITOR_NUM);
        viewTypeList.add(Contant.GRID_TYPE_TODAY_PRODUCT_VISIT);
        viewTypeList.add(Contant.GRID_TYPE_TODAY_ORDER_NUM);
        viewTypeList.add(Contant.GRID_TYPE_TODAY_RETURN_RATE);
        viewDelegate.setViewTypeList(viewTypeList);
        List<String> subTitleList = new ArrayList<>();
        subTitleList.add("0");
        subTitleList.add("0");
        subTitleList.add("0");
        subTitleList.add("0");
        viewDelegate.setSubTitleList(subTitleList);

        List<Integer> actionViewTypeList = new ArrayList<>();
        actionViewTypeList.add(Contant.GRID_TYPE_MY_WALLET);
        actionViewTypeList.add(Contant.GRID_TYPE_OPERATION_REPORT);
        actionViewTypeList.add(Contant.GRID_TYPE_MY_CUSTOMER);
        actionViewTypeList.add(Contant.GRID_TYPE_SETTING);
        viewDelegate.setActionViewTypeList(actionViewTypeList);

        List<String> actionSubTitleList = new ArrayList<>();
        actionSubTitleList.add("￥" + "100");
        viewDelegate.setActionSubTitleList(actionSubTitleList);
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

                }
                startActivity(intent);
            }
        });
    }
}
