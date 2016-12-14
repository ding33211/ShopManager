package com.soubu.goldensteward.support.bean.server;

import com.soubu.goldensteward.support.utils.CharacterParser;
import com.soubu.goldensteward.support.utils.PinyinLetterInterface;

import lombok.Data;

/**
 * Created by dingsigang on 16-12-12.
 */
@Data
public class StoreContactServerParams implements PinyinLetterInterface {
    private int user_id;
    private String name;
    private String portrait;
    private String phone;
    private String nick_name;
    private String sort;
    private int role;
    private int level;
    private int certification_type;
    private int type;
    private int identity;
    private int operation_mode;
    private int contact_type;
    private int relation_type;
    private String portrait_name;

    @Override
    public String getLetter() {
        return Character.toString(CharacterParser.getInstance().getSelling(name).charAt(0)).toUpperCase();

    }
}
