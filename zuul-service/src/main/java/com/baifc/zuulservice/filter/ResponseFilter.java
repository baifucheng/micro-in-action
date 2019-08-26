package com.baifc.zuulservice.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * projectName: spring-micro-in-action
 * packageName: com.baifc.zuulservice.filter
 * Created: 2019-08-26.
 * Auther: baifc
 * Description: Zuul后置过滤器
 *  该方法将使用zuul后置过滤器将关联ID注入http响应首部中，该http响应首部传回给服务调用者
 */
@Slf4j
@Component
public class ResponseFilter extends ZuulFilter {

    @Autowired
    private FilterUtils filterUtils;

    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;

    @Override
    public String filterType() {
        // 后置过滤器类型需要设置为"post"
        return FilterUtils.POST_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx =  RequestContext.getCurrentContext();
        log.debug("Adding the correlation id to the outbound headers. {}", filterUtils.getCorrelationId());

        // 注入原始Http请求中传入的，或在zuul网关中生成的关联ID注入至响应中
        ctx.getResponse().addHeader(FilterUtils.CORRELATION_ID, filterUtils.getCorrelationId());
        return null;
    }
}
