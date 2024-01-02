package com.creolophus.lucky.common.security;

import com.creolophus.lucky.common.web.CorsUtil;
import com.creolophus.lucky.common.exception.HttpStatusException;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * @author magicnana
 * @date 2019/5/27 下午6:26
 */
public class GoAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private static final Logger logger = LoggerFactory.getLogger(GoAuthenticationEntryPoint.class);

  @Override
  public void commence(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
      throws IOException {

    logger.error("尚未授权" + request.getRequestURI());

    CorsUtil.cors(request, response);

    throw new HttpStatusException(HttpStatus.UNAUTHORIZED);
  }
}
