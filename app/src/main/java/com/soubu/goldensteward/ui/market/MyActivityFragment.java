package com.soubu.goldensteward.ui.market;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.adapter.SingleAdapter;
import com.soubu.goldensteward.support.base.BaseApplication;
import com.soubu.goldensteward.support.bean.server.MyActivityServerParams;
import com.soubu.goldensteward.support.constant.IntentKey;
import com.soubu.goldensteward.support.delegate.RecyclerViewFragmentDelegate;
import com.soubu.goldensteward.support.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.support.bean.server.BasePageRequest;
import com.soubu.goldensteward.support.web.core.BaseResponse;
import com.soubu.goldensteward.support.web.core.BaseSubscriber;

import java.util.List;

/**
 * Created by dingsigang on 16-12-6.
 */

public class MyActivityFragment extends FragmentPresenter<RecyclerViewFragmentDelegate> {

    @Override
    protected Class<RecyclerViewFragmentDelegate> getDelegateClass() {
        return RecyclerViewFragmentDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        SingleAdapter adapter = new SingleAdapter<MyActivityServerParams>(getActivity(), R.layout.item_my_activity_recyclerview) {
            @Override
            protected void bindData(BaseViewHolder holder, MyActivityServerParams item, int position) {
                TextView tvActivityName = holder.getView(R.id.tv_activity_name);
                TextView tvSignUpEndTime = holder.getView(R.id.tv_sign_up_end_time);
                TextView tvStartTime = holder.getView(R.id.tv_activity_start_time);
                TextView tvEndTime = holder.getView(R.id.tv_activity_end_time);
                TextView tvStatus = holder.getView(R.id.tv_status);
                tvActivityName.setText(item.getActive_name());
                tvSignUpEndTime.setText(item.getSign_up_end_time());
                tvStartTime.setText(item.getActive_start_time());
                tvEndTime.setText(item.getActive_end_time());

            }

            @Override
            protected void bindListener(BaseViewHolder holder, MyActivityServerParams item, int position) {
                TextView tvWatchSpec = holder.getView(R.id.tv_watch_spec);
                tvWatchSpec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), SignUpSpecActivity.class);
                        intent.putExtra(IntentKey.EXTRA_ACTIVITY_ID, item.getId());
                        startActivity(intent);
                    }
                });
            }
        };
        viewDelegate.setAdapter(adapter);
        viewDelegate.setDecorationHeight(10);
        BaseApplication.getWebModel()
                .getMyActivity(new BasePageRequest(1))
                .sendTo(new BaseSubscriber<BaseResponse<List<MyActivityServerParams>>>(this) {
                    @Override
                    public void onSuccess(BaseResponse<List<MyActivityServerParams>> response) {
                        viewDelegate.setData(response.getResult().getData());
                    }
                });
    }

}
