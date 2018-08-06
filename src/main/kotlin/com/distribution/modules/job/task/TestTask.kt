package com.distribution.modules.job.task

import com.distribution.modules.sys.entity.SysUserEntity
import com.distribution.modules.sys.service.SysUserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * 测试定时任务(演示Demo，可删除)
 *
 *
 * testTask为spring bean的名称
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年11月30日 下午1:34:24
 */
@Component("testTask")
class TestTask {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    private val sysUserService: SysUserService? = null

    fun test(params: String) {
        logger.info("我是带参数的test方法，正在被执行，参数为：$params")

        try {
            Thread.sleep(1000L)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        val user = sysUserService!!.queryObject(1L)
        println(user.toString())

    }


    fun test2() {
        logger.info("我是不带参数的test2方法，正在被执行")
    }
}
