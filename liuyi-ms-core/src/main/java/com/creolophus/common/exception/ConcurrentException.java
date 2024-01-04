package com.creolophus.common.exception;

/**
 * @author magicnana
 * @date 2019/1/7 上午10:26
 */
public class ConcurrentException extends CreolophusException {

  public ConcurrentException(String message) {
    super(message);
  }

  public ConcurrentException(String message, Throwable cause) {
    super(message, cause);
  }
}
