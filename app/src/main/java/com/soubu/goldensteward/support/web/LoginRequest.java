package com.soubu.goldensteward.support.web;

import lombok.Data;

/**
 * 作者：余天然 on 2016/12/7 下午2:35
 */
@Data
public class LoginRequest {
    String phone;
    String password;

    public LoginRequest(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }
}
