package com.soubu.goldensteward.support.bean;

import com.soubu.goldensteward.ui.home.HomeGridViewAdapter;

/**
 * Created by dingsigang on 16-10-21.
 */
public class OperationReportRvItem {
    String label;
    HomeGridViewAdapter adapter;
    boolean clickable = true;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public HomeGridViewAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(HomeGridViewAdapter adapter) {
        this.adapter = adapter;
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }
}
