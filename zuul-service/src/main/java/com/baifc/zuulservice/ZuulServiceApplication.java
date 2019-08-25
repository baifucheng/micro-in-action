package com.baifc.zuulservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Zuul将自动使用Eureka来通过服务ID查找服务，然后使用Ribbon，对来自Zuul的请求进行客户端的负载均衡
 *
 * 访问http://localhost:5555/actuator/routes可以看到所有服务的映射列表
 */
@SpringBootApplication
// 使服务称为一个Zuul服务器
@EnableZuulProxy
public class ZuulServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulServiceApplication.class, args);
    }

}
