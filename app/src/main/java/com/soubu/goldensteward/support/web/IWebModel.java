package com.soubu.goldensteward.support.web;


import com.soubu.goldensteward.support.bean.server.UserServerParams;
import com.soubu.goldensteward.support.web.core.BaseResponse;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 自定义的网络接口类
 */
public interface IWebModel {

    //登录
    @POST("User/login")
    Observable<BaseResponse<UserServerParams>> login(@Body UserServerParams request);

}