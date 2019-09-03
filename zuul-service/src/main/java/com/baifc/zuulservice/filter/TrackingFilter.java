package com.baifc.zuulservice.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import jdk.internal.instrumentation.ClassInstrumentation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * projectName: spring-micro-in-action
 * packageName: com.baifc.zuulservice.filter
 * Created: 2019-08-26.
 * Auther: baifc
 * Description: zuul前置过滤器 在zuul将实际请求发送到目的之前被调用
 *  该过滤器将检查所有到网关的传入请求，并确定请求中是否存在名为tmx-correlation-id的HTTP部首
 *  tmx-correlation-id将包含一个唯一的全局通用ID，可以跨多个微服务来跟踪用户请求
 */
@Slf4j
@Component
public class TrackingFilter extends ZuulFilter {

    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;

    @Autowired
    private FilterUtils filterUtils;

    /**
     * 告诉zuul，该过滤器是一个前置过滤器、路由过滤器，还是后置过滤器
     * @return
     */
    @Override
    public String filterType() {
        return FilterUtils.PRE_FILTER_TYPE;
    }

    /**
     * filterOrder方法返回整数值，指示不同类型的过滤器的执行顺序
     * @return
     */
    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    /**
     * 返回一个boolean来指示该过滤器是否执行
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    /**
     * run方法是每次服务通过过滤器时执行的代码
     *  该方法中，会检查tmx-correlation-id是否存在，如果不存在，则生成一个关联值，并设置http首部tmx-correlation-id
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        if (filterUtils.getCorrelationId() != null) {
            log.info("tmx-correlation-id found in tracking filter: {}. ", filterUtils.getCorrelationId());
        } else {
            filterUtils.setCorrelationId(generaterCorrelationId());
            log.info("tmx-correlation-id generated in tracking filter: {}.", filterUtils.getCorrelationId());
        }
        RequestContext ctx = RequestContext.getCurrentContext();

        log.debug("Processing incoming request for {}.",  ctx.getRequest().getRequestURI());

        log.debug("--auther is " + getAuther());

        return null;
    }

    private String generaterCorrelationId() {
        return UUID.randomUUID().toString();
    }

    private String getAuther() {
        String result = "";
        String token = filterUtils.getAuthToken();
        log.debug("token = " + token);
        if (token != null) {
            if (!token.toLowerCase().startsWith("bearer".toLowerCase())) {
                return result;
            }
            String authToken = filterUtils.getAuthToken().substring("bearer".length()).trim();

            Claims claims = Jwts.parser()
                    .setSigningKey("baifc".getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(authToken)
                    .getBody();

            return (String)claims.get("auther");
        }
        return result;
    }
}
