package com.soubu.goldensteward.view.activity;

import android.content.Intent;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.adapter.BaseRecyclerViewAdapter;
import com.soubu.goldensteward.adapter.CustomGridViewAdapter;
import com.soubu.goldensteward.adapter.OperationReportRvAdapter;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.RecyclerViewActivityDelegate;
import com.soubu.goldensteward.module.Constant;
import com.soubu.goldensteward.module.OperationReportRvItem;

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
        mList = new ArrayList<>();
        mList.add(createItem(getString(R.string.turnover_volume),
                new String[]{getString(R.string.account_balance), getString(R.string.accumulated_income)},
                new String[]{"50", "0"}));
        mList.add(createItem(getString(R.string.store_visitor),
                new String[]{getString(R.string.accumulated_visitor), getString(R.string.today_visitor), getString(R.string.last_week_visitor), getString(R.string.last_month_visitor)},
                new String[]{"50", "0", "50", "0"}));
        mList.add(createItem(getString(R.string.product_access),
                new String[]{getString(R.string.accumulated_product_access), getString(R.string.today_product_access), getString(R.string.last_week_product_access), getString(R.string.last_month_product_access)},
                new String[]{"50", "0", "50", "0"}));
        mList.add(createItem(getString(R.string.return_rate),
                new String[]{getString(R.string.accumulated_return_rate), getString(R.string.today_return_rate), getString(R.string.last_week_return_rate), getString(R.string.last_month_return_rate)},
                new String[]{"50", "0", "50", "0"}));
        mList.add(createItem(getString(R.string.more_data),
                new String[]{getString(R.string.last_week_offer_num), getString(R.string.last_week_collect_num)},
                new String[]{"50", "0"}));
        viewDelegate.setData(mList);
    }

    private OperationReportRvItem createItem(String label, String[] title, String[] subTitle){
        OperationReportRvItem item = new OperationReportRvItem();
        item.setLabel(label);
        List<String> titleList = Arrays.asList(title);
        List<String> contentList = Arrays.asList(subTitle);
        CustomGridViewAdapter adapter = new CustomGridViewAdapter(getApplicationContext(), null, titleList, contentList);
        item.setAdapter(adapter);
        return item;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setAdapter(new OperationReportRvAdapter());
        viewDelegate.setRvItemOnClickListener(new BaseRecyclerViewAdapter.OnRvItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(OperationReportActivity.this, OperationReportSpecActivity.class);
                switch (position){
                    case 0:
                        intent.putExtra(Constant.EXTRA_TYPE, OperationReportSpecActivity.TYPE_TURNOVER);
                        break;
                }
                startActivity(intent);
            }
        });
    }
}
