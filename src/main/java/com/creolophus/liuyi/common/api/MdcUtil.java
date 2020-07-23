package com.creolophus.liuyi.common.api;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

/**
 * @author magicnana
 * @date 2020/1/16 下午2:44
 */
public class MdcUtil {

    public static final String MDC_DEFAULT = "-";

    public static void clearExt(){
        MDC.remove(GlobalSetting.MDC_EXT);
    }

    public static void clearAll(){
        MDC.clear();
    }

    public static void setExt(String ext) {
        if(StringUtils.isNotBlank(ext)) {
            MDC.put(GlobalSetting.MDC_EXT, ext);
        }
    }

    public static void setMethod() {
        if(StringUtils.isBlank(MDC.get(GlobalSetting.MDC_METHOD))) {
            MDC.put(GlobalSetting.MDC_METHOD, MDC_DEFAULT);
        }
    }

    public static void setMethod(String method) {
        if(StringUtils.isNotBlank(method)) {
            MDC.put(GlobalSetting.MDC_METHOD, method);
        }
    }

    public static void clearMethod(){
        MDC.remove(GlobalSetting.MDC_METHOD);
    }

    public static void setUri() {
        if(StringUtils.isBlank(MDC.get(GlobalSetting.MDC_URI))) {
            MDC.put(GlobalSetting.MDC_URI, MDC_DEFAULT);
        }
    }

    public static void setUri(String uri) {
        if(StringUtils.isNotBlank(uri)) {
            MDC.put(GlobalSetting.MDC_URI, uri);
        }
    }

    public static void clearUri(){
        MDC.remove(GlobalSetting.MDC_URI);
    }

}
