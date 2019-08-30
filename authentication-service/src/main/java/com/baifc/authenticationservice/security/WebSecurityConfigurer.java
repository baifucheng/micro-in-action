package com.baifc.authenticationservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * projectName: spring-micro-in-action
 * packageName: com.baifc.authenticationservice.security
 * Created: 2019-08-29.
 * Auther: baifc
 * Description: 创建个人用户平局以及其所属的角色
 */

// TODO spring5 的问题  There is no PasswordEncoder mapped for the id "null"
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    /**
     * authenticationManagerBean被spring security用来处理验证
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * spring security使用USerDetailsService处理返回的用户信息，这些用户信息将由spring security返回
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    /**
     * 该方法定义用户、密码和角色
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("baifc")
                .password("{noop}123456")
                .roles("ADMIN", "USER")
                .and()
                .withUser("moumou")
                .password("{noop}111111")
                .roles("USER");
    }
}
