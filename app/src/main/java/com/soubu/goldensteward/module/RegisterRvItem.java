package com.soubu.goldensteward.module;

import android.text.InputType;

/**
 * Created by lakers on 16/10/27.
 */

public class RegisterRvItem {
    int titleRes;
    int type;
    String content;
    boolean must;
    int editType = InputType.TYPE_CLASS_TEXT;
    int arrayRes;
    int webArrayRes;

    public int getTitleRes() {
        return titleRes;
    }

    public void setTitleRes(int titleRes) {
        this.titleRes = titleRes;
    }

    public int getArrayRes() {
        return arrayRes;
    }

    public void setArrayRes(int arrayRes) {
        this.arrayRes = arrayRes;
    }

    public int getWebArrayRes() {
        return webArrayRes;
    }

    public void setWebArrayRes(int webArrayRes) {
        this.webArrayRes = webArrayRes;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isMust() {
        return must;
    }

    public void setMust(boolean must) {
        this.must = must;
    }

    public int getEditType() {
        return editType;
    }

    public void setEditType(int editType) {
        this.editType = editType;
    }
}
