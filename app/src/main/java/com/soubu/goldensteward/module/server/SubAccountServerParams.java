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
