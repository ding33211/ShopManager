package com.soubu.goldensteward.support.web.core;


import com.soubu.goldensteward.support.base.BaseApplication;
import com.soubu.goldensteward.support.constant.SpKey;
import com.soubu.goldensteward.support.utils.DeviceUtil;
import com.soubu.goldensteward.support.utils.SPUtil;
import com.soubu.goldensteward.support.utils.VersionUtil;

import lombok.Data;

/**
 * 默认的Header
 * <p>
 * 作者：余天然 on 2016/12/7 下午3:46
 */
@Data
public class BaseHeader {

    private String os;
    private String device;
    private String token;
    private String version;

    public BaseHeader() {
        os = "Android";
        device = DeviceUtil.getDeviceIMEI(BaseApplication.getContext());
        token = SPUtil.getValue(SpKey.TOKEN, "");
        version = VersionUtil.getVersionName(BaseApplication.getContext());
    }

}
