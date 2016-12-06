package com.soubu.goldensteward.ui.information;

import android.support.v4.app.Fragment;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.support.delegate.TabViewpagerActivityDelegate;
import com.soubu.goldensteward.support.bean.BaseEventBusResp;
import com.soubu.goldensteward.support.bean.EventBusConfig;
import com.soubu.goldensteward.support.bean.server.BaseResp;
import com.soubu.goldensteward.support.bean.server.UserServerParams;
import com.soubu.goldensteward.support.net.RetrofitRequest;
import com.soubu.goldensteward.support.utils.ShowWidgetUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-10-19.
 */
public class InformationActivity extends ActivityPresenter<TabViewpagerActivityDelegate> {

    CompanyInformationFragment mCiFragment;
    PersonalInformationFragment mPiFragment;
    boolean mSucceed = false;

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
            if (mCiFragment.isAddressChanged()) {
                RetrofitRequest.getInstance().changeAddress(mCiFragment.getLocationParams());
            }
        } else {
            super.onBackPressed();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeSuccess(BaseEventBusResp resp) {
        BaseResp resp1 = (BaseResp) resp.getObject();
        int code = resp.getCode();
        if (code == EventBusConfig.CHANGE_USER_INFO || code == EventBusConfig.CHANGE_ADDRESS) {
            //只做一次成功操作，因为可能同时存在改变信息和地址
            if (mSucceed) {
                return;
            } else {
                mSucceed = true;
            }
            ShowWidgetUtil.showShort(resp1.msg);
            mPiFragment.updateToDb();
            mCiFragment.updateToDb();
            super.onBackPressed();
        }
    }
}
