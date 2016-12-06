package com.soubu.goldensteward.ui.report;

import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.mvp.view.AppDelegate;
import com.soubu.goldensteward.support.widget.linebarchart.LineView;
import com.soubu.goldensteward.support.widget.linebarchart.YAxisView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dingsigang on 16-11-29.
 */

public class NewOperationReportActivityDelegate extends AppDelegate {

    LineView mLineView;
    YAxisView mLeftAxisView;
    TextView mTvBrowserNum;
    TextView mTvVisitorNum;
    TextView mTvCollection;
    TextView mTvAverageNum;


    @Override
    public void initWidget() {
        super.initWidget();
        mLineView = get(R.id.line_view);
        mLeftAxisView = get(R.id.v_left_line);
        mTvBrowserNum = get(R.id.tv_browser_num);
        mTvVisitorNum = get(R.id.tv_visitor_num);
        mTvCollection = get(R.id.tv_collection_num);
        mTvAverageNum = get(R.id.tv_average_browser_num);
        View vLastWeek = get(R.id.bt_last_week);
        View vLastMonth = get(R.id.bt_last_month);
        View vLastHalfYear = get(R.id.bt_last_half_year);
        mTopButtons = new View[]{vLastWeek, vLastMonth, vLastHalfYear};
        mTopButtons[0].setSelected(true);
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_new_operation_report;
    }

    public void setDataList(ArrayList<ArrayList<Integer>> list, int space, ArrayList<Integer> colorList) {
        mLineView.setDataList(list, mLeftAxisView, space, colorList, false, true);
    }

    public void setBottomTextList(ArrayList<Date> list, String format) {
        mLineView.setBottomTextList(list, format);
    }

    public void setLineViewBottomSize(int size) {
        mLineView.setBottomTextSize(size);
        mLineView.requestLayout();
    }


    public void setLineIndexClickListener(LineView.OnClickIndexListener listener) {
        mLineView.setOnclickIndexListener(listener);
    }

    public void initBottomData(List<Integer> list) {
        get(R.id.ll_bottom).setVisibility(View.VISIBLE);
        mTvBrowserNum.setText(list.get(0) + "");
        mTvVisitorNum.setText(list.get(1) + "");
        mTvCollection.setText(list.get(2) + "");
        mTvCollection.post(new Runnable() {
            @Override
            public void run() {
                ((ScrollView) get(R.id.sv_container)).fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

}
