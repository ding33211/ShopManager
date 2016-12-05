package com.soubu.goldensteward.base.delegate;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;

import static android.R.attr.fragment;

/**
 * Created by dingsigang on 16-10-20.
 */
public class FragmentActivityDelegate extends AppDelegate {
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

    public void replaceFragment(Fragment fragment) {
        mFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    public void addFragment(Fragment fragment){
        mFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
    }

    public void addFragment(Fragment fragment, String tag) {
        mFragmentManager.beginTransaction().add(R.id.fragment_container, fragment, tag).commit();
    }

    public void showFragment(String hideTag, String showTag, Fragment fragment) {
        Fragment hideFragment = mFragmentManager.findFragmentByTag(hideTag);
        Fragment showFragment = mFragmentManager.findFragmentByTag(showTag);
        if(hideFragment != null && !hideFragment.isHidden()){
            mFragmentManager.beginTransaction().hide(hideFragment).commit();
        }
        if(showFragment != null && showFragment.isHidden()){
            mFragmentManager.beginTransaction().show(showFragment).commit();
        } else if(showFragment == null){
            addFragment(fragment, showTag);
        }
    }

}
