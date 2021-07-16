package com.creolophus.liuyi.common.security;

import com.creolophus.liuyi.common.api.ApiResult;
import com.creolophus.liuyi.common.json.JSON;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * @author magicnana
 * @date 2019/5/27 下午6:26
 */
public class GoAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private static final Logger logger = LoggerFactory.getLogger(GoAuthenticationEntryPoint.class);

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException e) throws IOException {

    logger.error("尚未授权" + request.getRequestURI());

    // 此处配置的是允许任意域名跨域请求，可根据需求指定
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Methods",
        "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
    response.setHeader("Access-Control-Max-Age", "86400");
    response.setHeader("Access-Control-Allow-Headers", "*");

    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setCharacterEncoding("UTF-8");
    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

    ApiResult result = new ApiResult(response.getStatus(),
        HttpStatus.UNAUTHORIZED.getReasonPhrase());
    PrintWriter printWriter = response.getWriter();
    String body = JSON.toJSONString(result);
    printWriter.write(body);
    printWriter.flush();

  }
}