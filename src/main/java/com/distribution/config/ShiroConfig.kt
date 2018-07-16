package com.distribution.config

import com.distribution.modules.sys.oauth2.OAuth2Filter
import com.distribution.modules.sys.oauth2.OAuth2Realm
import org.apache.shiro.mgt.SecurityManager
import org.apache.shiro.session.mgt.SessionManager
import org.apache.shiro.spring.LifecycleBeanPostProcessor
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.web.mgt.DefaultWebSecurityManager
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*
import javax.servlet.Filter

/**
 * Shiro配置
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-04-20 18:33
 */
@Configuration
class ShiroConfig {

    @Bean("sessionManager")
    fun sessionManager(): SessionManager {
        val sessionManager = DefaultWebSessionManager()
        sessionManager.isSessionValidationSchedulerEnabled = true
        sessionManager.isSessionIdUrlRewritingEnabled = false
        //sessionManager.setSessionIdCookieEnabled(false);
        return sessionManager
    }

    @Bean("securityManager")
    fun securityManager(oAuth2Realm: OAuth2Realm, sessionManager: SessionManager): SecurityManager {
        val securityManager = DefaultWebSecurityManager()
        securityManager.setRealm(oAuth2Realm)
        securityManager.sessionManager = sessionManager

        return securityManager
    }

    @Bean("shiroFilter")
    fun shirFilter(securityManager: SecurityManager): ShiroFilterFactoryBean {
        val shiroFilter = ShiroFilterFactoryBean()
        shiroFilter.securityManager = securityManager

        //oauth过滤
        val filters = HashMap<String, Filter>(16)
        filters["oauth2"] = OAuth2Filter()
        shiroFilter.filters = filters

        val filterMap = LinkedHashMap<String, String>()
        filterMap["/webjars/**"] = "anon"
        filterMap["/druid/**"] = "anon"
        filterMap["/api/**"] = "anon"
        filterMap["/cardorderinfo/exportExcel"] = "anon"

        //swagger配置
        filterMap["/swagger**"] = "anon"
        filterMap["/v2/api-docs"] = "anon"
        filterMap["/swagger-resources/configuration/ui"] = "anon"

        filterMap["/sys/login"] = "anon"
        filterMap["/pay"] = "anon"
        filterMap["/**/*.css"] = "anon"
        filterMap["/**/*.js"] = "anon"
        filterMap["/**/*.html"] = "anon"
        filterMap["/**/*.txt"] = "anon"
        filterMap["/fonts/**"] = "anon"
        filterMap["/plugins/**"] = "anon"
        filterMap["/favicon.ico"] = "anon"
        filterMap["/captcha.jpg"] = "anon"
        filterMap["/*.jpg"] = "anon"
        filterMap["/"] = "anon"
        filterMap["/**"] = "oauth2"
        shiroFilter.filterChainDefinitionMap = filterMap

        return shiroFilter
    }

    @Bean("lifecycleBeanPostProcessor")
    fun lifecycleBeanPostProcessor(): LifecycleBeanPostProcessor {
        return LifecycleBeanPostProcessor()
    }

    @Bean
    fun defaultAdvisorAutoProxyCreator(): DefaultAdvisorAutoProxyCreator {
        val proxyCreator = DefaultAdvisorAutoProxyCreator()
        proxyCreator.isProxyTargetClass = true
        return proxyCreator
    }

    @Bean
    fun authorizationAttributeSourceAdvisor(securityManager: SecurityManager): AuthorizationAttributeSourceAdvisor {
        val advisor = AuthorizationAttributeSourceAdvisor()
        advisor.securityManager = securityManager
        return advisor
    }

}
