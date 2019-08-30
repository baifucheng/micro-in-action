package com.baifc.licenseservice.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

/**
 * projectName: micro-in-action
 * packageName: com.baifc.licenseservice.config
 * Created: 2019/8/30.
 * Auther: baifc
 * Description:
 */
@Configuration
public class OAuth2Config {

    /**
     * 支持OAuth2调用的rest模板
     * @param details
     * @return
     */
    @Bean
    public OAuth2RestTemplate oAuth2RestTemplate(OAuth2ProtectedResourceDetails details, OAuth2ClientContext context) {
        return new OAuth2RestTemplate(details, context);
    }

//    @Bean
//    public OAuth2RestTemplate oAuth2RestTemplate(OAuth2ProtectedResourceDetails details) {
//        return new OAuth2RestTemplate(details);
//    }
}
