package com.soubu.goldensteward.view.activity;

import android.support.v4.app.Fragment;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.TabViewpagerActivityDelegate;
import com.soubu.goldensteward.view.fragment.CompanyInformationFragment;
import com.soubu.goldensteward.view.fragment.PersonalInformationFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-10-19.
 */
public class InformationActivity extends ActivityPresenter<TabViewpagerActivityDelegate> {

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
        fragments.add(new CompanyInformationFragment());
        fragments.add(new PersonalInformationFragment());
        String[] titles = new String[]{getString(R.string.company_info), getString(R.string.personal_information)};
        viewDelegate.initFragment(fragments, titles);
    }
}
