package com.distribution.common.utils;

import org.apache.commons.lang.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project gxph-loan
 * @Package com.gxph.common.utils
 * @Description TODO(描述)
 * @create 2018/4/27-11:24
 */
public final class CommonUtils {
    private CommonUtils() {
    }

    /**
     * 生成32位的UUID
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取4位随机验证码
     *
     * @return
     */
    public static String getRandom() {
        // for (int i = 0; i < 6; i++) {
        return IntStream.range(0, 4).mapToObj(i -> String.valueOf((int) Math.floor(Math.random() * 9 + 1)))
                .collect(Collectors.joining());
    }

    /**
     * 模糊身份证
     *
     * @param idCode
     * @return
     */
    public static String fuzzyIdCode(String idCode) {
        if (StringUtils.isBlank(idCode)) {
            return "";
        }
        return StringUtils.substring(idCode, 0, 3) + "***********" + StringUtils.substring(idCode, idCode.length() - 3, idCode.length());
    }

    public static String fuzzyMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return "";
        }
        return StringUtils.substring(mobile, 0, 3) + "*****" + StringUtils.substring(mobile, mobile.length() - 3, mobile.length());

    }


}
