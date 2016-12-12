package com.soubu.goldensteward.ui.market;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.adapter.SingleAdapter;
import com.soubu.goldensteward.support.bean.server.AllActivityServerParams;
import com.soubu.goldensteward.support.delegate.RecyclerViewFragmentDelegate;
import com.soubu.goldensteward.support.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.support.utils.ConvertUtil;
import com.soubu.goldensteward.support.utils.GlideUtils;

import java.util.ArrayList;
import java.util.Date;
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
                tvActivityName.setText(item.getName());
                tvStartTime.setText(ConvertUtil.dateToYYYY_MM_DD_HH_mm(new Date(Long.valueOf(item.getBegin_time()) * 1000)));
                tvEndTime.setText(ConvertUtil.dateToYYYY_MM_DD_HH_mm(new Date(Long.valueOf(item.getEnd_time()) * 1000)));
                GlideUtils.loadTopRoundedImage(ivActivity.getContext(), ivActivity, item.getUrl(), R.drawable.preview_bg, R.drawable.preview_bg);

            }

            @Override
            public void onItemClick(BaseViewHolder holder, AllActivityServerParams item, int position) {
                Intent intent = new Intent(getActivity(), ActivitySpecActivity.class);
                startActivity(intent);
            }
        };
        viewDelegate.setAdapter(adapter);
        List<AllActivityServerParams> list = new ArrayList<>();
        AllActivityServerParams params = new AllActivityServerParams();
        params.setUrl("https://www.baidu.com/img/bd_logo1.png");
        params.setName("baidu");
        params.setBegin_time(System.currentTimeMillis() / 1000 + "");
        params.setEnd_time(System.currentTimeMillis() / 1000 + 3600000 + "");
        list.add(params);
        params = new AllActivityServerParams();
        params.setUrl("https://gss0.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/lbs/pic/item/7dd98d1001e939010f5ffd0672ec54e736d196ac.jpg");
        params.setName("baidu");
        params.setBegin_time(System.currentTimeMillis() / 1000 + "");
        params.setEnd_time(System.currentTimeMillis() / 1000 + 3600000 + "");
        list.add(params);
        params = new AllActivityServerParams();
        params.setUrl("https://www.baidu.com/img/bd_logo1.png");
        params.setName("baidu");
        params.setBegin_time(System.currentTimeMillis() / 1000 + "");
        params.setEnd_time(System.currentTimeMillis() / 1000 + 3600000 + "");
        list.add(params);
        params = new AllActivityServerParams();
        params.setUrl("https://gss0.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/lbs/pic/item/7dd98d1001e939010f5ffd0672ec54e736d196ac.jpg");
        params.setName("baidu");
        params.setBegin_time(System.currentTimeMillis() / 1000 + "");
        params.setEnd_time(System.currentTimeMillis() / 1000 + 3600000 + "");
        list.add(params);
        params = new AllActivityServerParams();
        params.setUrl("https://www.baidu.com/img/bd_logo1.png");
        params.setName("baidu");
        params.setBegin_time(System.currentTimeMillis() / 1000 + "");
        params.setEnd_time(System.currentTimeMillis() / 1000 + 3600000 + "");
        list.add(params);
        params = new AllActivityServerParams();
        params.setUrl("https://www.baidu.com/img/bd_logo1.png");
        params.setName("baidu");
        params.setBegin_time(System.currentTimeMillis() / 1000 + "");
        params.setEnd_time(System.currentTimeMillis() / 1000 + 3600000 + "");
        list.add(params);
        viewDelegate.setData(list);
    }

}
