package com.baifc.licenseservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * projectName: spring-micro-in-action
 * packageName: com.baifc.licenseservice.config
 * Created: 2019-08-18.
 * Auther: baifc
 * Description:
 */
@Configuration
public class RestConfig {

    // spring cloud早期版本中默认自动支持Ribbon，从Angel版本之后，需要用@LoadBalanced显式标志支持Ribbon
    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
