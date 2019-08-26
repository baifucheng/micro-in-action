package com.baifc.licenseservice.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * projectName: micro-in-action
 * packageName: com.baifc.licenseservice.utils
 * Created: 2019/8/26.
 * Auther: baifc
 * Description: 拦截传入的http请求，是一个http servlet过滤器
 */
@Slf4j
@Component
public class UserContextFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 拦截所有进入服务的http请求，并将关联ID从HTTP请求映射到UserContext中
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        // 将请求header中的关联值，放入当前线程的UserContext
        UserContextHolder.getUserContext()
                .setCorrelationId(httpServletRequest.getHeader(UserContext.CORRELATION_ID));
        UserContextHolder.getUserContext()
                .setAuthToken(httpServletRequest.getHeader(UserContext.AUTH_TOKEN));
        UserContextHolder.getUserContext()
                .setUserId(httpServletRequest.getHeader(UserContext.USER_ID));
        UserContextHolder.getUserContext()
                .setOrgId(httpServletRequest.getHeader(UserContext.ORG_ID));

        log.info("UserContextFilter Correlation id: {}", UserContextHolder.getUserContext().getCorrelationId());

        filterChain.doFilter(httpServletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
