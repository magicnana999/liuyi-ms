package com.creolophus.liuyi.common.web;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author magicnana
 * @date 2019/9/26 上午10:56
 */
public class ApiContextFilter extends OncePerRequestFilter {

  private static final Logger logger = LoggerFactory.getLogger(ApiContextFilter.class);

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    MdcUtil.init(request.getRequestURI(), null);
    chain.doFilter(request, response);
    if (logger.isInfoEnabled()) {
      logger.info("{}", response.getStatus());
    }
    MdcUtil.clear();
    ApiContext.release();
  }
}
