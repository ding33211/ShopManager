package com.soubu.goldensteward.module;

/**
 * Created by dingsigang on 16-11-17.
 */

public class BaseEventBusResp {
    int code;
    Object object;

    public BaseEventBusResp(int code, Object object) {
        this.code = code;
        this.object = object;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
