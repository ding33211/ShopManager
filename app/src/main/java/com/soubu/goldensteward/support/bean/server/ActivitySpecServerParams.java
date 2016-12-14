package com.soubu.goldensteward.support.bean.server;

import lombok.Data;

/**
 * Created by dingsigang on 16-12-6.
 */
@Data
public class ActivitySpecServerParams {
    String id;
    String detail_img;
    String active_name;
    String sign_up_start_time;
    String sign_up_end_time;
    String active_start_time;
    String active_end_time;
    String active_rule;
    String sign_introduce;
//    Content[] content;



    @Data
    public class Content {
        String title;
        String content;
    }
}
