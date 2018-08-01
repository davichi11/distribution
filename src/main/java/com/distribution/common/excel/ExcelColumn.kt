package com.distribution.common.excel

import java.lang.annotation.Documented

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.common.excel
 * @Description TODO(描述)
 * @create 2018/7/31-23:00
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Documented
annotation class ExcelColumn(val value: String = "",
                             /**
                              * 从1开始
                              * @return
                              */
                             val col: Int = 0)
