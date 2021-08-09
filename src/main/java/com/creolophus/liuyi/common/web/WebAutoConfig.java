package com.creolophus.liuyi.common.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author magicnana
 * @date 2021/8/9 17:48
 */
@Configuration
public class WebAutoConfig extends WebMvcConfigurationSupport {

  private static final Logger logger = LoggerFactory.getLogger(WebAutoConfig.class);

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

  @Override
  protected void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    registry
        .addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");
    registry
        .addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
    super.addResourceHandlers(registry);
  }

  @Bean
  @ConditionalOnMissingBean
  public ApiContextFilter apiContextFilter() {
    return new ApiContextFilter();
  }

  @Bean
  @ConditionalOnMissingBean
  public ApiInterceptor apiInterceptor() {
    return new ApiInterceptor();
  }
}
