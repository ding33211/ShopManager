package com.soubu.goldensteward.delegate;

import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;
import com.soubu.goldensteward.widget.linebarchart.LineView;
import com.soubu.goldensteward.widget.linebarchart.YAxisView;

import java.util.ArrayList;

/**
 * Created by dingsigang on 16-10-21.
 */
public class OperationReportSpecActivityDelegate extends AppDelegate {
    LineView mLineView;
    YAxisView mLeftAxisView;
    TextView mLastWeek;
    TextView mLastMonth;

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
    }

    public void setBarDataList(ArrayList<ArrayList<Integer>> list, int space, ArrayList<Integer> colorList){
        mLineView.setBarDataList(list, mLeftAxisView, space, colorList);
    }

    public void setBottomTextList(ArrayList<String> list){
        mLineView.setBottomTextList(list);
    }

    public void clickLastWeek(){
        mLastWeek.setBackgroundResource(R.drawable.bg_orange_stroke_corners);
        mLastWeek.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        mLastMonth.setBackgroundResource(R.drawable.bg_grey_stroke_corners);
        mLastMonth.setTextColor(getActivity().getResources().getColor(R.color.subtitle_grey));
    }

    public void clickLastMonth(){
        mLastMonth.setBackgroundResource(R.drawable.bg_orange_stroke_corners);
        mLastMonth.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        mLastWeek.setBackgroundResource(R.drawable.bg_grey_stroke_corners);
        mLastWeek.setTextColor(getActivity().getResources().getColor(R.color.subtitle_grey));
    }
}
