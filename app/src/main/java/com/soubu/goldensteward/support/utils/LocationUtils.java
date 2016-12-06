package com.soubu.goldensteward.support.utils;

import android.content.Context;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

/**
 * @author sean
 *         <p>
 *         获取地理位置信息
 */
public class LocationUtils {

    //
    private static LocationUtils location;

    private LocationClient locationClient;
    // 设置为百度定位
    private String tempcoor = "gcj02";
    // 设置为高精度定位
    private LocationMode tempMode = LocationMode.Hight_Accuracy;
    //
    private BDLocationListener locationListener;
    //
    public boolean isGetLoaction = false;

    private LocationClientOption option;
    private Object objLock = new Object();


    public static LocationUtils getInstance(Context context) {
        if (location == null) {
            location = new LocationUtils(context);
        }
        return location;
    }

    /**
     * @param context
     */
    private LocationUtils(Context context) {
        synchronized (objLock) {
            locationClient = new LocationClient(context.getApplicationContext());
            InitLocation();
        }
    }

    /**
     * 獲取到的地址位置信息
     *
     * @return
     */
    public void getLocation(BDLocationListener Listener) {

        this.locationListener = Listener;
        // 注册监听
        if (locationListener != null && locationClient != null) {
            locationClient.registerLocationListener(locationListener);
        }
    }

    public void unregisterListener(BDLocationListener listener){
        if(listener != null){
            locationClient.unRegisterLocationListener(listener);
        }
    }

    /**
     * 开始获取地址位置
     */
    public void start() {
        synchronized (objLock) {
            if(locationClient != null && !locationClient.isStarted()){
                locationClient.start();
            }
        }
    }

    /**
     * 停止获取地址信息
     */
    public void stop() {
        synchronized (objLock) {
            if(locationClient != null && locationClient.isStarted()){
                locationClient.stop();
            }
        }
    }



    /**
     * 設置獲取信息類型
     */
    private void InitLocation() {

        option = new LocationClientOption();
        option.setLocationMode(tempMode);// 设置定位模式
        option.setCoorType(tempcoor);// 返回的定位结果是百度经纬度，默认值gcj02
        //int span = 5000;
        option.setScanSpan(0);// 设置发起定位请求的间隔时间为5000ms;	现在改为只定位1次
        option.setIsNeedAddress(true);
        locationClient.setLocOption(option);
    }

    public String getLoactionMode() {

        return option.getLocationMode().toString();
    }

    public void onDestroy() {

        if (locationClient != null && locationListener != null) {
            locationClient.unRegisterLocationListener(locationListener);
        }
    }
}
