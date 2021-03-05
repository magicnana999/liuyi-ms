package com.creolophus.liuyi.common.base;

import com.creolophus.liuyi.common.api.ApiContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author magicnana
 * @date 2019/5/20 下午2:31
 */
@Transactional(rollbackFor = Exception.class)
public class AbstractService {

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

    protected String like(String value) {
        return StringUtils.isNotBlank(value) ? "%" + value + "%" : null;
    }

}
