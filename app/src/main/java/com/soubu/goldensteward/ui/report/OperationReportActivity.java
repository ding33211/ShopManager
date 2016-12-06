package com.soubu.goldensteward.ui.report;

import android.content.Intent;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.base.BaseRecyclerViewAdapter;
import com.soubu.goldensteward.ui.home.HomeGridViewAdapter;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.support.delegate.RecyclerViewActivityDelegate;
import com.soubu.goldensteward.support.bean.BaseEventBusResp;
import com.soubu.goldensteward.support.bean.Constant;
import com.soubu.goldensteward.support.bean.EventBusConfig;
import com.soubu.goldensteward.support.bean.OperationReportRvItem;
import com.soubu.goldensteward.support.bean.server.BaseDataObject;
import com.soubu.goldensteward.support.bean.server.BaseResp;
import com.soubu.goldensteward.support.bean.server.OperationReportServerParams;
import com.soubu.goldensteward.support.net.RetrofitRequest;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dingsigang on 16-10-21.
 */
public class OperationReportActivity extends ActivityPresenter<RecyclerViewActivityDelegate> {
    List<OperationReportRvItem> mList;

    @Override
    protected Class<RecyclerViewActivityDelegate> getDelegateClass() {
        return RecyclerViewActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.operation_report);
    }

    @Override
    protected void initData() {
        super.initData();
        RetrofitRequest.getInstance().getOperationReport();
        initReportData(new OperationReportServerParams());
    }

    private void initReportData(OperationReportServerParams params) {
        mList = new ArrayList<>();
        mList.add(createItem(getString(R.string.turnover_volume),
                new String[]{getString(R.string.account_balance), getString(R.string.accumulated_income)},
                new String[]{params.getBalance(), params.getIncome()}));
        mList.add(createItem(getString(R.string.store_visitor),
                new String[]{getString(R.string.accumulated_visitor), getString(R.string.today_visitor), getString(R.string.last_week_visitor), getString(R.string.last_month_visitor)},
                new String[]{params.getShop_visit(), params.getToday_shop_visit(), params.getWeek_shop_visit(), params.getMonth_shop_visit()}));
        mList.add(createItem(getString(R.string.product_access),
                new String[]{getString(R.string.accumulated_product_access), getString(R.string.today_product_access), getString(R.string.last_week_product_access), getString(R.string.last_month_product_access)},
                new String[]{params.getProduct_visit(), params.getToday_product_visit(), params.getWeek_product_visit(), params.getMonth_product_visit()}));
        mList.add(createItem(getString(R.string.return_rate),
                new String[]{getString(R.string.accumulated_return_rate), getString(R.string.last_month_return_rate)},
                new String[]{params.getRefunds(), params.getMonth_refunds()}));
        OperationReportRvItem item = createItem(getString(R.string.more_data),
                new String[]{getString(R.string.last_week_offer_num), getString(R.string.last_week_collect_num)},
                new String[]{params.getWeek_offer(), params.getCollection()});
        item.setClickable(false);
        mList.add(item);
        viewDelegate.setData(mList);
    }

    private OperationReportRvItem createItem(String label, String[] title, String[] subTitle) {
        OperationReportRvItem item = new OperationReportRvItem();
        item.setLabel(label);
        List<String> titleList = Arrays.asList(title);
        List<String> contentList = Arrays.asList(subTitle);
        HomeGridViewAdapter adapter = new HomeGridViewAdapter(getApplicationContext(), null, titleList, contentList);
        item.setAdapter(adapter);
        return item;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getReportSuccess(BaseEventBusResp resp) {
        BaseResp resp1 = (BaseResp) resp.getObject();
        int code = resp.getCode();
        if (code == EventBusConfig.GET_OPERATION_REPORT) {
            OperationReportServerParams params = (OperationReportServerParams) ((BaseDataObject) resp1.getResult()).getData();
            initReportData(params);
        }
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setAdapter(new OperationReportRvAdapter());
        viewDelegate.setRvItemOnClickListener(new BaseRecyclerViewAdapter.OnRvItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(OperationReportActivity.this, OperationReportSpecActivity.class);
                switch (position) {
                    case 0:
                        intent.putExtra(Constant.EXTRA_TYPE, OperationReportSpecActivity.TYPE_TURNOVER);
                        break;
                    case 1:
                        intent.putExtra(Constant.EXTRA_TYPE, OperationReportSpecActivity.TYPE_STORE_VISITOR);
                        break;
                    case 2:
                        intent.putExtra(Constant.EXTRA_TYPE, OperationReportSpecActivity.TYPE_PRODUCT_ACCESS);
                        break;
                    case 3:
                        intent.putExtra(Constant.EXTRA_TYPE, OperationReportSpecActivity.TYPE_REFUND_RATE);
                        break;
                }
                startActivity(intent);
            }
        });
    }
}
