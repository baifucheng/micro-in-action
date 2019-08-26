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
 * Description:
 */
//public class UserContextInterceptor implements ClientHttpRequestInterceptor {
//    @Override
//    public ClientHttpResponse intercept(HttpRequest httpRequest,
//                                        byte[] bytes,
//                                        ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
//
//        HttpHeaders headers = httpRequest.getHeaders();
//        headers.add(UserContext.CORRELATION_ID, );
//        return null;
//    }
//}
