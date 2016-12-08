package com.soubu.goldensteward.support.web;

import lombok.Data;

/**
 * 作者：余天然 on 2016/12/7 下午2:35
 */
@Data
public class LoginResponse {

    String name;
    String portrait;
    String phone;
    String main_product;
    String province;
    String province_id;
    String city;
    String city_id;
    String address;
    String contact_name;
    String job;
    String company;
    String company_size;
    String operation_mode;
    String mail;
    String main_industry;
    String turnover;
    String fixed_telephone;
    String company_profile;
    String certification;
    String fail_cause;
    String child_status;
    String token;
    String code;
    String password;
    String image_code;
    //此type仅仅用来区别发送验证码的type，若后续被字段覆盖，则单独抽出验证码module
    String type;
}
