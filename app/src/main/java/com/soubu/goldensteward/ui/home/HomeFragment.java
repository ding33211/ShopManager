package com.soubu.goldensteward.ui.home;

import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;

import com.soubu.goldensteward.support.base.GoldenStewardApplication;
import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.greendao.DBHelper;
import com.soubu.goldensteward.support.greendao.User;
import com.soubu.goldensteward.support.greendao.UserDao;
import com.soubu.goldensteward.support.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.support.bean.BaseEventBusResp;
import com.soubu.goldensteward.support.bean.EventBusConfig;
import com.soubu.goldensteward.support.bean.server.BaseDataObject;
import com.soubu.goldensteward.support.bean.server.BaseResp;
import com.soubu.goldensteward.support.bean.server.HomeInfoServerParams;
import com.soubu.goldensteward.support.net.RetrofitRequest;
import com.soubu.goldensteward.ui.information.InformationActivity;
import com.soubu.goldensteward.ui.message.MessageActivity;
import com.soubu.goldensteward.ui.customer.MyCustomersActivity;
import com.soubu.goldensteward.ui.wallet.MyWalletActivity;
import com.soubu.goldensteward.ui.report.NewOperationReportActivity;
import com.soubu.goldensteward.ui.setting.SettingActivity;
import com.soubu.goldensteward.ui.storepreview.StorePreviewActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-10-18.
 */
public class HomeFragment extends FragmentPresenter<HomeFragmentDelegate> implements View.OnClickListener {

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

    private void initTopBar() {
        UserDao dao = DBHelper.getInstance(getActivity()).getUserDao();
        List<User> list = dao.queryBuilder().where(UserDao.Properties.Phone.eq(GoldenStewardApplication.getContext().getPhone())).list();
        if (list.size() > 0) {
            String name = list.get(0).getName();
            String url = list.get(0).getPortrait();
            viewDelegate.refreshCompany(name, url);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onServerSuccess(BaseEventBusResp resp) {
        BaseResp resp1 = (BaseResp) resp.getObject();
        int code = resp.getCode();
        if (code == EventBusConfig.GET_HOME_INFO) {
            BaseDataObject dataObject = (BaseDataObject) resp1.getResult();
            if (dataObject.getData() instanceof HomeInfoServerParams) {
                HomeInfoServerParams params = (HomeInfoServerParams) dataObject.getData();
                initHomeInfo(params);
            }
        }
        if (code == EventBusConfig.CHANGE_USER_INFO || code == EventBusConfig.CHANGE_ADDRESS) {
            initTopBar();
        }
    }

    private void initHomeInfo(HomeInfoServerParams params) {
        viewDelegate.refreshMoney(params.getMoney());
        List<String> titleList = new ArrayList<>();
        Resources resources = getActivity().getResources();
        titleList.add(resources.getString(R.string.today_visitor_num));
        titleList.add(resources.getString(R.string.today_product_visit));
        titleList.add(resources.getString(R.string.today_order_num));
        titleList.add(resources.getString(R.string.last_month_return_rate));
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
        viewDelegate.setOnClickListener(this, R.id.rl_company, R.id.ll_menu_r_1, R.id.ll_menu_r_2);
        viewDelegate.setActionOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position) {
                    case 0:
                        intent = new Intent(getActivity(), MyWalletActivity.class);
                        break;
                    case 1:
                        intent = new Intent(getActivity(), NewOperationReportActivity.class);
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

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.rl_company:
                intent = new Intent(getActivity(), InformationActivity.class);
                break;
            case R.id.ll_menu_r_2:
                intent = new Intent(getActivity(), StorePreviewActivity.class);
                break;
            case R.id.ll_menu_r_1:
                intent = new Intent(getActivity(), MessageActivity.class);
                break;
        }
        if (null != intent) {
            startActivity(intent);
        }

    }
}
