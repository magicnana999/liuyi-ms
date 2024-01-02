package com.creolophus.lucky.common.error;

/**
 * @author magicnana
 * @date 2019/5/15 下午2:17
 */
public class ApiError {

  public static final ApiError OK = new ApiError(200, "OK");

  public static final ApiError INTERNAL_ERROR = new ApiError(500, "暂时无法提供服务");
  public static final ApiError ERROR = new ApiError(400, "服务器内部错误");

  private int code;
  private String message;

  public ApiError(int code, String message) {
    this.code = code;
    this.message = message;
  }


  public ApiError format(String msg) {
    return new ApiError(this.getCode(), String.format(this.message, msg));
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
