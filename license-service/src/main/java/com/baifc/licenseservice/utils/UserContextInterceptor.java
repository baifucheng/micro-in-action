package com.baifc.licenseservice.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * projectName: micro-in-action
 * packageName: com.baifc.licenseservice.utils
 * Created: 2019/8/26.
 * Auther: baifc
 * Description: 将关联ID注入基于HTTP的传出服务请求中，这些服务由RestTemplate实例执行，可以连理服务之间调用的关系
 *  为了使用UserContextInterceptor，需要我们定义一个RestTemplate的bean，然后将UserContextInterceptor添加进去
 */
public class UserContextInterceptor implements ClientHttpRequestInterceptor {

    /**
     * 该方法在RestTemplate发生实际的Http服务调用之前被调用
     * @param httpRequest
     * @param bytes
     * @param clientHttpRequestExecution
     * @return
     * @throws IOException
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest,
                                        byte[] bytes,
                                        ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {

        HttpHeaders headers = httpRequest.getHeaders();
        // 为传出的服务调用准备HTTP请求首部，并添加存储在UserContext中的关联ID
        headers.add(UserContext.CORRELATION_ID, UserContextHolder.getUserContext().getCorrelationId());
        headers.add(UserContext.AUTH_TOKEN, UserContextHolder.getUserContext().getAuthToken());
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}
