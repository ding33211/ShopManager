package com.soubu.goldensteward.ui.report;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.adapter.FooterSingleAdapter;
import com.soubu.goldensteward.support.adapter.SingleAdapter;
import com.soubu.goldensteward.support.bean.TurnOverOrderRvItem;
import com.soubu.goldensteward.support.bean.server.EvaluateInReturnRateServerParams;
import com.soubu.goldensteward.support.bean.server.ImageServerParams;
import com.soubu.goldensteward.support.bean.server.OrderServerParams;
import com.soubu.goldensteward.support.bean.server.ProductInOrderListServerParams;
import com.soubu.goldensteward.support.bean.server.VisitFriendsServerParams;
import com.soubu.goldensteward.support.mvp.view.AppDelegate;
import com.soubu.goldensteward.support.net.RetrofitRequest;
import com.soubu.goldensteward.support.utils.ConvertUtil;
import com.soubu.goldensteward.support.utils.GlideUtils;
import com.soubu.goldensteward.support.utils.RegularUtil;
import com.soubu.goldensteward.support.widget.linebarchart.LineView;
import com.soubu.goldensteward.support.widget.linebarchart.YAxisView;
import com.soubu.goldensteward.support.widget.recyclerviewdecoration.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by dingsigang on 16-10-21.
 */
public class OperationReportSpecActivityDelegate extends AppDelegate {
    LineView mLineView;
    YAxisView mLeftAxisView;
    TextView mLastWeek;
    TextView mLastMonth;
    RecyclerView mRvContent;
    TextView mTvLabel;
    int mCurrentTabIndex = 0;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_operation_report_spec;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mLineView = get(R.id.line_view);
        mLeftAxisView = get(R.id.v_left_line);
        mLastWeek = get(R.id.tv_last_week);
        mLastMonth = get(R.id.tv_last_month);
        mRvContent = get(R.id.rv_content);
        mTvLabel = get(R.id.tv_label);
        mRvContent.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    public void setColorInfo(String title, String unit) {
        ((TextView) get(R.id.tv_color_info)).setText(title + "(" + unit + ")");
        mLineView.setUnit(unit);
    }

