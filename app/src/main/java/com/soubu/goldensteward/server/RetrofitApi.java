package com.soubu.goldensteward.server;

import com.soubu.goldensteward.module.server.BaseDataArray;
import com.soubu.goldensteward.module.server.BaseDataObject;
import com.soubu.goldensteward.module.server.BaseResp;
import com.soubu.goldensteward.module.server.HomeInfoServerParams;
import com.soubu.goldensteward.module.server.MainProductTagServerParams;
import com.soubu.goldensteward.module.server.MergeServerParams;
import com.soubu.goldensteward.module.server.ModifyPwdServerParams;
import com.soubu.goldensteward.module.server.OperationReportServerParams;
import com.soubu.goldensteward.module.server.UserServerParams;
import com.soubu.goldensteward.module.server.VerificationServerParams;
import com.soubu.goldensteward.module.server.WalletHomeInfoServerParams;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitApi {


    //获取验证码
    @FormUrlEncoded
    @POST("User/send_sms")
    Call<BaseResp<UserServerParams>> getVerifyCode(@Field("params") String params);


    //验证验证码
    @FormUrlEncoded
    @POST("User/check_code")
    Call<BaseResp<UserServerParams>> checkCode(@Field("params") String params);

    //获取主营产品tag
    @POST("User/get_main_tag")
    Call<BaseResp<BaseDataArray<MainProductTagServerParams>>> getMainTag();

    //注册
    @FormUrlEncoded
    @POST("User/register")
    Call<BaseResp<UserServerParams>> register(@Field("params") String params);

    //认证
    @FormUrlEncoded
    @POST("Certification/submit_certification")
    Call<BaseResp<VerificationServerParams>> submitCertification(@Field("params") String params);

    //子账户认证
    @FormUrlEncoded
    @POST("Child/submit_merge_child")
    Call<BaseResp<MergeServerParams>> submitMergeChild(@Field("params") String params);

    //登录
    @FormUrlEncoded
    @POST("User/login")
    Call<BaseResp<BaseDataObject<UserServerParams>>> login(@Field("params") String params);

    //修改登录密码
    @FormUrlEncoded
    @POST("User/change_login_password")
    Call<BaseResp<ModifyPwdServerParams>> changeLoginPwd(@Field("params") String params);

    //修改用户信息
    @FormUrlEncoded
    @POST("User/change_user_info")
    Call<BaseResp<UserServerParams>> changeUserInfo(@Field("params") String params);

    //修改用户地址
    @FormUrlEncoded
    @POST("User/change_address")
    Call<BaseResp<UserServerParams>> changeAddress(@Field("params") String params);

    //获取首页信息
    @POST("Index/index")
    Call<BaseResp<BaseDataObject<HomeInfoServerParams>>> getHomeInfo();

    //获取运营报表
    @POST("Report/report_index")
    Call<BaseResp<BaseDataObject<OperationReportServerParams>>> getOperationReport();

    //获取
    @POST("Wallet/my_wallet")
    Call<BaseResp<BaseDataObject<WalletHomeInfoServerParams>>> getMyWalletInfo();
}
