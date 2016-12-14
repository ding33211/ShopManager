package com.soubu.goldensteward.support.bean.server;

import lombok.Data;

/**
 * 报名活动页面的产品
 * Created by dingsigang on 16-12-13.
 */
@Data
public class ProductInSignUpActivityServerParams {
    String pro_id;
    String cover;
    String name;
    String price;
    String unit;
    String cut_price;
    String cut_units;
}
