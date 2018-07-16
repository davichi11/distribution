package com.distribution.common.utils

import org.apache.commons.lang.StringUtils
import java.util.*

/**
 * @author ChunLiang Hu
 * @Company
 * @Project gxph-loan
 * @Package com.gxph.common.utils
 * @Description TODO(描述)
 * @create 2018/4/27-11:24
 */
object CommonUtils {

    /**
     * 生成32位的UUID
     *
     * @return
     */
    val uuid: String
        get() = UUID.randomUUID().toString().replace("-", "")

    /**
     * 获取4位随机验证码
     *
     * @return
     */
    // for (int i = 0; i < 6; i++) {
    val random: String
        get() {
            val sb = StringBuilder()
            (0..3).forEach { _ ->
                val s = Math.floor(Math.random() * 9 + 1).toInt().toString()
                sb.append(s)
            }
            return sb.toString()
        }

    /**
     * 模糊身份证
     *
     * @param idCode
     * @return
     */
    fun fuzzyIdCode(idCode: String): String {
        return if (StringUtils.isBlank(idCode)) {
            ""
        } else StringUtils.substring(idCode, 0, 3) + "***********" + StringUtils.substring(idCode, idCode.length - 3, idCode.length)
    }

    fun fuzzyMobile(mobile: String): String {
        return if (StringUtils.isBlank(mobile)) {
            ""
        } else StringUtils.substring(mobile, 0, 3) + "*****" + StringUtils.substring(mobile, mobile.length - 3, mobile.length)

    }


}
