package com.soubu.goldensteward.support.web.core;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.base.BaseApplication;
import com.soubu.goldensteward.support.helper.UserManager;
import com.soubu.goldensteward.support.utils.LogUtil;
import com.soubu.goldensteward.support.utils.ShowWidgetUtil;
import com.soubu.goldensteward.support.web.mvp.BaseView;
import com.soubu.goldensteward.ui.login.LoginActivity;

import rx.Subscriber;

/**
 * Rx事件接收器基类
 * <p>
 * 作者：余天然 on 2016/12/7 下午4:31
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private boolean isTokenExpire = false;//是否是Token失效的错误，是的话则不弹出Toast

    private BaseView view;

    public BaseSubscriber() {
    }

    public BaseSubscriber(BaseView view) {
        this.view = view;
    }

    public BaseView getBaseView() {
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
                //token 过期
                if (response.getStatus() == -1) {
                    isTokenExpire = true;
                    new AlertDialog.Builder(BaseApplication.getContext().getNowContext()).setTitle(R.string.alert).setMessage(response.msg).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            UserManager.clearUser();
                            BaseApplication.getContext().finishAllActivity();
                            Intent intent = new Intent(BaseApplication.getContext().getNowContext(), LoginActivity.class);
                            BaseApplication.getContext().getNowContext().startActivity(intent);
                        }
                    }).show();
                }
                return true;
            }
        }
        //默认是成功的，因为有时候有些List可能已经从BaseResponse中取出来了
        return false;
    }

    //请求到数据了，但业务错误
    public BaseException onBizError(T t) {
        LogUtil.print("t=" + t);
        if (t instanceof BaseResponse) {
            BaseResponse response = (BaseResponse) t;
            LogUtil.print("业务错误：" + response.getMsg());
            return new BaseException(response.getStatus(), response.getMsg());
        }
        return new BaseException(0, "未知业务错误");
    }

    //没请求成功,网络错误
    public BaseException onWebError(Throwable e) {
        LogUtil.printException(e);
        return new BaseException(0, BaseApplication.getContext().getString(R.string.please_check_internet));
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
        if (!isTokenExpire) {
            ShowWidgetUtil.showShort(exception.getMessage());
        }
    }

}
