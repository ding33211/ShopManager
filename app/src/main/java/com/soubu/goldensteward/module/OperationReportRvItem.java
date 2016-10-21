package com.soubu.goldensteward.module;

import com.soubu.goldensteward.adapter.CustomGridViewAdapter;

/**
 * Created by dingsigang on 16-10-21.
 */
public class OperationReportRvItem {
    String label;
    CustomGridViewAdapter adapter;
    boolean clickable = true;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public CustomGridViewAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(CustomGridViewAdapter adapter) {
        this.adapter = adapter;
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }
}
