package com.soubu.goldensteward.delegate;

import android.widget.AdapterView;
import android.widget.GridView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.adapter.CustomGridViewAdapter;

import java.util.List;

/**
 * Created by dingsigang on 16-10-18.
 */
public class HomeFragmentDelegate extends BaseFragmentDelegate {
    CustomGridViewAdapter mTodayAdapter;
    CustomGridViewAdapter mActionAdapter;
    GridView mTodayGridView;
    GridView mActionGridView;


    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mTodayGridView = get(R.id.gv_today_data);
        mTodayAdapter = new CustomGridViewAdapter(getActivity());
        mTodayGridView.setAdapter(mTodayAdapter);
        mActionAdapter = new CustomGridViewAdapter(getActivity());
        mActionGridView = get(R.id.gv_other_action);
        mActionGridView.setAdapter(mActionAdapter);
    }

    public void setTodayData(List<Integer> iconList, List<String> titleList, List<String> subTitleList){
        mTodayAdapter.setData(iconList, titleList, subTitleList);
    }



    public void setActionData(List<Integer> iconList, List<String> titleList, List<String> subTitleList){
        mActionAdapter.setData(iconList, titleList, subTitleList);
    }


    public void setTodayOnItemClickListener(AdapterView.OnItemClickListener listener){
        mTodayGridView.setOnItemClickListener(listener);
    }

    public void setActionOnItemClickListener(AdapterView.OnItemClickListener listener){
        mActionGridView.setOnItemClickListener(listener);
    }
}
