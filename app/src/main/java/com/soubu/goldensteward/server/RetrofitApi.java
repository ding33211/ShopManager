package com.soubu.goldensteward.server;

import com.soubu.goldensteward.module.server.BaseData;
import com.soubu.goldensteward.module.server.BaseResp;
import com.soubu.goldensteward.module.server.LoginServerParams;
import com.soubu.goldensteward.module.server.MainProductTagServerParams;
import com.soubu.goldensteward.module.server.RegisterServerParams;
import com.soubu.goldensteward.module.server.VerificationServerParams;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitApi {


    //获取验证码
    @FormUrlEncoded
    @POST("User/send_sms")
    Call<BaseResp<RegisterServerParams>> getVerifyCode(@Field("params") String params);


    //验证验证码
    @FormUrlEncoded
    @POST("User/check_code")
    Call<BaseResp<RegisterServerParams>> checkCode(@Field("params") String params);

    //获取主营产品tag
    @POST("User/get_main_tag")
    Call<BaseResp<BaseData<MainProductTagServerParams>>> getMainTag();

    //注册
    @FormUrlEncoded
    @POST("User/register")
    Call<BaseResp<LoginServerParams>> register(@Field("params") String params);

    //注册
    @FormUrlEncoded
    @POST("Certification/submit_certification")
    Call<BaseResp<VerificationServerParams>> submitCertification(@Field("params") String params);
}
