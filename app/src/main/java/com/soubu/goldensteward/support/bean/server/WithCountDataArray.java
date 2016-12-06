package com.soubu.goldensteward.support.bean.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dingsigang on 16-11-9.
 */
public class WithCountDataArray<T> extends BaseDataArray<T>{
    @SerializedName("count")
    @Expose
    String count;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
