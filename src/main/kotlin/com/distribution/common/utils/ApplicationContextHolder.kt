package com.distribution.common.utils

import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

/**
 * @author ChunLiang Hu
 * @Company
 * @Project renren-fast
 * @Package com.distribution.common.utils
 
 * @create 2017/7/28-07:46
 */
@Component
class ApplicationContextHolder : ApplicationContextAware {

    @Throws(BeansException::class)
    override fun setApplicationContext(context: ApplicationContext) {
        if (applicationContext != null) {
            throw IllegalStateException("ApplicationContextHolder already holded 'applicationContext'.")
        }
        applicationContext = context
    }

    companion object {

        private var applicationContext: ApplicationContext? = null

        private fun getApplicationContext(): ApplicationContext {

            return applicationContext
                    ?: throw IllegalStateException("'applicationContext' property is null,ApplicationContextHolder not yet init.")
        }

        fun getBean(beanName: String): Any {
            return getApplicationContext().getBean(beanName)
        }

        fun <T> getBean(beanName: String, tClass: Class<T>): T {
            return getApplicationContext().getBean(beanName, tClass)
        }

        fun cleanHolder() {
            applicationContext = null
        }
    }
}
