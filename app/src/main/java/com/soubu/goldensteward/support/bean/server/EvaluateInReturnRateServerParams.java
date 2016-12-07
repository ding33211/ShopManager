package com.soubu.goldensteward.support.bean.server;

import lombok.Data;

/**
 * Created by dingsigang on 16-11-18.
 */
@Data
public class EvaluateInReturnRateServerParams {
    String re_id;
    String rating;
    String content;
    String add_time;
    String has_reply;
    String name;
    String portrait;
    String certification_type;
    String level;
    String role;
    String identity;
    String operation_mode;
    String reply;
    ImageServerParams[] imgList;
    String title;
    String pic;
    String type;
    String message;

}
