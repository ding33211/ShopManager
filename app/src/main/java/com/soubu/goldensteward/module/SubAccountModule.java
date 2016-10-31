package com.soubu.goldensteward.module;

import com.soubu.goldensteward.utils.CharacterParser;
import com.soubu.goldensteward.utils.PinyinLetterInterface;

/**
 * Created by lakers on 16/10/31.
 */

public class SubAccountModule implements PinyinLetterInterface{
    public static final int TYPE_NORMAL = 0x00;
    public static final int TYPE_REVIEW = 0x01;


    int type;
    String name;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getLetter() {
        return Character.toString(CharacterParser.getInstance().getSelling(name).charAt(0)).toUpperCase();
    }
}
