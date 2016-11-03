package com.soubu.goldensteward.module.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lakers on 16/11/2.
 */

public class BaseData<T> {

    @SerializedName("data")
    @Expose
    T[] data;

    public T[] getData() {
        return data;
    }
}
