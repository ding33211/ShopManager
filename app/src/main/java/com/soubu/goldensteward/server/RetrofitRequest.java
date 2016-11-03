package com.soubu.goldensteward.server;

import android.util.Log;

import com.google.gson.Gson;
import com.soubu.goldensteward.GoldenStewardApplication;
import com.soubu.goldensteward.module.server.BaseData;
import com.soubu.goldensteward.module.server.BaseResp;
import com.soubu.goldensteward.module.server.LoginServerParams;
import com.soubu.goldensteward.module.server.MainProductTagServerParams;
import com.soubu.goldensteward.module.server.RegisterServerParams;
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
        ShowWidgetUtil.showProgressDialog(GoldenStewardApplication.getContext(), "");
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
                    Log.e(TAG, "errorbody  :   " + response.body().msg);
                    EventBus.getDefault().post(response.code());
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
    public void getVerifyCode(RegisterServerParams params) {
        Call<BaseResp<RegisterServerParams>> call = RetrofitService.getInstance()
                .createApi(false)
                .getVerifyCode(new Gson().toJson(params));
        enqueueClue(call, true);
    }

    /**
     * 验证短信验证码
     *
     * @param params 注册对象
     */
    public void checkCode(RegisterServerParams params) {
        Call<BaseResp<RegisterServerParams>> call = RetrofitService.getInstance()
                .createApi(false)
                .checkCode(new Gson().toJson(params));
        enqueueClue(call, true);
    }


    /**
     * 获取主营产品标签
     */
    public void getMainTag() {
        Call<BaseResp<BaseData<MainProductTagServerParams>>> call = RetrofitService.getInstance()
                .createApi(false)
                .getMainTag();
        enqueueClue(call, true);
    }

    /**
     * 注册
     */
    public void register(RegisterServerParams params) {
        Call<BaseResp<LoginServerParams>> call = RetrofitService.getInstance()
                .createApi(false)
                .register(new Gson().toJson(params));
        enqueueClue(call, true);
    }

}
