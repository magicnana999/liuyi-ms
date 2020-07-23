package com.creolophus.liuyi.common.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author magicnana
 * @date 2018/5/25.
 */
@ApiIgnore
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class GlobalErrorController implements ErrorController {

    private static Logger logger = LoggerFactory.getLogger(GlobalErrorController.class);

    @Resource
    private ErrorInfoBuilder errorInfoBuilder;

    @RequestMapping(produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public ResponseEntity error(HttpServletRequest request, HttpServletResponse response) {
        ResponseEntity responseEntity =  errorInfoBuilder.getErrorInfo(request, response);
        return responseEntity;
    }

    @Override
    public String getErrorPath() {
        return this.errorInfoBuilder.getErrorPath();
    }


}
