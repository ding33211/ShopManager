package com.soubu.goldensteward.module.server;

import java.io.Serializable;

/**
 * Created by lakers on 16/10/31.
 */

public class RegisterServerParams implements Serializable{
    String name;
    String phone;
    String password;
    String code;
    String main_product;
    String province;
    String province_id;
    String city;
    String city_id;
    String detail_address;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getDetail_address() {
        return detail_address;
    }

    public void setDetail_address(String detail_address) {
        this.detail_address = detail_address;
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
