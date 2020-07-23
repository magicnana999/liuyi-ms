package com.creolophus.liuyi.common.base;

import com.creolophus.liuyi.common.api.ApiContext;

import javax.servlet.http.HttpServletRequest;

/**
 * @author magicnana
 * @date 2019/5/20 下午2:31
 */
public class AbstractController {

    protected long getUserId() {
        return ApiContext.getContext().getUserId();
    }

    protected String getIp() {
        return ApiContext.getContext().getIp();
    }

    protected HttpServletRequest getRequest() {
        return ApiContext.getContext().getRequest();
    }
}
