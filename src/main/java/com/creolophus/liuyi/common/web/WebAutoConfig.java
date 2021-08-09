package com.creolophus.liuyi.common.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author magicnana
 * @date 2021/8/9 17:48
 */
//@Configuration
public class WebAutoConfig extends WebMvcConfigurationSupport {

  private static final Logger logger = LoggerFactory.getLogger(WebAutoConfig.class);

//  @Bean
//  @ConditionalOnMissingBean
  public ApiInterceptor apiInterceptor() {
    return new ApiInterceptor();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    ApiInterceptor apiInterceptor = apiInterceptor();
    if (apiInterceptor != null) {
      if (logger.isInfoEnabled()) {
        logger.info("start addInterceptor with ApiInterceptor");
      }
      registry.addInterceptor(apiInterceptor).addPathPatterns(apiInterceptor.getPathPatterns());
    }
  }

//  @Bean
//  @ConditionalOnMissingBean
  public ApiContextFilter apiContextFilter() {
    return new ApiContextFilter();
  }

}
