package com.creolophus.liuyi.common.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2019/5/27 下午5:20
 */
@Configuration
@ConditionalOnClass(WebSecurityConfigurerAdapter.class)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityAutoConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityAutoConfig.class);


    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean
    public GoAuthenticationEntryPoint goAuthenticationEntryPoint(){
        return new GoAuthenticationEntryPoint();
    }

    @Bean
    @ConditionalOnMissingBean
    public GoAccessDeniedHandler goAccessDeniedHandler(){
        return new GoAccessDeniedHandler();
    }

    @Resource
    private UserDetailsService userDetailsService;

    @Bean
    @ConditionalOnMissingBean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        logger.info("start JwtAuthenticationTokenFilter");
        return new JwtAuthenticationTokenFilter();
    }


    @Bean
    @ConditionalOnMissingBean
    public LiuyiWebSecurityConfigurerAdapter liuyiWebSecurityConfigurerAdapter(){
        logger.info("start LiuyiWebSecurityConfigurerAdapter");
        return new LiuyiWebSecurityConfigurerAdapter(userDetailsService,goAccessDeniedHandler(),goAuthenticationEntryPoint(),jwtAuthenticationTokenFilter());
    }
}
