package com.creolophus.liuyi.common.api;

import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author magicnana
 * @date 2019/9/26 上午10:56
 */
public class ApiContextFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(ApiContextFilter.class);

    @Resource
    private ApiContextValidator apiContextValidator;

    @Resource
    private GlobalSetting globalSetting;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {

        apiContextValidator.initContext(request);
        chain.doFilter(request, response);
        logger.info("{}",response.getStatus());
        apiContextValidator.cleanContext();
    }

}
