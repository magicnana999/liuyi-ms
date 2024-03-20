package com.creolophus.liuyi.common.security;

import com.alibaba.fastjson2.JSON;
import com.creolophus.liuyi.common.web.ApiResult;
import com.creolophus.liuyi.common.web.CorsUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * @author magicnana
 * @date 2019/5/27 下午6:21
 */
public class GoAccessDeniedHandler implements AccessDeniedHandler {

  private static final Logger logger = LoggerFactory.getLogger(GoAccessDeniedHandler.class);

  @Override
  public void handle(
      HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
      throws IOException {

    logger.error("权限不够" + request.getRequestURI());

    CorsUtil.cors(request, response);

    ApiResult result = new ApiResult(response.getStatus(), HttpStatus.FORBIDDEN.getReasonPhrase());
    PrintWriter printWriter = response.getWriter();
    String body = JSON.toJSONString(result);
    printWriter.write(body);
    printWriter.flush();

  }
}
