package com.soubu.goldensteward.module;

import com.soubu.goldensteward.utils.CharacterParser;
import com.soubu.goldensteward.utils.PinyinLetterInterface;

import java.util.Date;

/**
 * Created by lakers on 16/10/29.
 */

public class CustomersModule implements PinyinLetterInterface {
    String imgUrl;
    String name;
    int dealCount;
    Date lastDealTime;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDealCount() {
        return dealCount;
    }

    public void setDealCount(int dealCount) {
        this.dealCount = dealCount;
    }

    public Date getLastDealTime() {
        return lastDealTime;
    }

    public void setLastDealTime(Date lastDealTime) {
        this.lastDealTime = lastDealTime;
    }

    @Override
    public String getLetter() {
        return Character.toString(CharacterParser.getInstance().getSelling(name).charAt(0)).toUpperCase();
    }
}
