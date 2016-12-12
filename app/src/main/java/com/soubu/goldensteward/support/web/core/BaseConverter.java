package com.soubu.goldensteward.support.web.core;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 请求-响应转换器
 */
public final class BaseConverter extends Converter.Factory {

    public static BaseConverter create() {
        return new BaseConverter();
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new RequestConverter<>();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new ResponseConverter<>(type);
    }

    /**
     * 请求转换器
     */
    class RequestConverter<T> implements Converter<T, RequestBody> {

        @Override
        public RequestBody convert(T value) throws IOException {
            return new FormBody.Builder()
                    .add("params", JSON.toJSONString(value))
                    .build();
        }
    }

    /**
     * 响应转换器
     */
    class ResponseConverter<T> implements Converter<ResponseBody, T> {
        private final Type type;

        public ResponseConverter(Type type) {
            this.type = type;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            BufferedSource bufferedSource = Okio.buffer(value.source());
            String tempStr = bufferedSource.readUtf8();
            bufferedSource.close();
            return JSON.parseObject(tempStr, type);
        }
    }
}
