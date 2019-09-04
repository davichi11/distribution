package com.distribution.common.utils

import org.apache.commons.lang3.StringUtils
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import java.util.*

/**
 * @author ChunLiang Hu
 * @Company
 * @Project gxph-loan
 * @Package com.gxph.common.utils
 
 * @create 2018/4/27-11:24
 */
object CommonUtils {

    /**
     * markdown解析器
     */
    private val parser = Parser.builder().build()

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
        return when {
            StringUtils.isBlank(idCode) -> ""
            else -> StringUtils.substring(idCode, 0, 3) + "***********" + StringUtils.substring(idCode, idCode.length - 3, idCode.length)
        }
    }

    fun fuzzyMobile(mobile: String): String {
        return when {
            StringUtils.isBlank(mobile) -> ""
            else -> StringUtils.substring(mobile, 0, 3) + "*****" + StringUtils.substring(mobile, mobile.length - 3, mobile.length)
        }

    }

    fun mdToHtml(value: String): String {
        return when {
            value.isEmpty() -> ""
            else -> {
                val s = value.replace("<!--more-->", "\r\n")
                val document = parser.parse(s)
                val renderer = HtmlRenderer.builder().build()
                renderer.render(document)
            }
        }
    }


}
