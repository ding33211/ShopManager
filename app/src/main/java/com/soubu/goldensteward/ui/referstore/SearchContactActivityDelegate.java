package com.soubu.goldensteward.ui.referstore;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.mvp.view.AppDelegate;

/**
 * Created by dingsigang on 16-12-12.
 */

public class SearchContactActivityDelegate extends AppDelegate {
    @Override
    public int getRootLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public boolean ifNeedSearch() {
        return true;
    }
}
