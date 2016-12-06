package com.soubu.goldensteward.support.bean.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dingsigang on 16-11-9.
 */
public class OrderDataArray extends BaseDataArray<OrderServerParams> {
    @SerializedName("wait_pay")
    @Expose
    String wait_pay;

    @SerializedName("wait_send")
    @Expose
    String wait_send;

    @SerializedName("other")
    @Expose
    String other;

    public String getWait_pay() {
        return wait_pay;
    }

    public void setWait_pay(String wait_pay) {
        this.wait_pay = wait_pay;
    }

    public String getWait_send() {
        return wait_send;
    }

    public void setWait_send(String wait_send) {
        this.wait_send = wait_send;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
