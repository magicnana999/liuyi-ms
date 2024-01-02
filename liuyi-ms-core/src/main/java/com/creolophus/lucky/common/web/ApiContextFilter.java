package com.creolophus.lucky.common.web;

import com.creolophus.lucky.common.context.ApiContext;
import com.creolophus.lucky.common.json.GsonUtil;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author magicnana
 * @date 2019/9/26 上午10:56
 */
public class ApiContextFilter extends OncePerRequestFilter {

  private static final Logger logger = LoggerFactory.getLogger(ApiContextFilter.class);

  @Order(-1111111)
  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    CorsUtil.cors(request, response);
    if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
      response.setStatus(HttpStatus.OK.value());
      return;
    }

    MdcUtil.init(request.getRequestURI(), null);
    logger.info("{}", GsonUtil.toJson(request.getParameterMap()));
    chain.doFilter(request, response);
    if (logger.isInfoEnabled()) {
      logger.info("{} {}", response.getStatus(), GsonUtil.toJson(ApiContext.getContext().getApiResult()));
    }
    MdcUtil.clear();
    ApiContext.release();
  }
}
