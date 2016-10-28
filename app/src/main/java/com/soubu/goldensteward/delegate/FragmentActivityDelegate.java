package com.soubu.goldensteward.delegate;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;

/**
 * Created by dingsigang on 16-10-20.
 */
public class FragmentActivityDelegate extends AppDelegate{
    FragmentManager mFragmentManager;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_fragment;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mFragmentManager = getActivity().getSupportFragmentManager();
    }

    public void replaceFragment(Fragment fragment){
        mFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    public void addFragment(Fragment fragment){
        if(fragment.isAdded()){
            mFragmentManager.beginTransaction().show(fragment).commit();
        } else {
            mFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).show(fragment).commit();
        }
    }
}
