package com.baifc.jwtservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Arrays;

/**
 * projectName: spring-micro-in-action
 * packageName: com.baifc.jwtservice.security
 * Created: 2019-08-31.
 * Auther: baifc
 * Description:
 */
@Configuration
public class JWTOAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Qualifier("userDetailsServiceBean")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenStore tokenStore;

//    @Autowired
//    private DefaultTokenServices tokenServices;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private JWTTokenEnhancer jwtTokenEnhancer;


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                // 使用内存存储
                .inMemory()
                // 注册应用程序的名称
                .withClient("license")
                // 密钥，该密钥在eagleeye程序调用QAuth2服务器以接收OAuth2访问令牌时提供
                .secret("{noop}license")
                // 授权类型列表，这些授权类型将由OAuth2服务支持，这里我们将支持密码授权类型和客户端凭证授权类型
                .authorizedGrantTypes(
                        "refresh_token",
                        "password",
                        "client_credentials"
                )
                // 定义调用应用程序在请求OAuth2服务器获取访问令牌时可以操作的范围
                .scopes("webclient", "mobileclient")
                .and()
                .withClient("organization")
                .secret("{noop}organization")
                .authorizedGrantTypes("password")
                .scopes("webclient", "mobileclient");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(
                Arrays.asList(jwtTokenEnhancer, jwtAccessTokenConverter)
        );

        endpoints
                .tokenStore(tokenStore)
                // 这是钩子，告诉spring security OAuth2使用JWT
                .accessTokenConverter(jwtAccessTokenConverter)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenEnhancer(tokenEnhancerChain);
    }


}
