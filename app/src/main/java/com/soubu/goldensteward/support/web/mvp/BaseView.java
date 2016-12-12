package com.soubu.goldensteward.support.web.mvp;

import com.trello.rxlifecycle.LifecycleTransformer;

/**
 * 作者：余天然 on 2016/12/9 下午3:18
 */
public interface BaseView {

    <T> LifecycleTransformer<T> bindLife();

    void initWidget();
}
