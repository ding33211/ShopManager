package com.soubu.goldensteward.support.bean.server;

import lombok.Data;

/**
 * 分页请求的基类
 * <p>
 * 从1开始
 * <p>
 * 作者：余天然 on 2016/12/14 下午6:19
 */
@Data
public class BasePageRequest {
    private int page = 1;
    private int size = 20;

    public BasePageRequest(int page) {
        this.page = page;
    }
}
