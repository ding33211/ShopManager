package com.soubu.goldensteward.ui.market;

import com.soubu.goldensteward.support.base.BaseApplication;
import com.soubu.goldensteward.support.bean.server.AllActivityServerParams;
import com.soubu.goldensteward.support.web.BasePageRequest;
import com.soubu.goldensteward.support.web.mvp.BaseListPresenter;

import java.util.List;

import rx.Observable;

/**
 * 作者：余天然 on 2016/12/14 下午7:14
 */
public class AllActivityPresenter extends BaseListPresenter<AllActivityServerParams> {
    @Override
    public Observable<List<AllActivityServerParams>> getData(int curPage) {
        return BaseApplication.getWebModel()
                .getAllActivity(new BasePageRequest(curPage))
                .getObservable()
                .map(res -> res.getResult().getData());
    }
}
