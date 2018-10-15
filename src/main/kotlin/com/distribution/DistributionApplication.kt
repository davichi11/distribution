package com.distribution

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation
import com.alicp.jetcache.anno.config.EnableMethodCache
import com.spring4all.swagger.EnableSwagger2Doc
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import springfox.documentation.swagger2.annotations.EnableSwagger2

/**
 * spring_boot 启动类
 *
 * @author huchunliang
 */
@EnableSwagger2Doc
@EnableSwagger2
@EnableMethodCache(basePackages = ["com.distribution"])
@EnableCreateCacheAnnotation
@SpringBootApplication
class DistributionApplication

fun main(args: Array<String>) {
    runApplication<DistributionApplication>()
}

