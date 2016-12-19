package com.soubu.goldensteward.ui.market;

import com.soubu.goldensteward.support.base.BaseApplication;
import com.soubu.goldensteward.support.bean.server.MyActivityServerParams;
import com.soubu.goldensteward.support.web.BasePageRequest;
import com.soubu.goldensteward.support.web.core.BaseResponse;
import com.soubu.goldensteward.support.web.core.ObservableWrapper;
import com.soubu.goldensteward.support.web.mvp.BaseListPresenter;

import java.util.List;

/**
 * 作者：余天然 on 2016/12/14 下午4:39
 */
public class MyActivityPresenter extends BaseListPresenter<MyActivityServerParams> {

    @Override
    protected ObservableWrapper<BaseResponse<List<MyActivityServerParams>>> getWrapper(int curPage) {
        return BaseApplication.getWebModel()
                .getMyActivity(new BasePageRequest(curPage));
    }
}
