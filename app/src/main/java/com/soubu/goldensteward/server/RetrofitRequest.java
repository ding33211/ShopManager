package com.soubu.goldensteward.server;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.soubu.goldensteward.GoldenStewardApplication;
import com.soubu.goldensteward.R;
import com.soubu.goldensteward.module.BaseEventBusResp;
import com.soubu.goldensteward.module.EventBusConfig;
import com.soubu.goldensteward.module.server.BaseDataArray;
import com.soubu.goldensteward.module.server.BaseDataObject;
import com.soubu.goldensteward.module.server.BaseResp;
import com.soubu.goldensteward.module.server.CustomerDetailDataObject;
import com.soubu.goldensteward.module.server.CustomerServerParams;
import com.soubu.goldensteward.module.server.EvaluateInReturnRateServerParams;
import com.soubu.goldensteward.module.server.FeedBackServerParams;
import com.soubu.goldensteward.module.server.HomeInfoServerParams;
import com.soubu.goldensteward.module.server.IncomeOrExpensesServerParams;
import com.soubu.goldensteward.module.server.MainProductTagServerParams;
import com.soubu.goldensteward.module.server.MergeServerParams;
import com.soubu.goldensteward.module.server.ModifyPwdServerParams;
import com.soubu.goldensteward.module.server.OperationReportServerParams;
import com.soubu.goldensteward.module.server.OrderDataArray;
import com.soubu.goldensteward.module.server.ProductInOrderListServerParams;
import com.soubu.goldensteward.module.server.ShopVisitorServerParams;
import com.soubu.goldensteward.module.server.SubAccountServerParams;
import com.soubu.goldensteward.module.server.TurnOverServerParams;
import com.soubu.goldensteward.module.server.UserServerParams;
import com.soubu.goldensteward.module.server.VerificationServerParams;
import com.soubu.goldensteward.module.server.VisitFriendsServerParams;
import com.soubu.goldensteward.module.server.WalletHomeInfoServerParams;
import com.soubu.goldensteward.module.server.WithCountDataArray;
import com.soubu.goldensteward.utils.PhoneUtil;
import com.soubu.goldensteward.utils.ShowWidgetUtil;
import com.soubu.goldensteward.view.activity.LoginActivity;

import org.greenrobot.eventbus.EventBus;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.baidu.location.h.j.m;

/**
 * Retrofit的网络请求类
 * Created by dingsigang on 16-8-17.
 */
public class RetrofitRequest {
    public static final String TAG = "RetrofitRequest";

    private static RetrofitRequest mInstance;

