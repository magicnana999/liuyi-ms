package com.creolophus.liuyi.common.base;

import com.creolophus.liuyi.common.api.ApiContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author magicnana
 * @date 2019/5/20 下午2:31
 */
public class AbstractController {

    protected long currentUserId() {
        return ApiContext.getContext().getUserId();
    }

    protected String currentIP() {
        return ApiContext.getContext().getIp();
    }

    protected String currentToken() {
        return ApiContext.getContext().getToken();
    }

    protected HttpServletRequest currentRequest() {
        return ApiContext.getContext().getRequest();
    }
}
