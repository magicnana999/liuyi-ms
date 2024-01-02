package com.creolophus.lucky.common.security;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author magicnana
 * @date 2019/5/27 下午5:20
 */
@Configuration
@ConditionalOnClass(WebSecurityConfigurerAdapter.class)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityAutoConfig extends WebSecurityConfigurerAdapter {

  public static final String HEADER_TOKEN_KEY = "Authorization";
  public static final String HEADER_TOKEN_PRE = "Bearer";

  private static final Logger logger = LoggerFactory.getLogger(SecurityAutoConfig.class);

  @Resource private UserDetailsService userDetailsService;

  @Bean
  @ConditionalOnMissingBean
  public GoAccessDeniedHandler goAccessDeniedHandler() {
    return new GoAccessDeniedHandler();
  }

  @Bean
  @ConditionalOnMissingBean
  public GoAuthenticationEntryPoint goAuthenticationEntryPoint() {
    return new GoAuthenticationEntryPoint();
  }

  @Bean
  @ConditionalOnMissingBean
  public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
    if (logger.isInfoEnabled()) {
      logger.info("start -> JwtAuthenticationTokenFilter");
    }
    return new JwtAuthenticationTokenFilter();
  }


  @Bean
  @ConditionalOnMissingBean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @ConditionalOnMissingBean
  public TokenReceiver tokenReceiver() {
    return new TokenReceiver();
  }

  @Autowired(required = false)
  public WebSecurityAppender webSecurityAppender;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
  }

  @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  public void configure(WebSecurity web) {

    web.ignoring().antMatchers("/actuator/**");

    web.ignoring().antMatchers("/error");

    web.ignoring().antMatchers(HttpMethod.OPTIONS);

    web.ignoring()
        .antMatchers(
            "/",
            "/doc.html",
            "/csrf",
            "/webjars/**",
            "swagger-ui.html",
            "**/swagger-ui.html",
            "/favicon.ico",
            "/**/*.css",
            "/**/*.js",
            "/**/*.png",
            "/**/*.gif",
            "/swagger-resources/**",
            "/v2/**",
            "/v3/**",
            "/**/*.ttf");
    web.ignoring()
        .antMatchers(
            "/swagger-resources/configuration/ui",
            "/swagger-resources",
            "/swagger-resources/configuration/security",
            "/swagger-ui.html");

    if (webSecurityAppender != null) {
      webSecurityAppender.configure(web);
    }
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {

    //        authSetting.getNoAuthenticationPatterns().add("/error/**");
    //
    //        String[] array = new String[authSetting.getNoAuthenticationPatterns().size()];
    //        for(int i=0;i<authSetting.getNoAuthenticationPatterns().size();i++){
    //            String up = authSetting.getNoAuthenticationPatterns().get(i);
    //            array[i] = up;
    //        }

    httpSecurity
        .exceptionHandling()
        .accessDeniedHandler(goAccessDeniedHandler())
        .and()
        .cors()
        .and()
        .csrf()
        .disable()
        .exceptionHandling()
        .authenticationEntryPoint(goAuthenticationEntryPoint())
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        // .antMatchers(array).permitAll()
        .anyRequest()
        .authenticated();

    httpSecurity.headers().cacheControl();

    httpSecurity.addFilterBefore(
        jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
  }
}
