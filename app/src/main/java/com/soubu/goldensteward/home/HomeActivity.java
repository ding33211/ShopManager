package com.soubu.goldensteward.home;


import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.home.HomeActivityDelegate;

/**
 * Created by dingsigang on 16-10-17.
 */
public class HomeActivity extends ActivityPresenter<HomeActivityDelegate> {
    //点击tab的index
    private int mIndex;
    //当前的tab的index
    private int mCurrentTabIndex;

    @Override
    protected Class getDelegateClass() {
        return HomeActivityDelegate.class;
    }

    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_home:
                mIndex = 0;
                break;
            case R.id.btn_sub_account:
                mIndex = 1;
                break;
            case R.id.btn_marketing:
                mIndex = 2;
                break;
        }
        if(mCurrentTabIndex != mIndex){
            viewDelegate.showFragmentAndSelectTab(mIndex, mCurrentTabIndex);
            mCurrentTabIndex = mIndex;
        }
    }

    @Override
    public boolean keyDownTwiceFinish() {
        return true;
    }
}
