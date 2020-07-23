package com.creolophus.liuyi.common.api;

import com.alibaba.fastjson.JSON;
import com.creolophus.liuyi.common.util.IPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

/**
 * @author magicnana
 * @date 2019/8/12 上午10:58
 */
public class ApiContextValidator {


    private static final Logger logger = LoggerFactory.getLogger(ApiContextValidator.class);

    public String[] ignoringAntMatchers() {
        return new String[]{};
    }


    public void setApiResult(Object obj){
        ApiContext.getContext().setApiResult(obj);
    }

    public void cleanContext() {


        ApiContext apiContext = ApiContext.getContext();

        apiContext.setUserId(0);
        apiContext.setUserAgent(null) ;
        apiContext.setIp(null);
        apiContext.setToken(null);
        apiContext.setRequest(null);
        apiContext.getExt().clear();
        apiContext.setApiResult(null);
        ApiContext.releaseContext();

    }

    public void initContext(){
        initContext(null);
    }

    protected String getDefaultExt(){
        return MdcUtil.MDC_DEFAULT;
    }

    public void initContext(HttpServletRequest request) {

        MdcUtil.setExt(getDefaultExt());
        MdcUtil.setUri();
        MdcUtil.setMethod("ApiContext");

        if(request!=null){
            ApiContext.getContext().setRequest(request);
            ApiContext.getContext().setIp(IPUtil.getRemoteIP(request));
            ApiContext.getContext().setUserAgent(request.getHeader("User-Agent"));

            MdcUtil.setUri(request.getRequestURI());
        }

    }

}
