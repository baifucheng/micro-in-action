package com.baifc.zuulservice.filter;

import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

/**
 * projectName: spring-micro-in-action
 * packageName: com.baifc.zuulservice.filter
 * Created: 2019-08-26.
 * Auther: baifc
 * Description:
 */
@Component
public class FilterUtils {

    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String AUTH_TOKEN     = "Authorization";
    public static final String USER_ID        = "tmx-user-id";
    public static final String ORG_ID         = "tmx-org-id";
    public static final String PRE_FILTER_TYPE = "pre";
    public static final String POST_FILTER_TYPE = "post";
    public static final String ROUTE_FILTER_TYPE = "route";

    /**
     * 获取HTTP首部的CORRELATION_ID
     * @return
     */
    public String getCorrelationId() {
        RequestContext ctx = RequestContext.getCurrentContext();

        // 首先检查传入请求的HTTP首部是否设置了tmx-correlation-id
        if (ctx.getRequest().getHeader(CORRELATION_ID) != null) {
            return ctx.getRequest().getHeader(CORRELATION_ID);
        } else {
            return ctx.getZuulRequestHeaders().get(CORRELATION_ID);
        }
    }

    /**
     * 添加HTTP首部映射
     *  Zuul不允许直接添加或修改传入请求中的HTTP部首，若要向HTTP请求首部添加值时，应该使用RequestContext中的addZuulRequestHeader方法
     *  addZuulRequestHeader方法维护一个单独的HTTP首部映射，这个映射实在请求通过Zuul服务器径流这些过滤器的时候添加的
     *  当Zuul服务器调用目标服务时，包含在ZuulRequestHeader映射中的数据将被合并
     * @param correlationId
     */
    public void setCorrelationId(String correlationId) {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(CORRELATION_ID, correlationId);
    }

    public final String getAuthToken(){
        RequestContext ctx = RequestContext.getCurrentContext();
        return ctx.getRequest().getHeader(AUTH_TOKEN);
    }
}
