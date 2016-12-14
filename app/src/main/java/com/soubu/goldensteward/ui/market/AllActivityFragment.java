package com.soubu.goldensteward.ui.market;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.adapter.SingleAdapter;
import com.soubu.goldensteward.support.base.BaseApplication;
import com.soubu.goldensteward.support.bean.server.AllActivityServerParams;
import com.soubu.goldensteward.support.constant.IntentKey;
import com.soubu.goldensteward.support.delegate.RecyclerViewFragmentDelegate;
import com.soubu.goldensteward.support.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.support.utils.GlideUtils;
import com.soubu.goldensteward.support.web.BasePageRequest;
import com.soubu.goldensteward.support.web.core.BaseResponse;
import com.soubu.goldensteward.support.web.core.BaseSubscriber;

import java.util.List;

/**
 * Created by dingsigang on 16-12-6.
 */

public class AllActivityFragment extends FragmentPresenter<RecyclerViewFragmentDelegate> {
    @Override
    protected Class<RecyclerViewFragmentDelegate> getDelegateClass() {
        return RecyclerViewFragmentDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        SingleAdapter adapter = new SingleAdapter<AllActivityServerParams>(getActivity(), R.layout.item_all_activity_recyclerview) {
            @Override
            protected void bindData(BaseViewHolder holder, AllActivityServerParams item, int position) {
                View vBottom = holder.getView(R.id.v_bottom_line);
                if (position == getItemCount() - 1) {
                    vBottom.setVisibility(View.VISIBLE);
                } else {
                    vBottom.setVisibility(View.GONE);
                }
                TextView tvActivityName = holder.getView(R.id.tv_activity_name);
                TextView tvStartTime = holder.getView(R.id.tv_start_time);
                TextView tvEndTime = holder.getView(R.id.tv_end_time);
                ImageView ivActivity = holder.getView(R.id.iv_activity);
                tvActivityName.setText(item.getActive_name());
                tvStartTime.setText(item.getStart_time());
                tvEndTime.setText(item.getEnd_time());
                GlideUtils.loadTopRoundedImage(ivActivity.getContext(), ivActivity, item.getIndex_img(), R.drawable.bg_no_activity, R.drawable.bg_no_activity);

            }

            @Override
            public void onItemClick(BaseViewHolder holder, AllActivityServerParams item, int position) {
                Intent intent = new Intent(getActivity(), ActivitySpecActivity.class);
                intent.putExtra(IntentKey.EXTRA_ACTIVITY_ID, item.getId());
                startActivity(intent);
            }
        };
        viewDelegate.setAdapter(adapter);


        BaseApplication.getWebModel().getAllActivity(new BasePageRequest(1)).sendTo(new BaseSubscriber<BaseResponse<List<AllActivityServerParams>>>(this) {
            @Override
            public void onSuccess(BaseResponse<List<AllActivityServerParams>> response) {
                initAllActivity(response);
            }
        });
    }


    public void initAllActivity(BaseResponse<List<AllActivityServerParams>> response) {
        viewDelegate.setData(response.getResult().getData());
    }

}
