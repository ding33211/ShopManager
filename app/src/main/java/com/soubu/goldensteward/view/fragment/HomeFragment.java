package com.soubu.goldensteward.view.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;

import com.soubu.goldensteward.GoldenStewardApplication;
import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.greendao.DBHelper;
import com.soubu.goldensteward.base.greendao.User;
import com.soubu.goldensteward.base.greendao.UserDao;
import com.soubu.goldensteward.base.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.delegate.HomeFragmentDelegate;
import com.soubu.goldensteward.module.Constant;
import com.soubu.goldensteward.module.server.BaseDataObject;
import com.soubu.goldensteward.module.server.BaseResp;
import com.soubu.goldensteward.module.server.HomeInfoServerParams;
import com.soubu.goldensteward.module.server.UserServerParams;
import com.soubu.goldensteward.server.RetrofitRequest;
import com.soubu.goldensteward.view.activity.InformationActivity;
import com.soubu.goldensteward.view.activity.MyCustomersActivity;
import com.soubu.goldensteward.view.activity.MyWalletActivity;
import com.soubu.goldensteward.view.activity.OperationReportActivity;
import com.soubu.goldensteward.view.activity.SettingActivity;
import com.soubu.goldensteward.widget.PayPassWordView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        initTopBar();
        RetrofitRequest.getInstance().getHomeInfo();
    }


    private void initTopBar(){
        UserDao dao = DBHelper.getInstance(getActivity()).getUserDao();
        List<User> list = dao.queryBuilder().where(UserDao.Properties.Phone.eq(GoldenStewardApplication.getContext().getPhone())).list();
        if(list.size() > 0){
            String name = list.get(0).getName();
            String url = list.get(0).getPortrait();
            viewDelegate.refreshCompany(name, url);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onServerSuccess(BaseResp resp){
        if(resp.getResult() instanceof BaseDataObject){
            BaseDataObject dataObject = (BaseDataObject) resp.getResult();
            if(dataObject.getData() instanceof HomeInfoServerParams){
                HomeInfoServerParams params = (HomeInfoServerParams) dataObject.getData();
                initHomeInfo(params);
            }
        }
    }

    private void initHomeInfo(HomeInfoServerParams params){
        viewDelegate.refreshMoney(params.getMoney());
        List<String> titleList = new ArrayList<>();
        Resources resources = getActivity().getResources();
        titleList.add(resources.getString(R.string.today_visitor_num));
        titleList.add(resources.getString(R.string.today_product_visit));
        titleList.add(resources.getString(R.string.today_order_num));
        titleList.add(resources.getString(R.string.today_return_rate));
        List<String> subTitleList = new ArrayList<>();
        subTitleList.add(params.getVisit());
        subTitleList.add(params.getProduct_visit());
        subTitleList.add(params.getOrder_count());
        subTitleList.add(params.getRefunds());
        viewDelegate.setTodayData(null, titleList, subTitleList);
        List<String> actionTitleList = new ArrayList<>();
        actionTitleList.add(resources.getString(R.string.my_wallet));
        actionTitleList.add(resources.getString(R.string.operation_report));
        actionTitleList.add(resources.getString(R.string.my_customer));
        actionTitleList.add(resources.getString(R.string.setting));
        List<String> actionSubTitleList = new ArrayList<>();
        actionSubTitleList.add("￥" + params.getMy_wallet());
        actionSubTitleList.add(resources.getString(R.string.product_access_etc));
        actionSubTitleList.add(resources.getString(R.string.transaction_record_display));
        actionSubTitleList.add(resources.getString(R.string.real_name_authentication_etc));
        List<Integer> iconList = new ArrayList<>();
        iconList.add(R.drawable.home_wallet);
        iconList.add(R.drawable.home_chart);
        iconList.add(R.drawable.home_customer);
        iconList.add(R.drawable.home_setting);
        viewDelegate.setActionData(iconList, actionTitleList, actionSubTitleList);
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
                        break;
                    case 1:
                        intent = new Intent(getActivity(), OperationReportActivity.class);
                        break;
                    case 2:
                        intent = new Intent(getActivity(), MyCustomersActivity.class);
                        break;
                    case 3:
                        intent = new Intent(getActivity(), SettingActivity.class);
                }
                startActivity(intent);
            }
        });
    }
}
