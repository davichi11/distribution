package com.distribution.weixin.wxpay.utils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.IntStream;


public class TenpayUtil {

    private static Object Server;
    private static String QRfromGoogle;

    /**
     * 把对象转换成字符串
     *
     * @param obj
     * @return String 转换成字符串,若对象为null,则返回空字符串.
     */
    public static String toString(Object obj) {
        if (obj == null) {
            return "";
        }

        return obj.toString();
    }


    /**
     * 取出一个指定长度大小的随机正整数.
     *
     * @param length int 设定所取出随机数的长度。length小于11
     * @return int 返回生成的随机数。
     */
    public static int buildRandom(int length) {
        int num;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        num = IntStream.range(0, length).map(i -> 10).reduce(1, (a, b) -> a * b);
        return (int) ((random * num));
    }

    /**
     * 获取编码字符集
     *
     * @param request
     * @param response
     * @return String
     */

    static String getCharacterEncoding(HttpServletRequest request, HttpServletResponse response) {

        if (null == request || null == response) {
            return "gbk";
        }

        String enc = request.getCharacterEncoding();
        if (null == enc || "".equals(enc)) {
            enc = response.getCharacterEncoding();
        }

        if (null == enc || "".equals(enc)) {
            enc = "gbk";
        }

        return enc;
    }

}
	
	










