package com.soubu.goldensteward.support.bean.server;

import lombok.Data;

/**
 * Created by dingsigang on 16-12-5.
 */
@Data
public class MyActivityServerParams {
    int id;
    String active_name;
    String sign_up_end_time;
    String active_start_time;
    String active_end_time;
    int status;

}
