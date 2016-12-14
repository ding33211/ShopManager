package com.soubu.goldensteward.support.bean.server;

import java.util.List;

import lombok.Data;

/**
 * 报名类
 * Created by dingsigang on 16-12-13.
 */
@Data
public class SignUpServerParams {
    private String uid;
    private String active_id;
    private List<String> product_list;
}
