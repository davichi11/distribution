package com.distribution.common.excel

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.common.excel
 
 * @create 2018/7/31-23:00
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class ExcelColumn(val value: String = "",
                             /**
                              * 从1开始
                              * @return
                              */
                             val col: Int = 0)
