package com.soubu.goldensteward.support.web.core;


import com.soubu.goldensteward.support.utils.LogUtil;
import com.soubu.goldensteward.support.utils.ShowWidgetUtil;
import com.soubu.goldensteward.support.web.mvp.BaseView;

import rx.Subscriber;

/**
 * Rx事件接收器基类
 * <p>
 * 作者：余天然 on 2016/12/7 下午4:31
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private BaseView view;

    public BaseSubscriber(BaseView view) {
        this.view = view;
    }

    public BaseView getView() {
        return view;
    }

    @Override
    public void onCompleted() {
        this.view = null;
    }

    @Override
    public void onError(Throwable e) {
        this.view = null;
        LogUtil.printException(e);
        ShowWidgetUtil.dismissProgressDialog();
        //先调用网络错误
        BaseException exception = onWebError(e);
        //再调用请求失败
        onFailure(exception);
    }

    @Override
    public void onNext(T t) {
        ShowWidgetUtil.dismissProgressDialog();
        //检查是否是业务错误
        if (checkBizFailure(t)) {
            //先调用业务错误
            BaseException exception = onBizError(t);
            //再调用请求失败
            onFailure(exception);
        } else {
            onSuccess(t);
        }
    }

    //检查是否是业务失败
    public boolean checkBizFailure(T t) {
        if (t instanceof BaseResponse) {
            BaseResponse response = (BaseResponse) t;
            //请求正确
            if (response.getStatus() == BaseStatus.SUCCESS) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    //请求到数据了，但业务错误
    public BaseException onBizError(T t) {
        if (t instanceof BaseResponse) {
            BaseResponse response = (BaseResponse) t;
            return new BaseException(response.getStatus(), response.getMsg());
        }
        return new BaseException(0, "系统错误");
    }

    //没请求成功,网络错误
    public BaseException onWebError(Throwable e) {
        return new BaseException(0, "系统错误");
    }

    /**
     * 请求成功
     */
    public abstract void onSuccess(T response);

    /**
     * 请求失败
     * <p>
     * 这里做一些公共的错误处理，个人页面需要自己处理错误的话，重写即可
     */
    public void onFailure(BaseException exception) {
        // TODO: 2016/12/7 公共的错误处理
        LogUtil.print(exception.getMessage());
        ShowWidgetUtil.showShort(exception.getMessage());
    }

}
