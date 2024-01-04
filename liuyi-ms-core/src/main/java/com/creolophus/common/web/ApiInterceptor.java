package com.creolophus.common.web;

import com.creolophus.common.context.ApiContext;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ApiInterceptor extends HandlerInterceptorAdapter {

  private static final Logger logger = LoggerFactory.getLogger(ApiInterceptor.class);
  @Resource private ApiHandler apiHandler;

  @Resource(name = "defaultApi")
  private Api defaultApi;

  protected void afterCompletion(HttpServletRequest request) {}

  protected void authenticate(HttpServletRequest request, Object handler) {
    if (handler instanceof HandlerMethod) {
      HandlerMethod hm = (HandlerMethod) handler;
      Api api = hm.getMethodAnnotation(Api.class);
      if (api == null) {
        api = defaultApi;
      }
      ApiContext.getContext().setApi(api);
      apiHandler.handle(api, request);
    }
  }

  public String getPathPatterns() {
    return "/btb/**";
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
