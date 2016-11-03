package com.soubu.goldensteward.module.server;

import android.text.TextUtils;

/**
 * Created by lakers on 16/11/3.
 */

public class VerificationServerParams {

    String com_type;
    String file_type;
    String company;
    String person;
    String id_num;
    String phone;
    String business_img;
    String code_img;
    String hold_img;
    String up_img;
    String down_img;

    public String getCom_type() {
        return com_type;
    }

    public void setCom_type(String com_type) {
        this.com_type = com_type;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getId_num() {
        return id_num;
    }

    public void setId_num(String id_num) {
        this.id_num = id_num;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBusiness_img() {
        return business_img;
    }

    public void setBusiness_img(String business_img) {
        this.business_img = business_img;
    }

    public String getCode_img() {
        return code_img;
    }

    public void setCode_img(String code_img) {
        this.code_img = code_img;
    }

    public String getHold_img() {
        return hold_img;
    }

    public void setHold_img(String hold_img) {
        this.hold_img = hold_img;
    }

    public String getUp_img() {
        return up_img;
    }

    public void setUp_img(String up_img) {
        this.up_img = up_img;
    }

    public String getDown_img() {
        return down_img;
    }

    public void setDown_img(String down_img) {
        this.down_img = down_img;
    }

    //增量复制
    public void deltaCopy(VerificationServerParams params){
        if(!TextUtils.isEmpty(params.com_type)){
            com_type = params.com_type;
        }
        if(!TextUtils.isEmpty(params.file_type)){
            file_type = params.file_type;
        }
        if(!TextUtils.isEmpty(params.company)){
            company = params.company;
        }
        if(!TextUtils.isEmpty(params.person)){
            person = params.person;
        }
        if(!TextUtils.isEmpty(params.id_num)){
            id_num = params.id_num;
        }
        if(!TextUtils.isEmpty(params.phone)){
            phone = params.phone;
        }
        if(!TextUtils.isEmpty(params.business_img)){
            business_img = params.business_img;
        }
        if(!TextUtils.isEmpty(params.code_img)){
            code_img = params.code_img;
        }
        if(!TextUtils.isEmpty(params.hold_img)){
            hold_img = params.hold_img;
        }
        if(!TextUtils.isEmpty(params.up_img)){
            up_img = params.up_img;
        }
        if(!TextUtils.isEmpty(params.down_img)){
            down_img = params.down_img;
        }

    }
}
