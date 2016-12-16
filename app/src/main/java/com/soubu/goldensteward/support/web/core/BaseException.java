package com.soubu.goldensteward.support.web.core;

/**
 * 网络异常
 * <p>
 * 作者：余天然 on 16/9/16 下午6:18
 */
public class BaseException extends RuntimeException {

    int errorCode;//0、系统错误，其它、业务错误

    public BaseException(String detailMessage) {
        super(detailMessage);
    }

    public BaseException(int errorCode, String detailMessage) {
        super(detailMessage);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
