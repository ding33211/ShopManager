package com.soubu.goldensteward.module.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dingsigang on 16-11-8.
 */
public class CustomerDeailDataObject extends BaseDataObject<CustomerServerParams> {
    @SerializedName("order")
    @Expose
    OrderServerParams[] order;

    public OrderServerParams[] getOrder() {
        return order;
    }
}
