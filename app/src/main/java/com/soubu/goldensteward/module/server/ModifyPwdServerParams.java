package com.soubu.goldensteward.module.server;

/**
 * Created by lakers on 16/11/4.
 */

public class ModifyPwdServerParams {
    String old_password;
    String new_password;

    public String getOld_password() {
        return old_password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }
}
