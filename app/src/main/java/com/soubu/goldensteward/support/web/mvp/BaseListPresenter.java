package com.soubu.goldensteward.support.web.mvp;

import java.util.List;

import rx.Observable;

/**
 * 作者：余天然 on 2016/12/12 上午11:20
 */
public abstract class BaseListPresenter<R> extends BasePresenter {

    public abstract Observable<List<R>> getData(int curPage);

}
