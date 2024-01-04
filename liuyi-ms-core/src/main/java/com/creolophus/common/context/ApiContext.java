package com.creolophus.common.context;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author magicnana
 * @date 2019/7/3 上午10:36
 */
public class ApiContext<T> implements Serializable {

  private long userId;
  private String token;
  private T api;
  private Map<String, Object> ext = new HashMap(8);

  private Object apiResult;

  public static ApiContext getContext() {
    ApiContext apiContext = ApiContextLocal.getInstance().get();
    return apiContext;
  }

  public static void release() {
    ApiContext ac = getContext();
    ac.setUserId(0);
    ac.setToken(null);
    ac.setApi(null);
    ac.getExt().clear();
    ac.setApiResult(null);
    ApiContextLocal.getInstance().remove();
  }

  public void delExt(String key) {
    this.getExt().remove(key);
  }

  public T getApi() {
    return api;
  }

  public void setApi(T api) {
    this.api = api;
  }

  public Object getApiResult() {
    return apiResult;
  }

  public void setApiResult(Object apiResult) {
    this.apiResult = apiResult;
  }

  public Map<String, Object> getExt() {
    return ext;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }

  public <T> T getExt(String key) {
    return (T) this.getExt().get(key);
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public void setExt(String key, Object value) {
    this.getExt().put(key, value);
  }
}
