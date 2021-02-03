package com.creolophus.liuyi.common.api;

import com.creolophus.liuyi.common.exception.HttpStatusException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * @author magicnana
 * @date 2020/10/10 4:03 PM
 */
@Service
public class ApiScope4InterHandler implements ApiScopeHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApiScope4InterHandler.class);

    @Override
    public boolean allow(String scope) {
        return StringUtils.isNotBlank(scope) && scope.endsWith(Api.SCOPE_INTER);
    }

    @Override
    public void handle(HttpServletRequest request) {
        String header = request.getHeader(GlobalSetting.HEADER_INTER_KEY);
        if(StringUtils.isNotBlank(header) && header.equals(GlobalSetting.HEADER_INTER_VAL)) {
            long userId= 0L;
            ApiContext.getContext().setApiScope(Api.SCOPE_INTER);
            ApiContext.getContext().setToken(header);
            ApiContext.getContext().setUserId(userId);
            if(logger.isDebugEnabled()) {
                logger.debug("token:{}", header);
                logger.debug("{}:{}", userId, "NONE");
            }
        }else{
            logger.error("还没授权" + request.getRequestURI());
            throw new HttpStatusException(HttpStatus.UNAUTHORIZED);
        }

    }
}
