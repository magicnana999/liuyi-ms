package com.creolophus.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author magicnana
 * @date 2021/8/10 14:55
 */
public class CorsUtil {

  public static void cors(HttpServletRequest request, HttpServletResponse response) {
    // 此处配置的是允许任意域名跨域请求，可根据需求指定
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader(
        "Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
    response.setHeader("Access-Control-Max-Age", "86400");
    response.setHeader("Access-Control-Allow-Headers", "*");
  }
}
