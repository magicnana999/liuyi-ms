package com.creolophus.liuyi.common.api;

import org.apache.commons.lang3.StringUtils;

/**
 * @author magicnana
 * @date 2019/6/10 上午10:04
 */
public class GlobalSetting {

    public static final String MDC_METHOD = "X-LiuYi-Method";
    public static final String MDC_EXT = "X-LiuYi-EXT";
    public static final String MDC_URI = "X-LiuYi-URI";
    public static final String MDC_IP = "X-LiuYi-IP";

    public static final String HEADER_TOKEN_KEY = "Authorization";
    public static final String HEADER_TOKEN_PRE = "Bearer";

    public static final String MODEL_PRODUCTION = "prod";
    public static final String MODEL_DEBUG = "debug";


    private String model = MODEL_DEBUG;

    private boolean loggingPrintJson = isDebug()?true:false;

    public void setModel(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public boolean isDebug(){
        return StringUtils.isBlank(model) || MODEL_DEBUG.equalsIgnoreCase(model);
    }

    public boolean isLoggingPrintJson() {
        return loggingPrintJson;
    }

    public void setLoggingPrintJson(boolean loggingPrintJson) {
        this.loggingPrintJson = loggingPrintJson;
    }
}
