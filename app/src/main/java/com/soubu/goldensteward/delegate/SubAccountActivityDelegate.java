package com.soubu.goldensteward.delegate;

import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.adapter.CustomGridViewAdapter;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;

import java.util.List;

/**
 * Created by lakers on 16/10/31.
 */

public class SubAccountActivityDelegate extends AppDelegate {

    CustomGridViewAdapter mAdapter;
    @Override
    public int getRootLayoutId() {
        return R.layout.activity_sub_account_spec;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mAdapter = new CustomGridViewAdapter(this.getActivity());
        ((GridView)get(R.id.gv_container)).setAdapter(mAdapter);

    }

    public void setTodayData(List<Integer> iconList, List<String> titleList, List<String> subTitleList){
        mAdapter.setData(iconList, titleList, subTitleList);
    }

    public void setLoginList(List<String> list){
        ((ListView)get(R.id.lv_content)).setAdapter(new ArrayAdapter(this.getActivity(), android.R.layout.simple_list_item_1, list));
    }
}
