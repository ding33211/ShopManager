package com.soubu.goldensteward.support.utils;


import com.soubu.goldensteward.R;

public class DrawableUtils {
    public static int getAvatarColor(String name) {
        switch (name.toCharArray()[0] % 7) {
            case 0:
                return R.drawable.avatar_1;
            case 1:
                return R.drawable.avatar_2;
            case 2:
                return R.drawable.avatar_3;
            case 3:
                return R.drawable.avatar_4;
            case 4:
                return R.drawable.avatar_5;
            case 5:
                return R.drawable.avatar_6;
            case 6:
                return R.drawable.avatar_7;
            default:
                return R.drawable.avatar_1;
        }
    }
}
