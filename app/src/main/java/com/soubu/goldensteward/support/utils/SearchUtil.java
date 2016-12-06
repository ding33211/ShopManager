package com.soubu.goldensteward.support.utils;


/**
 * 查找类工具
 * Created by dingsigang on 16-8-31.
 */
public class SearchUtil {

    /**
     * 在数组中找index
     * @param arrays
     * @param object
     * @return  默认返回0
     */
    public static int searchInArray(Object[] arrays, Object object){
        int index = 0;
        if(arrays == null){
            return 0;
        }
        for(Object o : arrays){
            if(o.equals(object)){
                return index;
            } else {
                index++;
            }
        }
        return 0;
    }

}
