package com.soubu.goldensteward.ui.market;

import com.soubu.goldensteward.support.base.BaseApplication;
import com.soubu.goldensteward.support.bean.server.AllActivityServerParams;
import com.soubu.goldensteward.support.bean.server.BasePageRequest;
import com.soubu.goldensteward.support.web.core.BaseResponse;
import com.soubu.goldensteward.support.web.core.ObservableWrapper;
import com.soubu.goldensteward.support.web.mvp.BaseListPresenter;

import java.util.List;

/**
 * 作者：余天然 on 2016/12/14 下午7:14
 */
public class AllActivityPresenter extends BaseListPresenter<AllActivityServerParams, BaseListView> {

    @Override
    protected ObservableWrapper<BaseResponse<List<AllActivityServerParams>>> getWrapper(int curPage) {
        return BaseApplication.getWebModel()
                .getAllActivity(new BasePageRequest(curPage));
    }

}
