package com.creolophus.liuyi.common.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ApiInterceptor.class);

    public String getPathPatterns(){
        return "/liuyi/**";
    }

    @Resource
    protected ApiContextValidator apiContextValidator;

    @Resource
    private ApiScope4InterHandler apiScopeHandlerList;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        afterCompletion(request);
    }





    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        preHandle(request);
        authenticate(request, handler);
        return true;
    }

    protected void authenticate(HttpServletRequest request, Object handler) {
        if(handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            Api api = hm.getMethodAnnotation(Api.class);
            if(api == null) {
                return;
            }
            apiScopeHandlerList.handle(request);
        } else {
            throw new RuntimeException("不是HandlerMethod类型,无法继续运行");
        }

    }


    protected void preHandle(HttpServletRequest request) {
        apiContextValidator.initContext(request);
    }

    protected void afterCompletion(HttpServletRequest request) {

    }
}

