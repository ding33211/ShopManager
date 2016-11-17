package com.soubu.goldensteward.module.server;

import com.soubu.goldensteward.utils.CharacterParser;
import com.soubu.goldensteward.utils.PinyinLetterInterface;

import java.io.Serializable;

/**
 * Created by dingsigang on 16-11-8.
 */
public class CustomerServerParams extends BaseDeltaCopyParams implements PinyinLetterInterface, Serializable {
    String uid;
    String name;
    String portrait;
    String order_count;
    String add_time;
    String sort;
    String role;
    String take_person;
    String contact_phone;
    String address;
    //子账户ID
    String child_id;
    //用户身份
    String certification_type;

    public String getCertification_type() {
        return certification_type;
    }

    public void setCertification_type(String certification_type) {
        this.certification_type = certification_type;
    }

    public String getChild_id() {
        return child_id;
    }

    public void setChild_id(String child_id) {
        this.child_id = child_id;
    }

    public String getTake_person() {
        return take_person;
    }

    public void setTake_person(String take_person) {
        this.take_person = take_person;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {

        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getOrder_count() {
        return order_count;
    }

    public void setOrder_count(String order_count) {
        this.order_count = order_count;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public String getLetter() {
        return Character.toString(CharacterParser.getInstance().getSelling(name).charAt(0)).toUpperCase();

    }
}
