package com.soubu.goldensteward.ui.market;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.bean.server.MyActivityServerParams;
import com.soubu.goldensteward.support.constant.IntentKey;
import com.soubu.goldensteward.support.utils.ConvertUtil;
import com.soubu.goldensteward.support.utils.LogUtil;
import com.soubu.goldensteward.support.web.mvp.BaseListMvpFragment;
import com.soubu.goldensteward.support.widget.recyclerviewdecoration.DividerItemDecoration;

/**
 * 作者：余天然 on 2016/12/14 下午6:04
 */
public class MyActivityMvpFragment extends BaseListMvpFragment<MyActivityPresenter, MyActivityServerParams> {

    @Override
    protected int getEmptyDesc() {
        return R.string.empty_my_activity_desc;
    }

    @Override
    protected int createItemId() {
        return R.layout.item_my_activity_recyclerview;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        setRecyclerViewDivider(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, ConvertUtil.dip2px(getActivity(), 10)));
    }

    @Override
    protected MyActivityPresenter createPresenter() {
        return new MyActivityPresenter();
    }

    @Override
    public void bindData(BaseViewHolder holder, MyActivityServerParams item, int position) {
        TextView tvActivityName = holder.getView(R.id.tv_activity_name);
        TextView tvSignUpEndTime = holder.getView(R.id.tv_sign_up_end_time);
        TextView tvStartTime = holder.getView(R.id.tv_activity_start_time);
        TextView tvEndTime = holder.getView(R.id.tv_activity_end_time);
        TextView tvStatus = holder.getView(R.id.tv_status);
        tvActivityName.setText(item.getActive_name());
        tvSignUpEndTime.setText(item.getSign_up_end_time());
        tvStartTime.setText(item.getActive_start_time());
        tvEndTime.setText(item.getActive_end_time());
        if (item.getStatus() == 1) {
            tvStatus.setText(R.string.appealing_quotes);
            tvStatus.setTextColor(getResources().getColor(R.color.search_text_color));
        } else if (item.getStatus() == 2) {
            tvStatus.setText(R.string.appeal_success);
            tvStatus.setTextColor(getResources().getColor(R.color.green_sign_up_product));
        } else {
            tvStatus.setText(R.string.appeal_fail);
            tvStatus.setTextColor(getResources().getColor(R.color.red_sign_up_product));
        }
    }

    @Override
    public void bindListener(BaseViewHolder holder, MyActivityServerParams item, int position) {
        TextView tvWatchSpec = holder.getView(R.id.tv_watch_spec);
        tvWatchSpec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.print("position=" + position);
                Intent intent = new Intent(getActivity(), SignUpSpecActivity.class);
                intent.putExtra(IntentKey.EXTRA_ACTIVITY_ID, item.getId());
                startActivity(intent);
            }
        });
    }

}
