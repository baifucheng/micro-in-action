//package com.baifc.organizationservice.security;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//
///**
// * projectName: micro-in-action
// * packageName: com.baifc.organizationservice.security
// * Created: 2019/8/30.
// * Auther: baifc
// * Description:
// */
// TODO 该配置是spring security用于服务授权的配置，为了便于测试，将配置注释，若要权限服务支持，需要将该配置打开
//@Configuration
//public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/v1/organizations/**")
//                .hasRole("ADMIN")
//                .anyRequest()
//                .authenticated();
////        super.configure(http);
//    }
//}
