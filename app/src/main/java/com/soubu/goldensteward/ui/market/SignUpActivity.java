package com.soubu.goldensteward.ui.market;

import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.base.BaseRecyclerViewAdapter;
import com.soubu.goldensteward.support.bean.BaseEventBusResp;
import com.soubu.goldensteward.support.bean.EventBusConfig;
import com.soubu.goldensteward.support.bean.server.BaseResp;
import com.soubu.goldensteward.support.bean.server.ProductInOrderListServerParams;
import com.soubu.goldensteward.support.bean.server.WithCountDataArray;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.support.net.RetrofitRequest;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dingsigang on 16-12-6.
 */

public class SignUpActivity extends ActivityPresenter<SignUpActivityDelegate> {
    List<Integer> mCheckedIndex;


    @Override
    protected Class<SignUpActivityDelegate> getDelegateClass() {
        return SignUpActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.activity_sign_up);
    }

    @Override
    protected void initData() {
        super.initData();
        mCheckedIndex = new ArrayList<>();
        RetrofitRequest.getInstance().getProductListOnSale();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getDataSuccess(BaseEventBusResp resp) {
        BaseResp resp1 = (BaseResp) resp.getObject();
        int code = resp.getCode();
        switch (code) {
            case EventBusConfig.GET_PRODUCT_LIST_ON_SALE:
                WithCountDataArray result2 = (WithCountDataArray) resp1.getResult();
                List<ProductInOrderListServerParams> list = Arrays.asList((ProductInOrderListServerParams[]) result2.getData());
                viewDelegate.initProduct(list);
                break;
        }
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnProductItemClickListener(new BaseRecyclerViewAdapter.OnRvItemClickListener() {
            @Override
            public void onClick(int position) {
                if (!mCheckedIndex.contains(position)) {
                    mCheckedIndex.add(position);
                } else {
                    mCheckedIndex.remove((Integer) position);
                }
                viewDelegate.refreshTotalText(mCheckedIndex.size());
            }
        });
        viewDelegate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_commit:
                        viewDelegate.onCommitSuccess();
                        break;
                    case R.id.bt_return:
                        setResult(RESULT_OK);
                        finish();
                        break;
                }
            }
        }, R.id.tv_commit, R.id.bt_return);
    }
}
