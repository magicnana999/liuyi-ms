package com.creolophus.liuyi.common.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

/**
 * @author magicnana
 * @date 2021/8/9 17:46
 */
@Configuration
public class ArgumentResolverAutoConfig {

  @Bean
  public HandlerMethodArgumentResolver getNullArgumentConfirm() {
    return new NullArgumentConfirm(false);
  }
}
