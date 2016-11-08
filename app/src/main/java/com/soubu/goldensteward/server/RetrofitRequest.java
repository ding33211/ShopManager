package com.soubu.goldensteward.server;

import android.util.Log;

import com.google.gson.Gson;
import com.soubu.goldensteward.GoldenStewardApplication;
import com.soubu.goldensteward.R;
import com.soubu.goldensteward.module.server.BaseDataArray;
import com.soubu.goldensteward.module.server.BaseDataObject;
import com.soubu.goldensteward.module.server.BaseResp;
import com.soubu.goldensteward.module.server.CustomerDeailDataObject;
import com.soubu.goldensteward.module.server.CustomerServerParams;
import com.soubu.goldensteward.module.server.HomeInfoServerParams;
import com.soubu.goldensteward.module.server.IncomeOrExpensesServerParams;
import com.soubu.goldensteward.module.server.MainProductTagServerParams;
import com.soubu.goldensteward.module.server.MergeServerParams;
import com.soubu.goldensteward.module.server.ModifyPwdServerParams;
import com.soubu.goldensteward.module.server.OperationReportServerParams;
import com.soubu.goldensteward.module.server.TurnOverServerParams;
import com.soubu.goldensteward.module.server.UserServerParams;
import com.soubu.goldensteward.module.server.VerificationServerParams;
import com.soubu.goldensteward.module.server.WalletHomeInfoServerParams;
import com.soubu.goldensteward.utils.PhoneUtil;
import com.soubu.goldensteward.utils.ShowWidgetUtil;

