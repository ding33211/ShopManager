package com.soubu.goldensteward.ui.referstore;

import android.content.Intent;

import com.soubu.goldensteward.support.base.BaseRecyclerViewAdapter;
import com.soubu.goldensteward.support.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.support.bean.BaseEventBusResp;
import com.soubu.goldensteward.support.bean.Constant;
import com.soubu.goldensteward.support.bean.EventBusConfig;
import com.soubu.goldensteward.support.bean.server.BaseDataArray;
import com.soubu.goldensteward.support.bean.server.BaseResp;
import com.soubu.goldensteward.support.bean.server.SubAccountServerParams;
import com.soubu.goldensteward.support.server.RetrofitRequest;
import com.soubu.goldensteward.support.utils.PinyinComparator;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by dingsigang on 16-10-18.
 */
public class SubAccountFragment extends FragmentPresenter<SubAccountFragmentDelegate> {
    List<SubAccountServerParams> mList;

    @Override
    protected Class<SubAccountFragmentDelegate> getDelegateClass() {
        return SubAccountFragmentDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        RetrofitRequest.getInstance().getSubAccountList();
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnRvItemSelectListener(new BaseRecyclerViewAdapter.OnRvItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), SubAccountSpecActivity.class);
                intent.putExtra(Constant.EXTRA_SUB_ACCOUNT_ID, mList.get(position).getUser_id());
                startActivity(intent);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetList(BaseEventBusResp resp) {
        BaseResp resp1 = (BaseResp) resp.getObject();
        int code = resp.getCode();
        if (code == EventBusConfig.GET_SUB_ACCOUNT_LIST) {
            SubAccountServerParams[] params = (SubAccountServerParams[]) ((BaseDataArray) resp1.getResult()).getData();
            mList = Arrays.asList(params);
            Collections.sort(mList, new PinyinComparator());
            viewDelegate.setData(mList);
        }
    }


}
