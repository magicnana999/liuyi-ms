package com.creolophus.common.security;

import com.creolophus.common.web.CorsUtil;
import com.creolophus.common.web.MdcUtil;
import com.creolophus.common.context.ApiContext;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author magicnana
 * @date 2019/5/27 下午6:08
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

  protected static final Logger logger =
      LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

  @Resource protected TokenReceiver tokenReceiver;

  @Resource protected TokenParser tokenParser;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    CorsUtil.cors(request, response);
    if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
      response.setStatus(HttpStatus.OK.value());
      return;
    }



    preHandle(request);

    String token = tokenReceiver.getToken(request);
    if (logger.isDebugEnabled()) {
      logger.debug("token:{}", token);
    }


    if (StringUtils.isNotBlank(token)) {

      ApiContext.getContext().setToken(token);

      UserDetails userDetail = tokenParser.parseToken(token);

      if (userDetail != null && StringUtils.isNotBlank(userDetail.getUsername())) {

        if (logger.isDebugEnabled()) {
          logger.debug("{}:{}", userDetail.getUsername(), userDetail.getAuthorities());
        }

        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                userDetail.getUsername(), null, userDetail.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ApiContext.getContext().setUserId(Long.valueOf(userDetail.getUsername()));
      }
    }
    chain.doFilter(request, response);

    postHandle(request);
  }

  protected void postHandle(HttpServletRequest request) {}

  protected void preHandle(HttpServletRequest request) {
    MdcUtil.init(request.getRequestURI(), null);
  }
}
