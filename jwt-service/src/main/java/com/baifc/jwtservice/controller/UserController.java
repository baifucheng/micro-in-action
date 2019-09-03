package com.baifc.jwtservice.controller;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * projectName: spring-micro-in-action
 * packageName: com.baifc.authenticationservice.controller
 * Created: 2019-08-29.
 * Auther: baifc
 * Description: JWT不需要提供user端点
 */
//@RestController
//public class UserController {
//
//    /**
//     * 映射到"/auth/user"端点，当试图访问由OAuth2保护的服务时，将会用到该端点
//     * @param user
//     * @return
//     */
//    @RequestMapping(value = "/user", produces = "application/json")
//    public Map<String, Object> user(OAuth2Authentication user) {
//        Map<String, Object> userInfo = new HashMap<>();
//
//        userInfo.put("user", user.getUserAuthentication().getPrincipal());
//        userInfo.put("authorities", AuthorityUtils.authorityListToSet(user.getUserAuthentication().getAuthorities()));
//
//        System.out.println(userInfo);
//        return userInfo;
//    }
//}
