package com.soubu.goldensteward.ui.market;

import com.soubu.goldensteward.support.base.BaseApplication;
import com.soubu.goldensteward.support.bean.server.MyActivityServerParams;
import com.soubu.goldensteward.support.bean.server.BasePageRequest;
import com.soubu.goldensteward.support.web.mvp.BaseListPresenter;

import java.util.List;

import rx.Observable;

/**
 * 作者：余天然 on 2016/12/14 下午4:39
 */
public class MyActivityPresenter extends BaseListPresenter<MyActivityServerParams> {

    public Observable<List<MyActivityServerParams>> getData(int curPage) {
        return BaseApplication.getWebModel()
                .getMyActivity(new BasePageRequest(curPage))
                .getObservable()
                .map(res -> res.getResult().getData());
    }
}
