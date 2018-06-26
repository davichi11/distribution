package com.distribution.common.xss;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * XSS过滤
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-04-01 10:20
 */
public class XssFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader( "Pragma", "no-cache" );
        httpServletResponse.addHeader( "Cache-Control", "must-revalidate" );
        httpServletResponse.addHeader( "Cache-Control", "no-cache" );
        httpServletResponse.addHeader( "Cache-Control", "no-store" );
        httpServletResponse.setDateHeader("Expires", 0);
        chain.doFilter(xssRequest, httpServletResponse);
    }

    @Override
    public void destroy() {
    }

}