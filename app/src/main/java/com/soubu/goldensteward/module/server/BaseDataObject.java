package com.soubu.goldensteward.module.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lakers on 16/11/4.
 */

public class BaseDataObject<T> {
    @SerializedName("data")
    @Expose
    T data;

    public T getData() {
        return data;
    }

    @SerializedName("token")
    @Expose
    String token;

    public String getToken() {
        return token;
    }
}
