package com.distribution.config

import com.google.code.kaptcha.impl.DefaultKaptcha
import com.google.code.kaptcha.util.Config
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*


/**
 * 生成验证码配置
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-04-20 19:22
 */
@Configuration
class CaptchaConfig {

    @Bean
    fun producer(): DefaultKaptcha {
        val properties = Properties()
        properties["kaptcha.border"] = "no"
        properties["kaptcha.textproducer.font.color"] = "black"
        properties["kaptcha.textproducer.char.space"] = "5"
        val config = Config(properties)
        val defaultKaptcha = DefaultKaptcha()
        defaultKaptcha.config = config
        return defaultKaptcha
    }
}
