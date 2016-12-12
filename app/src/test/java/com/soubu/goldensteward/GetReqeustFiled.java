package com.soubu.goldensteward;

import com.soubu.goldensteward.support.utils.ResourceUtil;

import org.junit.Test;

/**
 * 作者：余天然 on 2016/12/9 上午10:49
 */
public class GetReqeustFiled {

    @Test
    public void testResources() {
        String res = ResourceUtil.getInstance().read("response", "login.txt");
        System.out.println(res);
    }
}
