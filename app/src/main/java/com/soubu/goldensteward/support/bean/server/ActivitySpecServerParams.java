package com.soubu.goldensteward.support.bean.server;

import lombok.Data;

/**
 * Created by dingsigang on 16-12-6.
 */
@Data
public class ActivitySpecServerParams {
    String url;
    String name;
    String sign_up_time;
    String sign_end_time;
    String activity_start_time;
    String activity_end_time;
    Content[] content;

    @Data
    public class Content {
        String title;
        String content;
    }
}
