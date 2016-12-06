package com.soubu.goldensteward.support.bean.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dingsigang on 16-8-24.
 */
public class BaseResp<T> {

    @SerializedName("result")
    @Expose
    private T result;

    /**
     * @return The result
     */
    public T getResult() {
        return result;
    }

    @SerializedName("status")
    @Expose
    public int status;

    @SerializedName("msg")
    @Expose
    public String msg;

    @SerializedName("sec")
    @Expose
    public String sec;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
