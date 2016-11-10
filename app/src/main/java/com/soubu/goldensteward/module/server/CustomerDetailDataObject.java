package com.soubu.goldensteward.module.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dingsigang on 16-11-8.
 */
public class CustomerDetailDataObject extends BaseDataObject<CustomerServerParams> {
    @SerializedName("order")
    @Expose
    ProductInCustomerDetailServerParams[] order;

    public ProductInCustomerDetailServerParams[] getOrder() {
        return order;
    }
}
