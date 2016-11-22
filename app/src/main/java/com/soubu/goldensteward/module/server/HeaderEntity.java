package com.soubu.goldensteward.module.server;

import android.util.Log;

import com.soubu.goldensteward.GoldenStewardApplication;
import com.soubu.goldensteward.utils.AppUtil;
import com.soubu.goldensteward.utils.PhoneUtil;

/**
 * Created by lakers on 16/11/1.
 */

public class HeaderEntity {
    private String os;
    private String device;
    private String token;
    private String version;

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public HeaderEntity() {
        os = "Android";
        device = PhoneUtil.getDeviceIMEI(GoldenStewardApplication.getContext());
        token = GoldenStewardApplication.getContext().getToken();
        version = AppUtil.getVersionName(GoldenStewardApplication.getContext()) + "";
    }
}
