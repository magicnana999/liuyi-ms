package com.creolophus.liuyi.common.cloud;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author magicnana
 * @date 2018/12/27 下午2:58
 */
public class CustomRequestInterceptor implements RequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(CustomRequestInterceptor.class);

    @Override
    public void apply(RequestTemplate template) {

//        String platformKey = ApiContext.getContext().getPlatformKey();
//        String appKey = ApiContext.getContext().getAppKey();
//        String deviceId = ApiContext.getContext().getDeviceId();
//
//        template.header(authSetting.getHeaderAppKey(), appKey);
//        template.header(authSetting.getHeaderPlatformKey(), platformKey);
//        template.header(authSetting.getHeaderDeviceId(),deviceId);
//        logger.info("feign header [{}:{},{}:{},{}:{}]",authSetting.getHeaderAppKey(),appKey,authSetting.getHeaderPlatformKey(),platformKey,authSetting.getHeaderDeviceId(),deviceId);
    }


}
