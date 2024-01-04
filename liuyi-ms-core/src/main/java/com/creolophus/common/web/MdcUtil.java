package com.creolophus.common.web;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

/**
 * @author magicnana
 * @date 2020/1/16 下午2:44
 */
public class MdcUtil {

  public static final String MDC_METHOD = "X-LiuYi-Method";
  public static final String MDC_EXT = "X-LiuYi-EXT";
  public static final String MDC_URI = "X-LiuYi-URI";
  public static final String MDC_IP = "X-LiuYi-IP";
  public static final String HEADER_TOKEN_KEY = "Authorization";
  public static final String HEADER_TOKEN_PRE = "Bearer";
  public static final String HEADER_INTER_KEY = "X-LiuYi-Inter";
  public static final String HEADER_INTER_VAL = "BMW525LIBENZGLE4504MATICPANAMERA";
  public static final String MDC_DEFAULT = "-";

  {
    MDC.put("X-B3-TraceId", MdcUtil.MDC_DEFAULT);
    MDC.put("X-B3-SpanId", MdcUtil.MDC_DEFAULT);
    MDC.put("X-LiuYi-EXT", MdcUtil.MDC_DEFAULT);
    MDC.put("X-LiuYi-URI", MdcUtil.MDC_DEFAULT);
    MDC.put("X-LiuYi-Method", MdcUtil.MDC_DEFAULT);
  }

  public static void clearExt() {
    //        MDC.remove(MDC_EXT);
    MDC.put(MDC_EXT, MDC_DEFAULT);
  }

  //    public static void clearAll(){
  //        MDC.clear();
  //    }

  public static void setExt(String ext) {
    if (StringUtils.isNotBlank(ext)) {
      MDC.put(MDC_EXT, ext);
    }
  }

  public static void setMethod() {
    if (StringUtils.isBlank(MDC.get(MDC_METHOD))) {
      MDC.put(MDC_METHOD, MDC_DEFAULT);
    }
  }

  public static void setMethod(String method) {
    if (StringUtils.isNotBlank(method)) {
      MDC.put(MDC_METHOD, method);
    }
  }

  public static void clearMethod() {
    //        MDC.remove(MDC_METHOD);
    MDC.put(MDC_METHOD, MDC_DEFAULT);
  }

  public static void setUri() {
    if (StringUtils.isBlank(MDC.get(MDC_URI))) {
      MDC.put(MDC_URI, MDC_DEFAULT);
    }
  }

  public static void setUri(String uri) {
    if (StringUtils.isNotBlank(uri)) {
      MDC.put(MDC_URI, uri);
    }
  }

  public static void clearUri() {
    //        MDC.remove(MDC_URI);
    MDC.put(MDC_URI, MDC_DEFAULT);
  }

  public static void init() {
    init(null, null);
  }

  public static void init(String uri, String methodName) {

    setExt(MDC_DEFAULT);
    setMethod(StringUtils.isBlank(methodName) ? MDC_DEFAULT : methodName);
    setUri(StringUtils.isBlank(uri) ? MDC_DEFAULT : uri);
  }

  public static void clear() {
    clearExt();
    clearMethod();
    clearUri();
  }
}
