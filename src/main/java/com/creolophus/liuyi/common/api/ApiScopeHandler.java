package com.creolophus.liuyi.common.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author magicnana
 * @date 2020/9/22 3:05 PM
 */
public class ApiScopeHandler {


    @Autowired
    private WebApplicationContext applicationContext;
    @PostConstruct
    public void urls() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        for (RequestMappingInfo info : map.keySet()) {
            // 获取url的Set集合，一个方法可能对应多个url
            Set<String> patterns = info.getPatternsCondition().getPatterns();
            for (String url : patterns) {
                // 把结果存入静态变量中程序运行一次次方法之后就不用再次请求次方法
                System.out.println(url);
            }
        }

    }

    private static final ConcurrentHashMap<String,String> scopeTable = new ConcurrentHashMap<>();



    public boolean check(HttpServletRequest request){

    }
}
