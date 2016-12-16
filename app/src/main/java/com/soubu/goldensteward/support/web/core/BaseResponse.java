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

        //报名详情中的审核状态
        int status;

        //报名详情中的失败原因
        String fail_cause;

        //报名详情中的活动id
        int active_id;

        //报名详情中的报名状态
        int sign_up_status;
    }


}