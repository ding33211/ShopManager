package com.soubu.goldensteward.support.constant;


import com.soubu.goldensteward.BuildConfig;

public class ApiConfig {

    public static boolean IS_PRODUCT_ENV = BuildConfig.IS_PRODUCT_ENV;   //是否是生产环境（正式服）

//        public static String API_HOST = IS_PRODUCT_ENV ? "http://www.isoubu.net/ShopManager/Api/" : "http://www.isoubu.cn/ShopManager/Api/";
    public static String API_HOST = IS_PRODUCT_ENV ? "https://proxy.isoubu.com/ShopManager/Api/" : "https://proxy.isoubu.com/ShopManager/Api/";

}
