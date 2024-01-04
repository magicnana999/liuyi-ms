package com.creolophus.common.web;

import com.creolophus.common.exception.HttpStatusException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

/**
 * @author magicnana
 * @date 2021/9/4 17:02
 */
public class ApiHandler {
  private static final Logger logger = LoggerFactory.getLogger(ApiHandler.class);


  public void handle(Api api, HttpServletRequest request) {
    handleScope(api,request);
  }

  protected void handleScope(Api api, HttpServletRequest request) {
    if(api.scope().equals(Api.SCOPE_INTERNAL)){
      String header = request.getHeader(Api.INTERNAL_HEADER_KEY);

      if (logger.isDebugEnabled()) {
        logger.debug("{} {} {}", Api.SCOPE_INTERNAL, Api.INTERNAL_HEADER_KEY,header);
      }

      if(StringUtils.isBlank(header) || !header.equals(Api.INTERNAL_HEADER_VAL)){
        throw new HttpStatusException(HttpStatus.UNAUTHORIZED);
      }
    }
  }
}
