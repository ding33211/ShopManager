package com.soubu.goldensteward.support.web.core;

import lombok.Data;

/**
 * 网络请求的基类
 *
 * @param <T>
 */
@Data
public class BaseResponse<T> {

    private Entity<T> result;

    public int status;

    public String msg;

    public String sec;

    @Data
    public class Entity<T> {
        T data;

        String token;
    }


}