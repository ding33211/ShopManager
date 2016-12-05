package com.soubu.goldensteward.report;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;
import com.soubu.goldensteward.utils.MathDoubleUtil;
import com.soubu.goldensteward.widget.linebarchart.LineView;
import com.soubu.goldensteward.widget.linebarchart.PieHelper;
import com.soubu.goldensteward.widget.linebarchart.PieView;
import com.soubu.goldensteward.widget.linebarchart.YAxisView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by dingsigang on 16-11-29.
 */

public class OrderTransactionActivityDelegate extends AppDelegate {
    PieView mPv1;
    PieView mPv2;
    LineView mLineView;
    YAxisView mLeftAxisView;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_order_transaction;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mLineView = get(R.id.line_view);
        mLeftAxisView = get(R.id.v_left_line);
        mPv1 = get(R.id.pie_view_1);
        mPv2 = get(R.id.pie_view_2);
        View vLastWeek = get(R.id.bt_last_week);
        View vLastMonth = get(R.id.bt_last_month);
        mTopButtons = new View[]{vLastWeek, vLastMonth};
        mTopButtons[0].setSelected(true);
        mLineView.setUnit(getActivity().getString(R.string.yuan));
    }

    public void setPie1Data(String data1, String data2, String data3, String line1, String line2) {
        TextView tvPie11 = get(R.id.tv_color_card_num);
        TextView tvPie12 = get(R.id.tv_sample_card_num);
        TextView tvPie13 = get(R.id.tv_big_goods_num);
        TextView tvPie11p = get(R.id.tv_color_card_num_percent);
        TextView tvPie12p = get(R.id.tv_sample_card_num_percent);
        TextView tvPie13p = get(R.id.tv_big_goods_num_percent);
        tvPie11.setText(data1);
        tvPie12.setText(data2);
        tvPie13.setText(data3);
        int a = Integer.valueOf(data1);
        int b = Integer.valueOf(data2);
        int c = Integer.valueOf(data3);
        int ap = (int) MathDoubleUtil.round(MathDoubleUtil.div(a * 100, a + b + c, 1), 0);
        int bp = (int) MathDoubleUtil.round(MathDoubleUtil.div(b * 100, a + b + c, 1), 0);
        int cp = (int) MathDoubleUtil.round(MathDoubleUtil.div(c * 100, a + b + c, 1), 0);
        tvPie11p.setText(ap + "");
        tvPie12p.setText(bp + "");
        tvPie13p.setText(cp + "");
        ArrayList<PieHelper> pieHelperArrayList = new ArrayList<>();
        pieHelperArrayList.add(new PieHelper(ap, getActivity().getResources().getColor(R.color.pie_color_1), PieHelper.TYPE_BIG));
        pieHelperArrayList.add(new PieHelper(bp, getActivity().getResources().getColor(R.color.pie_color_2), PieHelper.TYPE_NORMAL));
        pieHelperArrayList.add(new PieHelper(cp, getActivity().getResources().getColor(R.color.pie_color_3), PieHelper.TYPE_SMALL));
        mPv1.setDate(pieHelperArrayList, line1, line2);
    }


    public void setPie2Data(String data1, String data2, String data3, String line1, String line2) {
        TextView tvPie11 = get(R.id.tv_color_card_amount);
        TextView tvPie12 = get(R.id.tv_sample_card_amount);
        TextView tvPie13 = get(R.id.tv_big_goods_amount);
        TextView tvPie11p = get(R.id.tv_color_card_amount_percent);
        TextView tvPie12p = get(R.id.tv_sample_card_amount_percent);
        TextView tvPie13p = get(R.id.tv_big_goods_amount_percent);
        tvPie11.setText(data1);
        tvPie12.setText(data2);
        tvPie13.setText(data3);
        int a = Integer.valueOf(data1);
        int b = Integer.valueOf(data2);
        int c = Integer.valueOf(data3);
        int ap = (int) MathDoubleUtil.round(MathDoubleUtil.div(a * 100, a + b + c, 1), 0);
        int bp = (int) MathDoubleUtil.round(MathDoubleUtil.div(b * 100, a + b + c, 1), 0);
        int cp = (int) MathDoubleUtil.round(MathDoubleUtil.div(c * 100, a + b + c, 1), 0);
        tvPie11p.setText(ap + "");
        tvPie12p.setText(bp + "");
        tvPie13p.setText(cp + "");
        ArrayList<PieHelper> pieHelperArrayList = new ArrayList<>();
        pieHelperArrayList.add(new PieHelper(ap, getActivity().getResources().getColor(R.color.pie_color_4), PieHelper.TYPE_BIG));
        pieHelperArrayList.add(new PieHelper(bp, getActivity().getResources().getColor(R.color.pie_color_5), PieHelper.TYPE_NORMAL));
        pieHelperArrayList.add(new PieHelper(cp, getActivity().getResources().getColor(R.color.pie_color_6), PieHelper.TYPE_SMALL));
        mPv2.setDate(pieHelperArrayList, line1, line2);
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
