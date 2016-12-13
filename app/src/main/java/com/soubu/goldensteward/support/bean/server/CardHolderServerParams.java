package com.soubu.goldensteward.support.bean.server;

import java.util.List;

import lombok.Data;

/**
 * Created by dingsigang on 16-12-12.
 */

@Data
public class CardHolderServerParams {

    private String card_id;
    private String name;
    private String remark;
    private String company;
    private int contact_status;
    private List<Tag> tag = null;

    @Data
    public class Tag {
        private String name;
        private int type;
    }
}
