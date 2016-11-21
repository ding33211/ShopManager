package com.soubu.goldensteward.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import jp.wasabeef.glide.transformations.internal.Utils;

import static android.support.v7.widget.StaggeredGridLayoutManager.TAG;
import static com.baidu.location.h.j.S;
import static java.lang.Math.log;
import static java.lang.Math.pow;

/**
 * 用于各种转换的工具
 * Created by dingsigang on 16-8-2.
 */
public class ConvertUtil {
    /**
     * sp转px
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转sp
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * MD5加密
     */
    public static String encryptMD5(String data) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return new BigInteger(md5.digest(data.getBytes())).toString(16);
    }

    /**
     * SHA加密
     */
    public static String encryptSHA(String data) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA");
        return new BigInteger(sha.digest(data.getBytes())).toString(32);
    }


    public static String dateToYYYY_MM_DD(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    public static String dateToMMPointDD(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("MM.dd");
        return formatter.format(date);
    }


    public static String dateToCustom(Date date, String format){
        if (date == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public static String dateToYYYY_MM_DD_HH_mm(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        return formatter.format(date);
    }

    public static String dateToYYYY_MM_DD_HH_mm_ss(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        return formatter.format(date);
    }

    public static String dateToYYYY_MM_DD_EEEE(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd  EEEE");
        return formatter.format(date);
    }

    public static String dateToHH_mm(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(date);
    }

    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des.toUpperCase();
    }


//    public static String hmacsha256(HttpUrl url, String macKey) {
//        try {
//            Mac mac = Mac.getInstance("HmacSHA256");
//            byte[] secretByte = macKey.getBytes("UTF-8");
//            String macData = "";
//            if (url != null && url.queryParameterNames().size() > 1) {
//                List<String> list = new ArrayList<>(url.queryParameterNames());
//                Collections.sort(list);
//                for (String a : list) {
//                    macData += a + "=" + url.queryParameter(a) + "&";
//                }
//                macData = macData.substring(0, macData.length() - 1);
//            } else {
//                macData = url.encodedQuery() == null ? "" : url.encodedQuery();
//            }
//            //此处需要将中文解码之后进行加密
//            String newData = URLDecoder.decode(macData);
//            byte[] dataBytes = newData.getBytes("UTF-8");
//            Log.e("xxxxxxxxxxxxxxx", "newData   :   " + newData + "      key   :   " + macKey);
//            SecretKey secret = new SecretKeySpec(secretByte, "HMACSHA256");
//            mac.init(secret);
//            byte[] doFinal = mac.doFinal(dataBytes);
//            Log.e("xxxxxxxxxxxxxxx", "result   :   " + bytes2Hex(doFinal));
//
//            return bytes2Hex(doFinal);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    }


    public static String hmacsha256(Map<String, String> map, String macKey) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            byte[] secretByte = macKey.getBytes("UTF-8");
            String macData = "";
            if (map != null && map.size() > 0) {
                List<String> list = new ArrayList<>(map.keySet());
                Collections.sort(list);
                for (String a : list) {
                    macData += a + "=" + map.get(a) + "&";
                }
                macData = macData.substring(0, macData.length() - 1);
            }
            byte[] dataBytes = macData.getBytes("UTF-8");
            Log.e("xxxxxxxxxxxxxxx", "data   :   " + macData + "      key   :   " + macKey);
            SecretKey secret = new SecretKeySpec(secretByte, "HMACSHA256");
            mac.init(secret);
            byte[] doFinal = mac.doFinal(dataBytes);
//            byte[] hexB = new Hex().encode(doFinal);
            Log.e("xxxxxxxxxxxxxxx", "result   :   " + bytes2Hex(doFinal));
            return bytes2Hex(doFinal);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * Find the Greatest Common Denominator.
     * https://en.wikipedia.org/wiki/Euclidean_algorithm
     *
     * @param min Minimum value
     * @param max Maximum value
     * @return Greatest common denominator
     */
    public static int GCD(int min, int max) {
        return max == 0 ? min : GCD(max, min % max);
    }


    public static String uriToPath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


    /**
     * 由最小值和最大值以及刻度数算出单位刻度值
     *
     * @param dMin
     * @param dMax
     * @param iMaxAxisNum
     * @return
     */
    public static int regulateSpace(double dMin, double dMax, int iMaxAxisNum) {
        if (iMaxAxisNum < 1 || dMax <= dMin)
            return 1;

        double dDelta = dMax - dMin;
        if (dDelta < 1.0) //Modify this by your requirement.
        {
            dMax += (1.0 - dDelta) / 2.0;
            dMin -= (1.0 - dDelta) / 2.0;
        }
        dDelta = dMax - dMin;

        int iExp = (int) (log(dDelta) / log(10.0)) - 2;
        double dMultiplier = pow(10, iExp);
        double dSolutions[] = {1, 2, 2.5, 5, 10, 20, 25, 50, 100, 200, 250, 500};
        int i;
        for (i = 0; i < dSolutions.length; i++) {
            double dMultiCal = dMultiplier * dSolutions[i];
            if (((int) (dDelta / dMultiCal) + 1) <= iMaxAxisNum) {
                break;
            }
        }
        double dInterval = dMultiplier * dSolutions[i];
        return (int) dInterval;
    }

}
