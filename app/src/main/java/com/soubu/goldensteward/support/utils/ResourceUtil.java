package com.soubu.goldensteward.support.utils;

import java.io.File;
import java.net.URISyntaxException;

/**
 * 作者：余天然 on 2016/12/8 上午9:46
 */
public class ResourceUtil {

    private static ResourceUtil instance;

    public static ResourceUtil getInstance() {
        if (instance == null) {
            instance = new ResourceUtil();
        }
        return instance;
    }

    //读取本地文件
    public String read(String dirName, String fileName) {
        String msg = "";
        try {
            String dir = getClass().getResource("/" + dirName).toURI().getPath();
            return FileUtil.readFile(dir + File.separator + fileName, "UTF-8").toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return msg;
    }
}
