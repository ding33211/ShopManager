package com.soubu.goldensteward.ui.report;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.adapter.FooterSingleAdapter;
import com.soubu.goldensteward.support.bean.TurnOverOrderRvItem;
import com.soubu.goldensteward.support.bean.server.OrderServerParams;
import com.soubu.goldensteward.support.mvp.view.AppDelegate;
import com.soubu.goldensteward.support.utils.ConvertUtil;
import com.soubu.goldensteward.support.utils.GlideUtils;
import com.soubu.goldensteward.support.utils.RegularUtil;
import com.soubu.goldensteward.support.widget.linebarchart.LineView;
import com.soubu.goldensteward.support.widget.linebarchart.YAxisView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dingsigang on 16-11-30.
 */

public class RefundDataActivityDelegate extends AppDelegate {
    LineView mLineView;
    YAxisView mLeftAxisView;
    RecyclerView mRvContent;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_refund_data;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mLineView = get(R.id.line_view);
        mLeftAxisView = get(R.id.v_left_line);
        mLineView.setUnit(getActivity().getString(R.string.percent));
        mRvContent = get(R.id.rv_content);
        mRvContent.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    public void initTabLayout(String[] titles) {
        TabLayout tabLayout = get(R.id.tl_title);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        for (String a : titles) {
            tabLayout.addTab(tabLayout.newTab().setText(a));
        }
    }

    public void initTurnOverVolumeRecyclerView(OrderServerParams[] params) {
        FooterSingleAdapter adapter = new FooterSingleAdapter<TurnOverOrderRvItem>(getActivity(), R.layout.item_turnover_order_recyclerview, R.layout.item_rv_footer_in_report) {
            @Override
            protected void bindData(BaseViewHolder holder, TurnOverOrderRvItem item, int position) {
                TextView tvOrderState = holder.getView(R.id.tv_order_state);
                TextView tvTime = holder.getView(R.id.tv_time);
                ImageView ivProductImage = holder.getView(R.id.iv_product);
                TextView tvCompany = holder.getView(R.id.tv_company);
                TextView tvPhone = holder.getView(R.id.tv_phone);
                TextView tvAddress = holder.getView(R.id.tv_address);
                TextView tvForWhat = holder.getView(R.id.tv_for_what);
                TextView tvUnit = holder.getView(R.id.tv_unit);
                TextView tvAmount = holder.getView(R.id.tv_amount);
                TextView tvTotal = holder.getView(R.id.tv_total);
                TextView tvPostageMode = holder.getView(R.id.tv_postage_mode);
                TextView tvPrice = holder.getView(R.id.tv_price);
                TextView tvRefundState = holder.getView(R.id.tv_refund_state);
                TextView tvCustomerService = holder.getView(R.id.tv_customer_service);
                View vShipFee = holder.getView(R.id.ll_ship_fee);
                View vDiscount = holder.getView(R.id.iv_discount);
                GlideUtils.loadRoundedImage(ivProductImage.getContext(), ivProductImage, item.getPic(), R.drawable.common_product_placeholder, R.drawable.common_product_placeholder);
                tvOrderState.setText(item.getStatus());
                tvTime.setText(ConvertUtil.dateToYYYY_MM_DD_HH_mm_ss(new Date(Long.valueOf(item.getTime()) * 1000)));
                tvCompany.setText(item.getName() + "(" + item.getConsignee() + ")");
                tvPhone.setText(item.getPhone());
                tvAddress.setText(item.getProvince() + "  " + item.getCity());
//            holder1.tvForWhat.setText(mProductType[Integer.valueOf(item.getType()) - 1]);
                tvForWhat.setText(item.getType());
                tvUnit.setText(item.getPrice());
                tvPrice.setText(item.getSum_price());
//            holder1.tvPostageMode.setText(mFreight[Integer.valueOf(item.getFreight()) - 1]);
                vShipFee.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(item.getFreight())) {
                    tvPostageMode.setText(item.getFreight());
                    vShipFee.setVisibility(View.VISIBLE);
                }
                tvRefundState.setText(item.getSec_status());
                tvTotal.setText(item.getP_count());
                vDiscount.setVisibility(View.GONE);
                if (RegularUtil.isInteger(item.getDiscount())) {
                    int discount = Integer.valueOf(item.getDiscount());
                    if (discount > 1) {
                        vDiscount.setVisibility(View.VISIBLE);
                    }
                }
            }
        };
        List<TurnOverOrderRvItem> list = new ArrayList<>();
        for (OrderServerParams param : params) {
            TurnOverOrderRvItem item = new TurnOverOrderRvItem();
            item.setStatus(param.getStatus());
            item.setTime(param.getAdd_time());
            item.setCity(param.getCity());
            item.setProvince(param.getProvince());
            item.setDiscount(param.getDiscount());
            item.setSum_price(param.getTotal_price());
            item.setFreight(param.getShip_type());
            item.setName(param.getName());
            item.setPhone(param.getPhone());
            item.setPic(param.getPic());
            item.setSec_status(param.getSec_status());
            item.setType(param.getOrder_type());
            item.setConsignee(param.getConsignee());
//            item.setRvType(BaseRecyclerViewAdapter.TYPE_ONLY);
            list.add(item);
        }
        adapter.setData(list);
        mRvContent.setAdapter(adapter);
    }

    public void setBarDataList(ArrayList<ArrayList<Integer>> list, int space, ArrayList<Integer> colorList,
                               ArrayList<ArrayList<String>> contentList) {
        mLineView.setBarDataList(list, mLeftAxisView, space, colorList, contentList);
    }

    public void setBottomTextList(ArrayList<Date> list, String format) {
        mLineView.setBottomTextList(list, format);
    }

    public void setLineViewBottomSize(int size) {
        mLineView.setBottomTextSize(size);
        mLineView.requestLayout();
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }
}
