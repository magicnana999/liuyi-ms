package com.creolophus.common.security;

import org.springframework.security.config.annotation.web.builders.WebSecurity;

/**
 * @author magicnana
 * @date 2021/10/26 13:52
 */
public interface WebSecurityAppender {

  void configure(WebSecurity web);
}
