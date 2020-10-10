package com.creolophus.liuyi.common.security;

import com.creolophus.liuyi.common.api.ApiResult;
import com.creolophus.liuyi.common.json.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author magicnana
 * @date 2019/5/27 下午6:21
 */
public class GoAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(GoAccessDeniedHandler.class);
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException {

        logger.error("权限不够" + httpServletRequest.getRequestURI());

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        ApiResult result = new ApiResult(response.getStatus(),HttpStatus.FORBIDDEN.getReasonPhrase());
        PrintWriter printWriter = response.getWriter();
        String body = JSON.toJSONString(result);
        printWriter.write(body);
        printWriter.flush();
    }
}
