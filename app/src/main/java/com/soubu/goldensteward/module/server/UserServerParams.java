package com.soubu.goldensteward.module.server;

import java.io.Serializable;

/**
 * Created by lakers on 16/10/31.
 */

public class UserServerParams extends BaseDeltaCopyParams implements Serializable {
    String name;
    String portrait;
    String phone;
    String main_product;
    String province;
    String province_id;
    String city;
    String city_id;
    String address;
    String contact_name;
    String job;
    String company;
    String company_size;
    String operation_mode;
    String mail;
    String main_industry;
    String turnover;
    String fixed_telephone;
    String company_profile;
    String certification;
    String fail_cause;
    String child_status;
    String token;
    String code;
    String password;

    //此type仅仅用来区别发送验证码的type，若后续被字段覆盖，则单独抽出验证码module
    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getChild_status() {
        return child_status;
    }

    public void setChild_status(String child_status) {
        this.child_status = child_status;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getFail_cause() {
        return fail_cause;
    }

    public void setFail_cause(String fail_cause) {
        this.fail_cause = fail_cause;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMain_product() {
        return main_product;
    }

    public void setMain_product(String main_product) {
        this.main_product = main_product;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String detail_address) {
        this.address = detail_address;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany_size() {
        return company_size;
    }

    public void setCompany_size(String company_size) {
        this.company_size = company_size;
    }

    public String getOperation_mode() {
        return operation_mode;
    }

    public void setOperation_mode(String operation_mode) {
        this.operation_mode = operation_mode;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMain_industry() {
        return main_industry;
    }

    public void setMain_industry(String main_industry) {
        this.main_industry = main_industry;
    }

    public String getTurnover() {
        return turnover;
    }

    public void setTurnover(String turnover) {
        this.turnover = turnover;
    }

    public String getFixed_telephone() {
        return fixed_telephone;
    }

    public void setFixed_telephone(String fixed_telephone) {
        this.fixed_telephone = fixed_telephone;
    }

    public String getCompany_profile() {
        return company_profile;
    }

    public void setCompany_profile(String company_profile) {
        this.company_profile = company_profile;
    }

}
