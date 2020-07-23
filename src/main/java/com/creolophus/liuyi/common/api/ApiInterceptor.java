package com.creolophus.liuyi.common.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        afterCompletion(request);
    }





    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        preHandle(request);
        return true;
    }


    protected void preHandle(HttpServletRequest request) {

    }

    protected void afterCompletion(HttpServletRequest request) {

    }
}

