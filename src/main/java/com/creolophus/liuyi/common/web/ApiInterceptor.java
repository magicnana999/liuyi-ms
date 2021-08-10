package com.creolophus.liuyi.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ApiInterceptor extends HandlerInterceptorAdapter {

  private static final Logger logger = LoggerFactory.getLogger(ApiInterceptor.class);

  protected void afterCompletion(HttpServletRequest request) {}

  protected void authenticate(HttpServletRequest request, Object handler) {
    //    if (handler instanceof HandlerMethod) {
    //      HandlerMethod hm = (HandlerMethod) handler;
    //      Api api = hm.getMethodAnnotation(Api.class);
    //      if (api == null) {
    //        return;
    //      }
    //
    //      for (ApiAnnoHandler apiAnnoHandler : apiAnnoHandlers) {
    //        if (apiAnnoHandler.allow(api)) {
    //          apiAnnoHandler.handle(request, api);
    //        }
    //      }
    //    } else {
    //      throw new RuntimeException("不是HandlerMethod类型,无法继续运行");
    //    }
  }

  public String getPathPatterns() {
    return "/liuyi/**";
  }

  protected void preHandle(HttpServletRequest request) {
    MdcUtil.init(request.getRequestURI(), null);
  }

  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) {
    preHandle(request);
    authenticate(request, handler);
    return true;
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    afterCompletion(request);
  }
}