    public void refreshTabTitle(String[] titles) {
        TabLayout tabLayout = get(R.id.tl_title);
        tabLayout.setVisibility(View.VISIBLE);
        get(R.id.v_tab_layout_margin).setVisibility(View.VISIBLE);
        mTvLabel.setVisibility(View.GONE);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (mCurrentTabIndex != tab.getPosition()) {
                    mCurrentTabIndex = tab.getPosition();
                    RetrofitRequest.getInstance().getOrderList(tab.getPosition());
                }

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

    public void clickLastWeek() {
        mLastWeek.setBackgroundResource(R.drawable.bg_orange_stroke_corners);
        mLastWeek.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        mLastMonth.setBackgroundResource(R.drawable.bg_grey_stroke_corners);
        mLastMonth.setTextColor(getActivity().getResources().getColor(R.color.subtitle_grey));
    }

    public void clickLastMonth() {
        mLastMonth.setBackgroundResource(R.drawable.bg_orange_stroke_corners);
        mLastMonth.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        mLastWeek.setBackgroundResource(R.drawable.bg_grey_stroke_corners);
        mLastWeek.setTextColor(getActivity().getResources().getColor(R.color.subtitle_grey));
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

    public void initLabel(String label) {
        mTvLabel.setText(label);
    }

    public void initReturnRateView() {
        get(R.id.ll_top).setVisibility(View.INVISIBLE);
    }

    public void initStoreVisitorRecyclerView(VisitFriendsServerParams[] params) {
        FooterSingleAdapter adapter = new FooterSingleAdapter<VisitFriendsServerParams>(getActivity(), R.layout.item_store_visitor_contact_friends_recyclerview, R.layout.item_rv_footer_in_report) {
            @Override
            protected void bindData(BaseViewHolder holder, VisitFriendsServerParams item, int position) {
                ImageView ivAvatar = holder.getView(R.id.iv_avatar);
                TextView tvName = holder.getView(R.id.tv_name);
                TextView tvTime = holder.getView(R.id.tv_time);
                GlideUtils.loadRoundedImage(ivAvatar.getContext(), ivAvatar, item.getPortrait(), R.drawable.common_header, R.drawable.common_header);
                tvName.setText(item.getName());
                tvTime.setText(ConvertUtil.dateToYYYY_MM_DD(new Date(Long.valueOf(item.getAdd_time()) * 1000)));
            }
        };
        List<VisitFriendsServerParams> list = Arrays.asList(params);
        adapter.setData(list);
        mRvContent.setAdapter(adapter);
    }

    public void initProductAccessRecyclerView(ProductInOrderListServerParams[] params) {
        SingleAdapter adapter = new SingleAdapter<ProductInOrderListServerParams>(getActivity(), R.layout.item_old_product_maybe_delete_soon) {
            @Override
            protected void bindData(BaseViewHolder holder, ProductInOrderListServerParams item, int position) {
                ImageView ivProductImg = holder.getView(R.id.iv_product);
                TextView tvProductName = holder.getView(R.id.tv_name);
                TextView tvBrowse = holder.getView(R.id.tv_browser_volume);
                TextView tvCollection = holder.getView(R.id.tv_collection_volume);
                TextView tvUnit = holder.getView(R.id.tv_unit);
                TextView tvUnitPrice = holder.getView(R.id.tv_unit_price);
                TextView tvTime = holder.getView(R.id.tv_time);
                TextView tvCustomerService = holder.getView(R.id.tv_customer_service);
                GlideUtils.loadRoundedImage(ivProductImg.getContext(), ivProductImg, item.getPic(), R.drawable.common_product_placeholder, R.drawable.common_product_placeholder);
                tvProductName.setText(item.getTitle());
                tvUnitPrice.setText(item.getPrice());
                tvUnit.setText(item.getUnit());
                tvTime.setText(ConvertUtil.dateToYYYY_MM_DD(new Date(Long.valueOf(item.getTime()) * 1000)));
                tvBrowse.setText(item.getVisit());
                tvCollection.setText(item.getCollection());
            }

        };
        List<ProductInOrderListServerParams> list = Arrays.asList(params);
        adapter.setData(list);
        mRvContent.setAdapter(adapter);
    }

    public void initReturnRateRecyclerView(List<EvaluateInReturnRateServerParams> list) {
        FooterSingleAdapter adapter = new FooterSingleAdapter<EvaluateInReturnRateServerParams>(getActivity(), R.layout.item_store_visitor_contact_friends_recyclerview, R.layout.item_rv_footer_in_report) {
            @Override
            protected void bindData(BaseViewHolder holder, EvaluateInReturnRateServerParams item, int position) {
                ImageView ivCompanyIcon = holder.getView(R.id.iv_company_icon);
                TextView tvName = holder.getView(R.id.tv_name);
                ImageView ivVip = holder.getView(R.id.iv_vip);
//            ivShopType = (ImageView) itemView.findViewById(R.id.iv_shop_type);
                ImageView ivShopSpec = holder.getView(R.id.iv_shop_spec);
                TextView tvContent = holder.getView(R.id.tv_content);
                TextView tvTime = holder.getView(R.id.tv_time);
                TextView tvCustomerService = holder.getView(R.id.tv_customer_service);
                GridView gvImage = holder.getView(R.id.gv_image);
                RatingBar rbRating = holder.getView(R.id.rb_rating);
                TextView tvReply = holder.getView(R.id.tv_reply);
                gvImage.setVisibility(View.GONE);
                if (item.getImgList() != null && item.getImgList().length > 0) {
                    ImageServerParams[] images = item.getImgList();
                    List<String> urls = new ArrayList<>();
                    for (ImageServerParams params : images) {
                        urls.add(params.getThumb_img());
                    }
                    gvImage.setAdapter(new ImageGridViewAdapter(gvImage.getContext(), urls));
                    gvImage.setVisibility(View.VISIBLE);
                }
                GlideUtils.loadRoundedImage(ivCompanyIcon.getContext(), ivCompanyIcon, item.getPortrait(), R.drawable.common_header, R.drawable.common_header);
                tvName.setText(item.getName());
                if (RegularUtil.isInteger(item.getLevel())) {
                    switch (Integer.valueOf(item.getLevel())) {
                        case 0:
                            ivVip.setImageResource(R.drawable.role_0);
                            break;
                        case 1:
                            ivVip.setImageResource(R.drawable.role_1);
                            break;
                        case 2:
                            ivVip.setImageResource(R.drawable.role_2);
                            break;
                        case 3:
                            ivVip.setImageResource(R.drawable.role_3);
                            break;
                        case 4:
                            ivVip.setImageResource(R.drawable.role_4);
                            break;
                    }
                }
                try {
                    rbRating.setRating(Float.valueOf(item.getRating()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tvContent.setText(item.getContent());
                tvTime.setText(TextUtils.isEmpty(item.getAdd_time()) ? "" : ConvertUtil.dateToYYYY_MM_DD_HH_mm(new Date(Long.valueOf(item.getAdd_time()) * 1000)));
                tvReply.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(item.getReply())) {
                    tvReply.setText("商家回复：" + item.getReply());
                    tvReply.setVisibility(View.VISIBLE);
                }
                if (RegularUtil.isInteger(item.getRole())) {
                    int role = Integer.valueOf(item.getRole());
                    if (role == 1) {
                        ivShopSpec.setImageResource(R.drawable.purchaser_type_purchaser);
                    } else {
                        ivShopSpec.setImageResource(R.drawable.supplier_type_supplier);
                    }
                }
            }
        };
//        List<ReturnRateAllEvaluateRvItem> list = new ArrayList<>();
//        ReturnRateAllEvaluateRvItem item = new ReturnRateAllEvaluateRvItem();
//        item.setUrls(new String[]{"http://img.isoubu.net/jgj/certification/1479263394496.jpg","http://img.isoubu.net/jgj/certification/1479263394496.jpg","http://img.isoubu.net/jgj/certification/1479263394496.jpg","http://img.isoubu.net/jgj/certification/1479263394496.jpg","http://img.isoubu.net/jgj/certification/1479263394496.jpg","http://img.isoubu.net/jgj/certification/1479263394496.jpg","http://img.isoubu.net/jgj/certification/1479263394496.jpg","http://img.isoubu.net/jgj/certification/1479263394496.jpg","http://img.isoubu.net/jgj/certification/1479263394496.jpg"});
//        list.add(item);
//        list.add(new ReturnRateAllEvaluateRvItem());
//        list.add(new ReturnRateAllEvaluateRvItem());
//        ReturnRateAllEvaluateRvItem item2 = new ReturnRateAllEvaluateRvItem();
//        item2.setUrls(new String[]{"http://img.isoubu.net/jgj/certification/1479263394496.jpg","http://img.isoubu.net/jgj/certification/1479263394496.jpg","http://img.isoubu.net/jgj/certification/1479263394496.jpg","http://img.isoubu.net/jgj/certification/1479263394496.jpg","http://img.isoubu.net/jgj/certification/1479263394496.jpg","http://img.isoubu.net/jgj/certification/1479263394496.jpg","http://img.isoubu.net/jgj/certification/1479263394496.jpg"});
//        list.add(item2);
//        list.add(new ReturnRateAllEvaluateRvItem());
//        list.add(new ReturnRateAllEvaluateRvItem());
//        list.add(new ReturnRateAllEvaluateRvItem());
//        list.add(new ReturnRateAllEvaluateRvItem());
        adapter.setData(list);
        mRvContent.setAdapter(adapter);
        mRvContent.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, 2));
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }
}
