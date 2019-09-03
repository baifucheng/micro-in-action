package com.baifc.licenseservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * projectName: spring-micro-in-action
 * packageName: com.baifc.jwtservice.security
 * Created: 2019-08-31.
 * Auther: baifc
 * Description: 定义spring将如何管理JWT令牌的创建、签名和翻译
 *  本例中我们将使用一个对称密钥
 */
@Configuration
public class JWTTokenStoreConfig {

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 在JWT和OAuth2服务器之间充当翻译
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        // 定义将用于签署令牌的签名密钥
        converter.setSigningKey("baifc");
        return converter;
    }

    /**
     * 用于从出示给服务的令牌中读取数据
     * @return
     */
    @Primary
    @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

//    @Bean
//    public TokenEnhancer jwtTokenEnhancer() {
//        return new JWTTokenEnhancer();
//    }
}
