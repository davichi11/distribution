package com.distribution.common.xss

import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * XSS过滤
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-04-01 10:20
 */
class XssFilter : Filter {

    @Throws(ServletException::class)
    override fun init(config: FilterConfig) {
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val xssRequest = XssHttpServletRequestWrapper(request as HttpServletRequest)
        val httpServletResponse = response as HttpServletResponse
        httpServletResponse.setHeader("Pragma", "no-cache")
        httpServletResponse.addHeader("Cache-Control", "must-revalidate")
        httpServletResponse.addHeader("Cache-Control", "no-cache")
        httpServletResponse.addHeader("Cache-Control", "no-store")
        httpServletResponse.setDateHeader("Expires", 0)
        chain.doFilter(xssRequest, httpServletResponse)
    }

    override fun destroy() {}

}