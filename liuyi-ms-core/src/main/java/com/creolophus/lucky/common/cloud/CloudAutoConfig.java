package com.creolophus.lucky.common.cloud;

import com.creolophus.lucky.common.web.Api;
import feign.Feign;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author magicnana
 * @date 2019/3/1 上午12:36
 */
@Configuration
@ConditionalOnClass(Feign.class)
public class CloudAutoConfig {

  private static org.slf4j.Logger logger = LoggerFactory.getLogger(CloudAutoConfig.class);

  @Bean
  @Scope("prototype")
  @ConditionalOnMissingBean
  public Decoder decoder() {
    if (logger.isInfoEnabled()) {
      logger.info("start -> feign Decoder");
    }
    return new CustomDecoder();
  }

  @Bean
  @Scope("prototype")
  @ConditionalOnMissingBean
  public ErrorDecoder errorDecoder() {
    if (logger.isInfoEnabled()) {
      logger.info("start -> feign ErrorDecoder");
    }
    return new CustomErrorDecoder();
  }

  @Bean
  @Scope("prototype")
  @ConditionalOnMissingBean
  public Logger.Level feignLoggerLevel() {
    return Logger.Level.NONE;
  }

  @Bean
  @Scope("prototype")
  @ConditionalOnMissingBean
  public RequestInterceptor requestInterceptor() {
    return template -> template.header(Api.INTERNAL_HEADER_KEY, Api.INTERNAL_HEADER_VAL);
  }
}
