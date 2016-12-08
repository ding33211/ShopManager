package com.soubu.goldensteward.support.web.core;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Header的OkHttp拦截器
 * <p>
 * 作者：余天然 on 2016/12/7 下午3:44
 */
public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        String agent = JSON.toJSONString(new BaseHeader());
        Request request = chain.request()
                .newBuilder()
                .header("SHOP_MANAGER_AGENT", agent)
                .build();
        return chain.proceed(request);
    }
}
