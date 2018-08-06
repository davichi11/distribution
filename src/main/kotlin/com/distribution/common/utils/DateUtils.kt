package com.distribution.common.utils

import org.apache.commons.lang.StringUtils
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

/**
 * 日期处理
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年12月21日 下午12:53:33
 */
object DateUtils {

    /**
     * 时间格式(yyyy-MM-dd)
     */
    private const val DATE_PATTERN = "yyyy-MM-dd"
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    private const val DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss"

    /**
     * 转换时间为字符串
     *
     * @param date
     * @param pattern 时间格式
     * @return
     */
    @JvmOverloads
    fun format(date: LocalDate, pattern: String = DATE_PATTERN): String {
        return date.format(DateTimeFormatter.ofPattern(pattern))
    }

    /**
     * 转换时间戳为字符串
     *
     * @param dateTime
     * @param timePattern 时间戳格式
     * @return
     */
    @JvmOverloads
    fun formatDateTime(dateTime: LocalDateTime, timePattern: String = DATE_TIME_PATTERN): String {
        return dateTime.format(DateTimeFormatter.ofPattern(timePattern))
    }

    /**
     * 字符串转换为时间
     *
     * @param date
     * @param datePattern 时间格式
     * @return
     */
    @JvmOverloads
    fun stringFormatDate(date: String, datePattern: String = DATE_PATTERN): LocalDate {
        return if (StringUtils.isNotBlank(date))
            LocalDate.parse(date, DateTimeFormatter.ofPattern(datePattern))
        else
            LocalDate.now()
    }

    /**
     * 字符串转换为时间戳
     *
     * @param dateTiem
     * @param timePattern
     * @return
     */
    fun stringFormatDateTime(dateTiem: String, timePattern: String): LocalDateTime {
        return if (StringUtils.isNotBlank(dateTiem))
            LocalDateTime.parse(dateTiem, DateTimeFormatter.ofPattern(timePattern))
        else
            LocalDateTime.now()
    }

    /**
     * 使用默认的时间格式转换字符串为时间戳
     *
     * @param dateTime
     * @return
     */
    fun stringFormatDateTime(dateTime: String): LocalDateTime {
        return stringFormatDateTime(dateTime, DATE_TIME_PATTERN)
    }

    /**
     * 获取本月的第一天
     *
     * @return
     */
    fun startDateOfMonth(): LocalDate {
        return LocalDate.now().with(TemporalAdjusters.firstDayOfMonth())
    }

    /**
     * 获取本月的最后一天
     * @return
     */
    fun endDateOfMonth(): LocalDate {
        return LocalDate.now().with(TemporalAdjusters.lastDayOfMonth())
    }

}

