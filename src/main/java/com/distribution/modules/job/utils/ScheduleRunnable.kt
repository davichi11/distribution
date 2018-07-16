package com.distribution.modules.job.utils

import com.distribution.common.exception.RRException
import com.distribution.common.utils.ApplicationContextHolder
import org.apache.commons.lang.StringUtils
import org.springframework.util.ReflectionUtils

import java.lang.reflect.Method

/**
 * 执行定时任务
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年11月30日 下午12:49:33
 */
class ScheduleRunnable @Throws(NoSuchMethodException::class, SecurityException::class)
constructor(beanName: String, methodName: String, private val params: String) : Runnable {
    private val target: Any = ApplicationContextHolder.getBean(beanName)
    private var method: Method? = null

    init {

        if (StringUtils.isNotBlank(params)) {
            this.method = target.javaClass.getDeclaredMethod(methodName, String::class.java)
        } else {
            this.method = target.javaClass.getDeclaredMethod(methodName)
        }
    }

    override fun run() {
        try {
            ReflectionUtils.makeAccessible(method!!)
            if (StringUtils.isNotBlank(params)) {
                method!!.invoke(target, params)
            } else {
                method!!.invoke(target)
            }
        } catch (e: Exception) {
            throw RRException("执行定时任务失败", e)
        }

    }

}
