package com.creolophus.common.web;

import com.creolophus.common.error.ApiError;
import java.util.Collections;

/**
 * @author magicnana
 * @date 2019/5/15 下午2:16
 */
public final class ApiResult<T> implements java.io.Serializable {

  private static final long serialVersionUID = -3292747658038585878L;

  private int code;
  private String message;
  private T data;

  public ApiResult() {
    this.code = ApiError.OK.getCode();
    this.data = (T) Collections.emptyMap();
  }

  public ApiResult(T data) {
    this.code = ApiError.OK.getCode();
    this.data = data == null ? (T) Collections.emptyMap() : data;
  }

  public ApiResult(int code, String message) {
    this.code = code;
    this.message = message;
    this.data = data == null ? (T) Collections.emptyMap() : data;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "{\"code\":"
        + code
        + ",\"msg\":\""
        + message
        + "\", \"data\":"
        + (data == null ? "" : data.toString())
        + '}';
  }
}