import org.greenrobot.eventbus.EventBus;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private <T> void enqueueClue(Call<BaseResp<T>> call, final boolean needEventPost) {
        enqueueClue(call, needEventPost, null);
    }


    private <T> void enqueueClue(Call<BaseResp<T>> call, final boolean needEventPost, String dialogContent) {
        if(!PhoneUtil.isConnected(GoldenStewardApplication.getContext())){
            ShowWidgetUtil.showShort(R.string.please_check_internet);
            return;
        }
        ShowWidgetUtil.showProgressDialog(dialogContent, R.style.LoadingProgressTheme);
        call.enqueue(new Callback<BaseResp<T>>() {
            @Override
            public void onResponse(Call<BaseResp<T>> call, Response<BaseResp<T>> response) {
                Log.e(TAG, "1111111111111");
                if (response.body().status != HttpURLConnection.HTTP_OK) {
                    Log.e(TAG, "errorBody  :   " + response.body().msg + "   status  :  " + response.body().status);
                    ShowWidgetUtil.showShort(response.body().msg);
                    if (needEventPost) {
                        EventBus.getDefault().post(response.body().status);
                    }
                } else {
                    if (needEventPost) {
                        EventBus.getDefault().post(response.body());
                    }
                }
                ShowWidgetUtil.dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<BaseResp<T>> call, Throwable t) {
                Log.e(TAG, "1111111111111" + t.toString());
                ShowWidgetUtil.dismissProgressDialog();
                ShowWidgetUtil.showShort(t.toString());
            }
        });
    }


    /**
     * 获取验证码
     *
     * @param params 注册对象
     */
    public void getVerifyCode(UserServerParams params) {
        Call<BaseResp<UserServerParams>> call = RetrofitService.getInstance()
                .createApi(false)
                .getVerifyCode(new Gson().toJson(params));
        enqueueClue(call, true);
    }

    /**
     * 验证短信验证码
     *
     * @param params 注册对象
     */
    public void checkCode(UserServerParams params) {
        Call<BaseResp<UserServerParams>> call = RetrofitService.getInstance()
                .createApi(false)
                .checkCode(new Gson().toJson(params));
        enqueueClue(call, true);
    }


    /**
     * 获取主营产品标签
     */
    public void getMainTag() {
        Call<BaseResp<BaseDataArray<MainProductTagServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .getMainTag();
        enqueueClue(call, true);
    }

    /**
     * 注册
     */
    public void register(UserServerParams params) {
        Call<BaseResp<UserServerParams>> call = RetrofitService.getInstance()
                .createApi(false)
                .register(new Gson().toJson(params));
        enqueueClue(call, true);
    }

    /**
     * 认证
     */
    public void submitCertification(VerificationServerParams params) {
        Call<BaseResp<VerificationServerParams>> call = RetrofitService.getInstance()
                .createApi(false)
                .submitCertification(new Gson().toJson(params));
        enqueueClue(call, true);
    }

    /**
     * 合并子店铺
     */
    public void submitMergeChild(MergeServerParams params) {
        Call<BaseResp<MergeServerParams>> call = RetrofitService.getInstance()
                .createApi(false)
                .submitMergeChild(new Gson().toJson(params));
        enqueueClue(call, true);
    }

    /**
     * 登录
     */
    public void login(UserServerParams params) {
        Call<BaseResp<BaseDataObject<UserServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .login(new Gson().toJson(params));
        enqueueClue(call, true);
    }

    /**
     * 修改密码
     */
    public void modifyLoginPwd(ModifyPwdServerParams params) {
        Call<BaseResp<ModifyPwdServerParams>> call = RetrofitService.getInstance()
                .createApi(false)
                .changeLoginPwd(new Gson().toJson(params));
        enqueueClue(call, true);
    }

    /**
     * 修改用户信息
     */
    public void changeUserInfo(UserServerParams params) {
        Call<BaseResp<UserServerParams>> call = RetrofitService.getInstance()
                .createApi(false)
                .changeUserInfo(new Gson().toJson(params));
        enqueueClue(call, true);
    }


    /**
     * 修改用户地址
     */
    public void changeAddress(UserServerParams params) {
        Call<BaseResp<UserServerParams>> call = RetrofitService.getInstance()
                .createApi(false)
                .changeAddress(new Gson().toJson(params));
        enqueueClue(call, true);
    }


    /**
     * 获取首页信息
     */
    public void getHomeInfo() {
        Call<BaseResp<BaseDataObject<HomeInfoServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .getHomeInfo();
        enqueueClue(call, true);
    }


    /**
     * 获取运营报表
     */
    public void getOperationReport() {
        Call<BaseResp<BaseDataObject<OperationReportServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .getOperationReport();
        enqueueClue(call, true);
    }

    /**
     * 获取运营报表
     */
    public void getMyWalletInfo() {
        Call<BaseResp<BaseDataObject<WalletHomeInfoServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .getMyWalletInfo();
        enqueueClue(call, true);
    }

    /**
     * 验证旧手机
     */
    public void checkOldPhone(UserServerParams params) {
        Call<BaseResp<BaseDataObject<UserServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .checkOldPhone(new Gson().toJson(params));
        enqueueClue(call, true);
    }

    /**
     * 修改手机号
     */
    public void changePhone(UserServerParams params) {
        Call<BaseResp<BaseDataObject<UserServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .changePhone(new Gson().toJson(params));
        enqueueClue(call, true);
    }

    /**
     * 忘记密码
     */
    public void forgetPassword(UserServerParams params) {
        Call<BaseResp<BaseDataObject<UserServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .forgetPassword(new Gson().toJson(params));
        enqueueClue(call, true);
    }

    /**
     * 获取我的用户列表
     */
    public void getCustomerList() {
        Call<BaseResp<BaseDataArray<CustomerServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .getCustomerList();
        enqueueClue(call, true);
    }

    /**
     * 获取用户详情
     */
    public void getCustomerDetail(CustomerServerParams params) {
        Call<BaseResp<CustomerDeailDataObject>> call = RetrofitService.getInstance()
                .createApi(false)
                .getCustomerDetail(new Gson().toJson(params));
        enqueueClue(call, true);
    }

    /**
     * 获取我的收入列表
     */
    public void getMyIncome() {
        Call<BaseResp<BaseDataArray<IncomeOrExpensesServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .getMyIncome();
        enqueueClue(call, true);
    }

    /**
     * 获取我的支出列表
     */
    public void getMyExpense() {
        Call<BaseResp<BaseDataArray<IncomeOrExpensesServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .getMyExpenses();
        enqueueClue(call, true);
    }

    /**
     * 获取成交额
     */
    public void getTurnOver() {
        Call<BaseResp<BaseDataArray<TurnOverServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .getTurnOver();
        enqueueClue(call, true);
    }

}
