package com.soubu.goldensteward.server;

import android.util.Log;

import com.google.gson.Gson;
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
        ShowWidgetUtil.showProgressDialog(dialogContent);
        call.enqueue(new Callback<BaseResp<T>>() {
            @Override
            public void onResponse(Call<BaseResp<T>> call, Response<BaseResp<T>> response) {
                Log.e(TAG, "1111111111111");
                if (response.body().status != HttpURLConnection.HTTP_OK) {
//                    BufferedReader reader = null;
//                    StringBuilder sb = new StringBuilder();
//                    try {
//                        reader = new BufferedReader(new InputStreamReader(response.errorBody().byteStream()));
//                        String line;
//                        try {
//                            while ((line = reader.readLine()) != null) {
//                                sb.append(line);
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    String finallyError = sb.toString();
                    Log.e(TAG, "errorBody  :   " + response.body().msg + "   status  :  " + response.body().status);
                    ShowWidgetUtil.showShort(response.body().msg);
//                    EventBus.getDefault().post(response.body());
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
    public void checkOldPhone() {
        Call<BaseResp<BaseDataObject<WalletHomeInfoServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .getMyWalletInfo();
        enqueueClue(call, true);
    }

}
