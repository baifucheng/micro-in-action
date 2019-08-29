package com.baifc.authenticationservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * projectName: spring-micro-in-action
 * packageName: com.baifc.authenticationservice.security
 * Created: 2019-08-29.
 * Auther: baifc
 * Description: 定义通过OAuth2验证服务注册哪些应用程序
 *  AuthorizationServerConfigurerAdapter是spring security的核心部分，它提供了执行管件的验证和授权功能的基本机制
 */
@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 该方法定义了哪些客户端将注册到服务
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                // 使用内存存储
                .inMemory()
                // 注册应用程序的名称
                .withClient("eagleeye")
                // 密钥，该密钥在eagleeye程序调用QAuth2服务器以接收OAuth2访问令牌时提供
                .secret("{noop}thisissecret")
                // 授权类型列表，这些授权类型将由OAuth2服务支持，这里我们将支持密码授权类型和客户端凭证授权类型
                .authorizedGrantTypes(
                        "refresh_token",
                        "password",
                        "client_credentials"
                )
                // 定义调用应用程序在请求OAuth2服务器获取访问令牌时可以操作的范围
                .scopes("webclient", "mobileclient");
    }

    /**
     * 该方法定义了AuthenticationServerConfigurer中使用的不同组件
     *  告诉spring使用spring提供的默认管理验证器，和用户详细信息服务
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }
}
