package com.creolophus.common.exception;

import com.creolophus.common.error.ApiError;

/**
 * @author magicnana
 * @date 2019/6/24 下午5:51
 */
public class NoContentException extends ApiException {

  private ApiError apiErrorCode;

  public NoContentException(ApiError apiErrorCode) {
    super(apiErrorCode.getMessage());
    this.apiErrorCode = apiErrorCode;
  }

  public ApiError getApiError() {
    return apiErrorCode;
  }
}
