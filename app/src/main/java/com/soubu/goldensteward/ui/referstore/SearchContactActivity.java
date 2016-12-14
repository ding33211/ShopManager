package com.soubu.goldensteward.ui.referstore;

import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;

/**
 * Created by dingsigang on 16-12-12.
 */

public class SearchContactActivity extends ActivityPresenter<SearchContactActivityDelegate> {
    @Override
    protected Class<SearchContactActivityDelegate> getDelegateClass() {
        return SearchContactActivityDelegate.class;
    }


}
