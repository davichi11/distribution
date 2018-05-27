package com.distribution.weixin.wxpay.utils;


import java.security.MessageDigest;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MD5Util {

    private static String byteArrayToHexString(byte b[]) {
        return IntStream.range(0, b.length).mapToObj(i -> byteToHexString(b[i])).collect(Collectors.joining());
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }

    static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname)) {
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetname)));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return resultString;
    }

    private static final String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

}
