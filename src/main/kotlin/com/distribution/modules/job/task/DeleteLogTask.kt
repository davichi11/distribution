package com.distribution.modules.job.task

import com.distribution.common.utils.DateUtils
import com.distribution.modules.sys.service.SysLogService
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDate

/**
 * @author ChunLiang Hu
 * @Company
 * @Project renren-fast
 * @Package com.distribution.modules.job.task
 * @Description TODO(描述)
 * @create 2017/10/24-09:40
 */

@Component("delete_log")
class DeleteLogTask {
    @Autowired
    private lateinit var logService: SysLogService
    private val log = LoggerFactory.getLogger(DeleteLogTask::class.java)

    /**
     * 定时删除日志任务
     *
     * @param params
     */
    fun deleteLog(params: String) {
        var beginDate = LocalDate.now().minusMonths(1)
        var endDate = LocalDate.now()
        if (params.contains(",")) {
            val strings = params.split(",").dropLastWhile { it.isEmpty() }.toTypedArray()
            //提取开始时间并转换
            beginDate = strings.filter { s -> s.toUpperCase().contains("BEGINDATE") }.map { convertToLocalDate(it) }[0]
            //提取结束时间并转换
            endDate = strings.filter { s -> s.toUpperCase().contains("ENDDATE") }.map { convertToLocalDate(it) }[0]
        }
        try {
            logService.deleteByParams(mapOf("beginDate" to beginDate, "endDate" to endDate))
        } catch (e: Exception) {
            log.error("删除日志异常", e)
            logService.saveErrorLog(params)
        }

    }

    /**
     * 转换日期时间
     *
     * @param date
     * @return
     */
    private fun convertToLocalDate(date: String): LocalDate {
        if (StringUtils.isBlank(date)) {
            return LocalDate.now()
        }
        var separator = ""
        if (date.contains(":")) {
            separator = ":"
        }
        if (date.contains("=")) {
            separator = "="
        }
        return DateUtils.stringFormatDate(StringUtils.substringAfter(date, separator))
    }

}
