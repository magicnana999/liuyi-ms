package com.creolophus.lucky.common.web;

import com.creolophus.lucky.common.json.GsonUtil;
import com.creolophus.lucky.common.json.JacksonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author magicnana
 * @date 2021/8/9 17:48
 */
@Configuration
public class WebAutoConfig implements WebMvcConfigurer {

  private static final Logger logger = LoggerFactory.getLogger(WebAutoConfig.class);

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    ApiInterceptor apiInterceptor = apiInterceptor();
    if (apiInterceptor != null) {
      registry.addInterceptor(apiInterceptor).addPathPatterns(apiInterceptor.getPathPatterns());
      if (logger.isInfoEnabled()) {
        logger.info("start -> addInterceptor with ApiInterceptor");
      }
    }
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

  //  @Override
  //  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
  //    WebMvcConfigurer.super.configureMessageConverters(converters);
  //  }

  @Bean
  @Primary
  public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
    return new MappingJackson2HttpMessageConverter(jacksonObjectMapper());
  }

  @Bean
  @Primary
  public ObjectMapper jacksonObjectMapper() {

    ObjectMapper objectMapper = JacksonUtil.getObjectMapper();

    if (logger.isInfoEnabled()) {
      logger.info("start -> objectMapper");
    }

    return objectMapper;
  }

  @Bean
  @ConditionalOnMissingBean
  public HttpMessageConverters httpMessageConverters() {

    GsonHttpMessageConverter gsonHttpMessageConverter = new LiuyiGsonHttpMessageConverter();
    Gson gson = GsonUtil.gson();
    gsonHttpMessageConverter.setGson(gson);

    if (logger.isInfoEnabled()) {
      logger.info("start -> addMessageConverters GSonHttpMessageConverter");
    }

    return new HttpMessageConverters(
        gsonHttpMessageConverter, mappingJackson2HttpMessageConverter());
  }

  @Bean
  @ConditionalOnMissingBean
  public ApiHandler apiHandler() {
    return new ApiHandler();
  }

  @Bean
  @ConditionalOnMissingBean
  public Api defaultApi() {
    return Api.PUBLIC_API;
  }
}
