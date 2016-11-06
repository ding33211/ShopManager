package com.soubu.goldensteward.view.activity;

import android.support.v4.app.Fragment;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.TabViewpagerActivityDelegate;
import com.soubu.goldensteward.module.server.BaseResp;
import com.soubu.goldensteward.module.server.UserServerParams;
import com.soubu.goldensteward.server.RetrofitRequest;
import com.soubu.goldensteward.utils.ShowWidgetUtil;
import com.soubu.goldensteward.view.fragment.CompanyInformationFragment;
import com.soubu.goldensteward.view.fragment.PersonalInformationFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.baidu.location.f.mC;
import static com.baidu.location.h.j.m;

/**
 * Created by dingsigang on 16-10-19.
 */
public class InformationActivity extends ActivityPresenter<TabViewpagerActivityDelegate> {

    CompanyInformationFragment mCiFragment;
    PersonalInformationFragment mPiFragment;
    boolean mSuccessed = false;

    @Override
    protected Class<TabViewpagerActivityDelegate> getDelegateClass() {
        return TabViewpagerActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.information);
    }

    @Override
    protected void initData() {
        super.initData();
        List<Fragment> fragments = new ArrayList<>();
        mCiFragment = new CompanyInformationFragment();
        mPiFragment = new PersonalInformationFragment();
        fragments.add(mCiFragment);
        fragments.add(mPiFragment);
        String[] titles = new String[]{getString(R.string.company_info), getString(R.string.personal_information)};
        viewDelegate.initFragment(fragments, titles);
    }

    @Override
    public void onBackPressed() {
        if (mCiFragment.isChanged() || mPiFragment.isChanged()) {
            UserServerParams params = new UserServerParams();
            params.deltaCopy(mCiFragment.getParams());
            params.deltaCopy(mPiFragment.getParams());
            RetrofitRequest.getInstance().changeUserInfo(params);
            if(mCiFragment.isAddressChanged()){
                RetrofitRequest.getInstance().changeAddress(mCiFragment.getLocationParams());
            }
        }  else {
            super.onBackPressed();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeSuccess(BaseResp resp) {
        if (resp.getResult() instanceof UserServerParams) {
            //只做一次成功操作，因为可能同时存在改变信息和地址
            if(mSuccessed){
                return;
            } else {
                mSuccessed = true;
            }
            ShowWidgetUtil.showShort(resp.msg);
            mPiFragment.updateToDb();
            mCiFragment.updateToDb();
            super.onBackPressed();
        }
    }
}
