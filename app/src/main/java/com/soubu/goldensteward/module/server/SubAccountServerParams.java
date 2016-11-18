package com.soubu.goldensteward.module.server;

import com.soubu.goldensteward.utils.CharacterParser;
import com.soubu.goldensteward.utils.PinyinLetterInterface;

/**
 * Created by dingsigang on 16-11-17.
 */

public class SubAccountServerParams implements PinyinLetterInterface {
    String user_id;
    String name;
    String portrait;
    String sort;
    String phone;
    String income;
    String order_count;
    String offer_count;
    String[] week_login;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getOrder_count() {
        return order_count;
    }

    public void setOrder_count(String order_count) {
        this.order_count = order_count;
    }

    public String getOffer_count() {
        return offer_count;
    }

    public void setOffer_count(String offer_count) {
        this.offer_count = offer_count;
    }

    public String[] getWeek_login() {
        return week_login;
    }

    public void setWeek_login(String[] week_login) {
        this.week_login = week_login;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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
