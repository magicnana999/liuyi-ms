package com.creolophus.liuyi.common.exception;

import com.creolophus.liuyi.common.error.ApiError;

/**
 * @author magicnana
 * @date 2019/6/24 下午5:51
 */
public class ErrorCodeException extends AutoStatusException {

  private ApiError apiError;

  public ErrorCodeException(ApiError error) {
    super(error.getMessage());
    this.apiError = error;
  }

  public ErrorCodeException(ApiError error, int httpStatusCode) {
    super(error.getMessage(), httpStatusCode);
    this.apiError = error;
  }

  public ErrorCodeException(ApiError error, String... formatMessage) {
    super(error.format(formatMessage).getMessage());
    this.apiError = error;
  }

  public ErrorCodeException(ApiError error, int httpStatusCode, String... formatMessage) {
    super(error.format(formatMessage).getMessage(), httpStatusCode);
    this.apiError = error;
  }

  public ApiError getApiError() {
    return apiError;
  }
}