    public static RetrofitRequest getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitRequest.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitRequest();
                }
            }
        }
        return mInstance;
    }

    private <T> void enqueueClue(Call<BaseResp<T>> call, final boolean needEventPost, int eventBusCode) {
        enqueueClue(call, needEventPost, null, eventBusCode);
    }


    private <T> void enqueueClue(Call<BaseResp<T>> call, final boolean needEventPost, String dialogContent, final int eventBusCode) {
        if (!PhoneUtil.isConnected(GoldenStewardApplication.getContext())) {
            ShowWidgetUtil.showShort(R.string.please_check_internet);
            return;
        }
        ShowWidgetUtil.showProgressDialog(dialogContent, R.style.LoadingProgressTheme);
        call.enqueue(new Callback<BaseResp<T>>() {
            @Override
            public void onResponse(Call<BaseResp<T>> call, Response<BaseResp<T>> response) {
                ShowWidgetUtil.dismissProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().status == -1) {
                        new AlertDialog.Builder(GoldenStewardApplication.getNowContext()).setTitle(R.string.alert).setMessage(response.body().msg).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                GoldenStewardApplication.getContext().clearUser();
                                Intent intent = new Intent(GoldenStewardApplication.getNowContext(), LoginActivity.class);
                                GoldenStewardApplication.getNowContext().startActivity(intent);
                            }
                        }).show();

                    }
                    if (response.body().status != HttpURLConnection.HTTP_OK) {
                        Log.e(TAG, "errorBody  :   " + response.body().msg + "   status  :  " + response.body().status);
                        ShowWidgetUtil.showShort(response.body().msg);
                        if (needEventPost) {
                            EventBus.getDefault().post(response.body().status);
                        }
                    } else {
                        if (needEventPost) {
                            EventBus.getDefault().post(new BaseEventBusResp(eventBusCode, response.body()));
                        }
                    }
                } else {
                    ServerErrorUtil.handleServerError(response.code());
                }
            }

            @Override
            public void onFailure(Call<BaseResp<T>> call, Throwable t) {
                Log.e(TAG, "1111111111111" + t.toString());
                ShowWidgetUtil.dismissProgressDialog();
                if (t.toString().contains("SocketTimeout")) {
                    ShowWidgetUtil.showShort(R.string.error_socket_timeout_message);
                } else {
                    ShowWidgetUtil.showShort(t.toString());
                }
            }
        });
    }


    /**
     * 获取验证码
     *
     * @param params 注册对象
     */
    public void getVerifyCode(UserServerParams params) {
        Call<BaseResp<Object>> call = RetrofitService.getInstance()
                .createApi(false)
                .getVerifyCode(new Gson().toJson(params));
        enqueueClue(call, true, EventBusConfig.GET_VERIFY_CODE);
    }

    /**
     * 验证短信验证码
     *
     * @param params 注册对象
     */
    public void checkCode(UserServerParams params) {
        Call<BaseResp<Object>> call = RetrofitService.getInstance()
                .createApi(false)
                .checkCode(new Gson().toJson(params));
        enqueueClue(call, true, EventBusConfig.CHECK_CODE);
    }


    /**
     * 获取主营产品标签
     */
    public void getMainTag() {
        Call<BaseResp<BaseDataArray<MainProductTagServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .getMainTag();
        enqueueClue(call, true, EventBusConfig.GET_MAIN_TAG);
    }

    /**
     * 注册
     */
    public void register(UserServerParams params) {
        Call<BaseResp<UserServerParams>> call = RetrofitService.getInstance()
                .createApi(false)
                .register(new Gson().toJson(params));
        enqueueClue(call, true, EventBusConfig.REGISTER);
    }

    /**
     * 认证
     */
    public void submitCertification(VerificationServerParams params) {
        Call<BaseResp<Object>> call = RetrofitService.getInstance()
                .createApi(false)
                .submitCertification(new Gson().toJson(params));
        enqueueClue(call, true, EventBusConfig.SUBMIT_CERTIFICATION);
    }

    /**
     * 合并子店铺
     */
    public void submitMergeChild(MergeServerParams params) {
        Call<BaseResp<Object>> call = RetrofitService.getInstance()
                .createApi(false)
                .submitMergeChild(new Gson().toJson(params));
        enqueueClue(call, true, EventBusConfig.SUBMIT_MERGE_CHILD);
    }

    /**
     * 子账户验证
     */
    public void checkChildPhone(UserServerParams params) {
        Call<BaseResp<Object>> call = RetrofitService.getInstance()
                .createApi(false)
                .checkChildPhone(new Gson().toJson(params));
        enqueueClue(call, true, EventBusConfig.CHECK_CHILD_PHONE);
    }

    /**
     * 登录
     */
    public void login(UserServerParams params) {
        Call<BaseResp<BaseDataObject<UserServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .login(new Gson().toJson(params));
        enqueueClue(call, true, EventBusConfig.LOGIN);
    }

    /**
     * 修改密码
     */
    public void changeLoginPwd(ModifyPwdServerParams params) {
        Call<BaseResp<Object>> call = RetrofitService.getInstance()
                .createApi(false)
                .changeLoginPwd(new Gson().toJson(params));
        enqueueClue(call, true, EventBusConfig.CHANGE_LOGIN_PWD);
    }

    /**
     * 修改用户信息
     */
    public void changeUserInfo(UserServerParams params) {
        Call<BaseResp<Object>> call = RetrofitService.getInstance()
                .createApi(false)
                .changeUserInfo(new Gson().toJson(params));
        enqueueClue(call, true, EventBusConfig.CHANGE_USER_INFO);
    }


    /**
     * 修改用户地址
     */
    public void changeAddress(UserServerParams params) {
        Call<BaseResp<Object>> call = RetrofitService.getInstance()
                .createApi(false)
                .changeAddress(new Gson().toJson(params));
        enqueueClue(call, true, EventBusConfig.CHANGE_ADDRESS);
    }


    /**
     * 获取首页信息
     */
    public void getHomeInfo() {
        Call<BaseResp<BaseDataObject<HomeInfoServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .getHomeInfo();
        enqueueClue(call, true, EventBusConfig.GET_HOME_INFO);
    }


    /**
     * 获取运营报表
     */
    public void getOperationReport() {
        Call<BaseResp<BaseDataObject<OperationReportServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .getOperationReport();
        enqueueClue(call, true, EventBusConfig.GET_OPERATION_REPORT);
    }

    /**
     * 获取钱包首页
     */
    public void getMyWalletInfo() {
        Call<BaseResp<BaseDataObject<WalletHomeInfoServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .getMyWalletInfo();
        enqueueClue(call, true, EventBusConfig.GET_MY_WALLET_INFO);
    }

    /**
     * 验证旧手机
     */
    public void checkOldPhone(UserServerParams params) {
        Call<BaseResp<Object>> call = RetrofitService.getInstance()
                .createApi(false)
                .checkOldPhone(new Gson().toJson(params));
        enqueueClue(call, true, EventBusConfig.CHECK_OLD_PHONE);
    }

    /**
     * 修改手机号
     */
    public void changePhone(UserServerParams params) {
        Call<BaseResp<Object>> call = RetrofitService.getInstance()
                .createApi(false)
                .changePhone(new Gson().toJson(params));
        enqueueClue(call, true, EventBusConfig.CHANGE_PHONE);
    }

    /**
     * 忘记密码
     */
    public void forgetPassword(UserServerParams params) {
        Call<BaseResp<Object>> call = RetrofitService.getInstance()
                .createApi(false)
                .forgetPassword(new Gson().toJson(params));
        enqueueClue(call, true, EventBusConfig.FORGET_PWD);
    }

    /**
     * 获取我的用户列表
     */
    public void getCustomerList() {
        Call<BaseResp<BaseDataArray<CustomerServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .getCustomerList();
        enqueueClue(call, true, EventBusConfig.GET_CUSTOMER_LIST);
    }

    /**
     * 获取用户详情
     */
    public void getCustomerDetail(CustomerServerParams params) {
        Call<BaseResp<CustomerDetailDataObject>> call = RetrofitService.getInstance()
                .createApi(false)
                .getCustomerDetail(new Gson().toJson(params));
        enqueueClue(call, true, EventBusConfig.GET_CUSTOMER_DETAIL);
    }

    /**
     * 获取我的收入列表
     */
    public void getMyIncome() {
        Call<BaseResp<BaseDataArray<IncomeOrExpensesServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .getMyIncome();
        enqueueClue(call, true, EventBusConfig.GET_MY_INCOME);
    }

    /**
     * 获取我的支出列表
     */
    public void getMyExpense() {
        Call<BaseResp<BaseDataArray<IncomeOrExpensesServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .getMyExpenses();
        enqueueClue(call, true, EventBusConfig.GET_MY_EXPENSES);
    }

    /**
     * 获取成交额
     */
    public void getTurnOver() {
        Call<BaseResp<BaseDataArray<TurnOverServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .getTurnOver();
        enqueueClue(call, true, EventBusConfig.GET_TURNOVER);
    }


    /**
     * 订单列表
     */
    public void getOrderList(int type) {
        ProductInOrderListServerParams params = new ProductInOrderListServerParams();
        params.setType(type + "");
        Call<BaseResp<OrderDataArray>> call = RetrofitService.getInstance()
                .createApi(false)
                .getOrderList(new Gson().toJson(params));
        enqueueClue(call, true, EventBusConfig.GET_ORDER_LIST);
    }

    /**
     * 店铺访客
     */
    public void getShopVisit() {
        Call<BaseResp<BaseDataArray<ShopVisitorServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .getShopVisit();
        enqueueClue(call, true, EventBusConfig.GET_SHOP_VISIT);
    }

    /**
     * 好友列表
     */
    public void getVisitFriends() {
        Call<BaseResp<WithCountDataArray<VisitFriendsServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .getVisitFriends();
        enqueueClue(call, true, EventBusConfig.GET_VISIT_FRIENDS);
    }


    /**
     * 产品访问
     */
    public void getProductVisit() {
        Call<BaseResp<BaseDataArray<ShopVisitorServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .getProductVisit();
        enqueueClue(call, true, EventBusConfig.GET_PRODUCT_VISIT);
    }

    /**
     * 在售列表
     */
    public void getProductListOnSale() {
        Call<BaseResp<WithCountDataArray<ProductInOrderListServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .getProductListOnSale();
        enqueueClue(call, true, EventBusConfig.GET_PRODUCT_LIST_ON_SALE);
    }

    /**
     * 退货率
     */
    public void getReturnRates() {
        Call<BaseResp<BaseDataArray<ShopVisitorServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .getReturnRates();
        enqueueClue(call, true, EventBusConfig.GET_RETURN_RATES);
    }

    /**
     * 获取评价
     */
    public void getAllEvaluateInReturnRate() {
        Call<BaseResp<BaseDataArray<EvaluateInReturnRateServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .getAllEvaluateInReturnRates();
        enqueueClue(call, true, EventBusConfig.GET_ALL_EVALUATE_IN_RETURN_RATES);
    }

    /**
     * 子账户列表
     */
    public void getSubAccountList() {
        Call<BaseResp<BaseDataArray<SubAccountServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .getSubAccountList();
        enqueueClue(call, true, EventBusConfig.GET_SUB_ACCOUNT_LIST);
    }

    /**
     * 获取子账号详情
     */
    public void getSubAccountDetail(String id) {
        Map map = new HashMap();
        map.put("id", id);
        Call<BaseResp<BaseDataObject<SubAccountServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .getSubAccountDetail(new Gson().toJson(map));
        enqueueClue(call, true, EventBusConfig.GET_SUB_ACCOUNT_DETAIL);
    }

    /**
     * 获取旧手机的验证码
     */
    public void getOldPhoneVerifyCode(String imageCode) {
        Map map = new HashMap();
        map.put("image_code", imageCode);
        Call<BaseResp<Object>> call = RetrofitService.getInstance()
                .createApi(false)
                .getOldPhoneVerifyCode(new Gson().toJson(map));
        enqueueClue(call, true, EventBusConfig.GET_OLD_PHONE_VERIFY_CODE);
    }

    /**
     * 获取旧手机的验证码
     */
    public void sendFeedBack(FeedBackServerParams params) {
        Call<BaseResp<Object>> call = RetrofitService.getInstance()
                .createApi(false)
                .sendFeedBack(new Gson().toJson(params));
        enqueueClue(call, true, EventBusConfig.SEND_FEEDBACK);
    }

}
