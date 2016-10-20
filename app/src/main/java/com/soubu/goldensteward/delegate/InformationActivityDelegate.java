package com.soubu.goldensteward.delegate;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;
import com.soubu.goldensteward.view.fragment.CompanyInformationFragment;
import com.soubu.goldensteward.view.fragment.PersonalInformationFragment;
import com.soubu.goldensteward.widget.TitleFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-10-19.
 */
public class InformationActivityDelegate extends AppDelegate{

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_information;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ViewPager viewPager = get(R.id.vp_content);
        TabLayout tabLayout = get(R.id.tl_title);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new CompanyInformationFragment());
        fragments.add(new PersonalInformationFragment());

        TitleFragmentPagerAdapter adapter = new TitleFragmentPagerAdapter(getActivity().getSupportFragmentManager(),
                fragments, new String[]{getActivity().getString(R.string.company_information), getActivity().getString(R.string.personal_information)});
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
