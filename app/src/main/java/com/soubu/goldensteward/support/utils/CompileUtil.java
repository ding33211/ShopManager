package com.soubu.goldensteward.support.utils;

import android.text.TextUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dingsigang on 16-8-25.
 */
public class CompileUtil {


    public static Map<String, String> compile(Object oldObject,
                                              Object newObject) {
        Class<Object> cOld = (Class<Object>) oldObject.getClass();
        Field[] filesDb = cOld.getDeclaredFields();
        Class<Object> cNew = (Class<Object>) newObject.getClass();
        Map<String, String> changedMap = new HashMap<String, String>();
        for (Field field : filesDb) {
            String getMethodName = "get"
                    + field.getName().substring(0, 1).toUpperCase()
                    + field.getName().substring(1);
            try {
                Method mOld = cOld.getMethod(getMethodName);
                Method mNew = cNew.getMethod(getMethodName);
                Object valOld = mOld.invoke(oldObject);
                Object valNew = mNew.invoke(newObject);
                if (valOld != null) {
                    if(valNew == null){
                        valNew = "";
                    }
                    if (!valOld.equals(valNew)) {
                        String newString = String.valueOf(valNew);
                        changedMap.put(field.getName(), newString);
                    }
                } else {
                    if(valNew != null && !TextUtils.equals("", valNew.toString())){
                        changedMap.put(field.getName(), String.valueOf(valNew));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        //customer,deal,user获得object类型,上传需要字符传类型
//        if(changedMap.containsKey("customerId")){
//            changedMap.put("customer", changedMap.get("customerId"));
//            changedMap.remove("customerId");
//        }
//        if(changedMap.containsKey("dealId")){
//            changedMap.put("deal", changedMap.get("dealId"));
//            changedMap.remove("dealId");
//        }
//        if(changedMap.containsKey("userId")){
//            changedMap.put("user", changedMap.get("userId"));
//            changedMap.remove("userId");
//        }
        return changedMap;
    }
}
