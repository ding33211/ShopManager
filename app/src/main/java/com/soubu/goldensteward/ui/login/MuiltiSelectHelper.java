package com.soubu.goldensteward.ui.login;

import android.view.View;

/**
 * 多选一按钮的辅助类
 * <p>
 * 作者：余天然 on 2016/12/12 下午3:59
 */
public class MuiltiSelectHelper {

    protected View[] views = null;

    public MuiltiSelectHelper(View[] views) {
        this.views = views;
    }

    public void setTopBarSelected(int index) {
        for (int i = 0; i < views.length; i++) {
            if (i == index) {
                views[i].setSelected(true);
            } else {
                views[i].setSelected(false);
            }
        }
    }
}
