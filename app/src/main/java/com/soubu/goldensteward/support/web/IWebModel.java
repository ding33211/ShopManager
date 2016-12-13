package com.soubu.goldensteward.support.web;


import com.soubu.goldensteward.support.bean.server.ActivitySpecServerParams;
import com.soubu.goldensteward.support.bean.server.AllActivityServerParams;
import com.soubu.goldensteward.support.bean.server.BaseDataArray;
import com.soubu.goldensteward.support.bean.server.BaseDataObject;
import com.soubu.goldensteward.support.bean.server.BaseResp;
import com.soubu.goldensteward.support.bean.server.CustomerDetailDataObject;
import com.soubu.goldensteward.support.bean.server.CustomerServerParams;
import com.soubu.goldensteward.support.bean.server.EvaluateInReturnRateServerParams;
import com.soubu.goldensteward.support.bean.server.FeedBackServerParams;
import com.soubu.goldensteward.support.bean.server.HomeInfoServerParams;
import com.soubu.goldensteward.support.bean.server.IncomeOrExpensesServerParams;
import com.soubu.goldensteward.support.bean.server.MainProductTagServerParams;
import com.soubu.goldensteward.support.bean.server.MyActivityServerParams;
import com.soubu.goldensteward.support.bean.server.OperationReportServerParams;
import com.soubu.goldensteward.support.bean.server.OrderDataArray;
import com.soubu.goldensteward.support.bean.server.ProductInOrderListServerParams;
import com.soubu.goldensteward.support.bean.server.ShopVisitorServerParams;
import com.soubu.goldensteward.support.bean.server.SubAccountInActivityServerParams;
import com.soubu.goldensteward.support.bean.server.SubAccountServerParams;
import com.soubu.goldensteward.support.bean.server.TurnOverServerParams;
import com.soubu.goldensteward.support.bean.server.UserServerParams;
import com.soubu.goldensteward.support.bean.server.VisitFriendsServerParams;
import com.soubu.goldensteward.support.bean.server.WalletHomeInfoServerParams;
import com.soubu.goldensteward.support.bean.server.WithCountDataArray;
import com.soubu.goldensteward.support.web.core.BaseResponse;
import com.soubu.goldensteward.support.web.core.ObservableWrapper;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 自定义的网络接口类
 */
public interface IWebModel {

    //登录
    @POST("User/login")
    ObservableWrapper<BaseResponse<UserServerParams>> login(@Body UserServerParams request);

    //获取验证码
    @POST("User/send_sms")
    ObservableWrapper<BaseResponse<Object>> getVerifyCode(@Body UserServerParams params);

    //验证验证码
    @POST("User/check_code")
    ObservableWrapper<BaseResponse<Object>> checkCode(@Body UserServerParams params);

    //忘记密码
    @POST("Security/forget_password")
    ObservableWrapper<BaseResponse<Object>> forgetPassword(@Body UserServerParams params);

    //意见反馈
    @POST("Other/feedback")
    ObservableWrapper<BaseResponse> sendFeedBack(@Body FeedBackServerParams params);

    //获取修改手机号老号码验证码
    @POST("User/change_phone_sms")
    ObservableWrapper<BaseResponse> getOldPhoneVerifyCode(@Body Map map);

    //获取子账号详情
    @POST("Child/child_detail")
    ObservableWrapper<BaseResponse<SubAccountServerParams>> getSubAccountDetail(@Body Map map);

    //活动列表接口
    @POST("Activity/activity_list")
    ObservableWrapper<BaseResponse<List<AllActivityServerParams>>> getAllActivity();

    //获取我的活动
    @POST("Activity/my_activity_list")
    ObservableWrapper<BaseResponse<List<MyActivityServerParams>>> getMyActivity();

    //获取活动详情
    @POST("Activity/activity_detail")
    ObservableWrapper<BaseResponse<ActivitySpecServerParams>> getActivitySpec(@Body Map map);

    //活动详情页面获取子账号信息
    @POST("Activity/get_child")
    ObservableWrapper<BaseResponse<List<SubAccountInActivityServerParams>>> getSubAccountInActivity();

