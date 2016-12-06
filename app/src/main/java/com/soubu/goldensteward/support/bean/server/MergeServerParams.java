package com.soubu.goldensteward.support.bean.server;

/**
 * Created by lakers on 16/11/3.
 */

public class MergeServerParams {
    String main_phone;
    String[] child_phone;

    public String getMain_phone() {
        return main_phone;
    }

    public void setMain_phone(String main_phone) {
        this.main_phone = main_phone;
    }

    public String[] getChild_phone() {
        return child_phone;
    }

    public void setChild_phone(String[] child_phone) {
        this.child_phone = child_phone;
    }
}
