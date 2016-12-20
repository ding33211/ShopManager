package com.soubu.goldensteward.ui.market;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.bean.server.AllActivityServerParams;
import com.soubu.goldensteward.support.constant.IntentKey;
import com.soubu.goldensteward.support.utils.GlideUtils;
import com.soubu.goldensteward.support.web.mvp.BaseListMvpFragment;

/**
 * Created by dingsigang on 16-12-6.
 */

public class AllActivityMvpFragment extends BaseListMvpFragment<AllActivityPresenter, AllActivityServerParams> {


    @Override
    protected int getEmptyDesc() {
        return R.string.empty_all_activity_desc;
    }

    @Override
    protected int createItemId() {
        return R.layout.item_all_activity_recyclerview;
    }

    @Override
    protected AllActivityPresenter createPresenter() {
        return new AllActivityPresenter();
    }

    @Override
    public void bindData(BaseViewHolder holder, AllActivityServerParams item, int position) {
        View vBottom = holder.getView(R.id.v_bottom_line);
        if (position == refreshHelper.adapter.getItemCount() - 1) {
            vBottom.setVisibility(View.VISIBLE);
        } else {
            vBottom.setVisibility(View.GONE);
        }
        TextView tvActivityName = holder.getView(R.id.tv_activity_name);
        TextView tvStartTime = holder.getView(R.id.tv_start_time);
        TextView tvEndTime = holder.getView(R.id.tv_end_time);
        ImageView ivActivity = holder.getView(R.id.iv_activity);
        View vHaveSignedUp = holder.getView(R.id.iv_have_signed_up);
        tvActivityName.setText(item.getActive_name());
        tvStartTime.setText(item.getStart_time());
        tvEndTime.setText(item.getEnd_time());
        GlideUtils.loadTopRoundedImage(ivActivity.getContext(), ivActivity, item.getIndex_img(), R.drawable.bg_no_activity, R.drawable.bg_no_activity);
        if (item.getSign_up_status() == 1) {
            vHaveSignedUp.setVisibility(View.VISIBLE);
        } else {
            vHaveSignedUp.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(BaseViewHolder holder, AllActivityServerParams item, int position) {
        Intent intent = new Intent(getActivity(), ActivitySpecActivity.class);
        intent.putExtra(IntentKey.EXTRA_ACTIVITY_ID, item.getId());
        intent.putExtra(IntentKey.EXTRA_HAVE_SIGNED_UP, item.getSign_up_status() == 1);
        startActivity(intent);
    }

}