    //---------------------------------------------------------------------------

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
    Call<BaseResp<Object>> submitCertification(@Field("params") String params);

    //子账户关联
    @FormUrlEncoded
    @POST("Child/submit_merge_child")
    Call<BaseResp<Object>> submitMergeChild(@Field("params") String params);

    //子账户验证
    @FormUrlEncoded
    @POST("Child/check_phone")
    Call<BaseResp<Object>> checkChildPhone(@Field("params") String params);

    //修改登录密码
    @FormUrlEncoded
    @POST("User/change_login_password")
    Call<BaseResp<Object>> changeLoginPwd(@Field("params") String params);

    //修改用户信息
    @FormUrlEncoded
    @POST("User/change_user_info")
    Call<BaseResp<Object>> changeUserInfo(@Field("params") String params);

    //修改用户地址
    @FormUrlEncoded
    @POST("User/change_address")
    Call<BaseResp<Object>> changeAddress(@Field("params") String params);

    //获取首页信息
    @POST("Index/index")
    Call<BaseResp<BaseDataObject<HomeInfoServerParams>>> getHomeInfo();

    //获取运营报表
    @POST("Report/report_index")
    Call<BaseResp<BaseDataObject<OperationReportServerParams>>> getOperationReport();

    //获取
    @POST("Wallet/my_wallet")
    Call<BaseResp<BaseDataObject<WalletHomeInfoServerParams>>> getMyWalletInfo();

    //验证旧手机号
    @FormUrlEncoded
    @POST("Security/check_old_phone")
    Call<BaseResp<Object>> checkOldPhone(@Field("params") String params);

    //修改手机号
    @FormUrlEncoded
    @POST("Security/change_phone")
    Call<BaseResp<Object>> changePhone(@Field("params") String params);


    //获取我的用户列表
    @POST("Customer/customer_list")
    Call<BaseResp<BaseDataArray<CustomerServerParams>>> getCustomerList();

    //获取客户详情
    @FormUrlEncoded
    @POST("Customer/customer_detail")
    Call<BaseResp<CustomerDetailDataObject>> getCustomerDetail(@Field("params") String params);

    //获取我的收入
    @POST("Wallet/my_income")
    Call<BaseResp<BaseDataArray<IncomeOrExpensesServerParams>>> getMyIncome();

    //获取我的支出
    @POST("Wallet/my_expenditure")
    Call<BaseResp<BaseDataArray<IncomeOrExpensesServerParams>>> getMyExpenses();

    //获取成交额
    @POST("Report/turnover")
    Call<BaseResp<BaseDataArray<TurnOverServerParams>>> getTurnOver();

    //获取订单列表
    @FormUrlEncoded
    @POST("Report/order_list")
    Call<BaseResp<OrderDataArray>> getOrderList(@Field("params") String params);

    //获取店铺访问接口
    @POST("Report/shop_visit")
    Call<BaseResp<BaseDataArray<ShopVisitorServerParams>>> getShopVisit();

    //获取好友列表
    @POST("Report/visit_friends")
    Call<BaseResp<WithCountDataArray<VisitFriendsServerParams>>> getVisitFriends();

    //获取产品访问
    @POST("Report/product_visit")
    Call<BaseResp<BaseDataArray<ShopVisitorServerParams>>> getProductVisit();

    //获取在手列表
    @POST("Report/product_list")
    Call<BaseResp<WithCountDataArray<ProductInOrderListServerParams>>> getProductListOnSale();

    //获取退货率
    @POST("Report/return_rates")
    Call<BaseResp<BaseDataArray<ShopVisitorServerParams>>> getReturnRates();

    //获取评价列表
    @POST("Report/evaluate")
    Call<BaseResp<WithCountDataArray<EvaluateInReturnRateServerParams>>> getAllEvaluateInReturnRates();

    //获取子账户列表
    @POST("Child/child_list")
    Call<BaseResp<BaseDataArray<SubAccountServerParams>>> getSubAccountList();
}