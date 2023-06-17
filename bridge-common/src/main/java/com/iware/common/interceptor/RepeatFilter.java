package com.iware.common.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xiaoh
 * @ClassName ChannelFilter
 * @Date 2021/3/25 下午2:22
 * @Version 1.0
 */
public class RepeatFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(RepeatFilter.class);

    private static final String HTTPS = "HTTPS";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Do nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("================进入过滤器======================");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        ServletRequest requestWrapper = new RequestWrapper(httpServletRequest);
        String scheme = request.getScheme();
        logger.info("====> schema is: {}", scheme);
        if (!StringUtils.isEmpty(scheme) && HTTPS.equalsIgnoreCase(scheme)) {
            httpServletResponse.setHeader("Set-Cookie", "JSESSIONID=" + httpServletRequest.getSession().getId() + "; Path=/cas;HttpOnly=true;Secure=true;");
        }
        chain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {
        // Do nothing
    }
}
