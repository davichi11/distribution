package com.distribution.weixin.wxpay.utils;


import java.security.MessageDigest;
import java.util.Random;
import java.util.SortedMap;

/**
 * '============================================================================
 * 'api说明：
 * 'createSHA1Sign创建签名SHA1
 * 'getSha1()Sha1签名
 * '============================================================================
 * '
 */
public class Sha1Util {

    public static String getNonceStr() {
        Random random = new Random();
        return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "UTF-8");
    }

    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /**
     * 创建签名SHA1
     * @param signParams
     * @return
     * @throws Exception
     */
    public static String createSHA1Sign(SortedMap<String, String> signParams) throws Exception {
        StringBuffer sb = new StringBuffer();
        //要采用URLENCODER的原始值！
        signParams.forEach((k, v) -> sb.append(k).append("=").append(v).append("&"));
        String params = sb.substring(0, sb.lastIndexOf("&"));
//		System.out.println("sha1之前:" + params);
//		System.out.println("SHA1签名为："+getSha1(params));
        return getSha1(params);
    }

    /**
     * Sha1签名
     * @param str
     * @return
     */
    private static String getSha1(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("GBK"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }
}
