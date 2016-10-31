package com.soubu.goldensteward.utils;

import java.util.Comparator;

/**
 * Created by lakers on 16/9/16.
 */
public class PinyinComparator implements Comparator<PinyinLetterInterface> {

    @Override
    public int compare(PinyinLetterInterface o1, PinyinLetterInterface o2) {
        String o1Letter = o1.getLetter();
        String o2Letter = o2.getLetter();
        return o1Letter.compareTo(o2Letter);
    }
}
