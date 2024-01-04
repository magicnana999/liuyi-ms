package com.creolophus.common.security;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author magicnana
 * @date 2021/10/25 20:34
 */
public class TokenReceiver {

  private static final Logger logger = LoggerFactory.getLogger(TokenReceiver.class);

  public String getToken(HttpServletRequest request) {
    String auth_token = request.getHeader(SecurityAutoConfig.HEADER_TOKEN_KEY);
    if(StringUtils.isBlank(auth_token)){
      return null;
    }
    final String auth_token_start = SecurityAutoConfig.HEADER_TOKEN_PRE + " ";
    return  auth_token.substring(auth_token_start.length());
  }
}
