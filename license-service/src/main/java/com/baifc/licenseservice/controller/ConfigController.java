package com.baifc.licenseservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * projectName: spring-micro-in-action
 * packageName: com.baifc.licenseservice.controller
 * Created: 2019-08-14.
 * Auther: baifc
 * Description:
 */
@RestController
@RequestMapping("/config")
// http://localhost:8080/actuator/refresh 刷新该类下的所有配置
@RefreshScope
public class ConfigController {

    @Value("${example.property}")
    private String exampleProperty;

    @GetMapping("/")
    public String getExampleProperty() {
        return exampleProperty;
    }
}
