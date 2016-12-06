package com.soubu.goldensteward.support.bean.server;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by lakers on 16/11/5.
 */

public abstract class BaseDeltaCopyParams {


    //增量复制
    public void deltaCopy(BaseDeltaCopyParams params) {
        try {
            // 获取源对象类型
            Class<?> clazz = params.getClass();
//            // 获取源对象构造函数
//            Constructor<?> constructor = clazz.getConstructor();
//            // 实例化出目标对象
//            Object objDes = constructor.newInstance();
            // 获得源对象所有属性
            Field[] fields = clazz.getDeclaredFields();
            // 遍历所有属性
            for (int i = 0; i < fields.length; i++) {
                // 属性对象
                Field field = fields[i];
                // 属性名
                String fieldName = field.getName();
                // 获取属性首字母
                String firstLetter = fieldName.substring(0, 1).toUpperCase();
                // 拼接get方法名如getName
                String getMethodName = "get" + firstLetter + fieldName.substring(1);
                // 得到get方法对象
                Method getMethod = clazz.getMethod(getMethodName);
                // 对源对象调用get方法获取属性值
                Object value = getMethod.invoke(params);
                if (value != null) {
                    // 拼接set方法名
                    String setMethodName = "set" + firstLetter + fieldName.substring(1);
                    // 获取set方法对象
                    Method setMethod = clazz.getMethod(setMethodName, new Class[]{field.getType()});
                    // 对目标对象调用set方法装入属性值
                    setMethod.invoke(this, new Object[]{value});
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
