package com.creolophus.liuyi.common.exception;

/**
 * @author magicnana
 * @date 2019/6/24 下午5:51
 */
public abstract class AutoStatusException extends ApiException {

  private int code = 200;

  public AutoStatusException(String message) {
    super(message);
  }

  public AutoStatusException(String message, int code) {
    super(message);
    this.code = code;
  }

  public int getHttpCode() {
    return code;
  }
}
