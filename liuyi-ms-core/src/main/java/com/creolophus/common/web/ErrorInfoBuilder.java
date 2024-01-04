package com.creolophus.common.web;

import com.creolophus.common.context.ApiContext;
import com.creolophus.common.error.ApiError;
import com.creolophus.common.exception.ApiException;
import com.creolophus.common.exception.BrokenException;
import com.creolophus.common.exception.ErrorCodeException;
import com.creolophus.common.exception.HttpStatusException;
import com.creolophus.common.exception.NoContentException;
import com.creolophus.common.json.GsonUtil;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.util.WebUtils;

/**
 * @author magicnana
 * @date 2018/5/25.
 */
@Component
public class ErrorInfoBuilder {

  private Logger logger = LoggerFactory.getLogger(getClass());

  private ErrorProperties errorProperties;

  @Value("${spring.cloud.http-always-ok:false}")
  private boolean httpAlwaysOK;

  public ErrorInfoBuilder(ServerProperties serverProperties) {
    this.errorProperties = serverProperties.getError();
  }

  public ResponseEntity e0(HttpStatus httpStatus) {
    String message = httpStatus.getReasonPhrase();
    logger.error("error [{}:{}] {}", httpStatus.value(), httpStatus.getReasonPhrase(), message);
    ApiResult apiResult = new ApiResult(httpStatus.value(), message);
    return getResponseEntity(apiResult, httpStatus);
  }

  public ResponseEntity e0(HttpStatus httpStatus, String message) {
    logger.error("error [{}:{}] {}", httpStatus.value(), httpStatus.getReasonPhrase(), message);
    ApiResult apiResult = new ApiResult(httpStatus.value(), message);
    return getResponseEntity(apiResult, httpStatus);
  }

  public ResponseEntity e0(HttpStatus httpStatus, ApiError apiError) {
    logger.error(
        "error [{}:{}] {}",
        httpStatus.value(),
        httpStatus.getReasonPhrase(),
        (GsonUtil.toJson(apiError)));
    ApiResult apiResult = new ApiResult(apiError.getCode(), apiError.getMessage());
    return getResponseEntity(apiResult, httpStatus);
  }

  public ResponseEntity e0(HttpStatus httpStatus, ApiResult apiResult) {
    logger.error(
        "error [{}:{}] {}",
        httpStatus.value(),
        httpStatus.getReasonPhrase(),
        apiResult.getMessage());
    apiResult.setCode(httpStatus.value());
    return getResponseEntity(apiResult, httpStatus);
  }

  public Throwable getCloudError(HttpServletRequest request) {
    Throwable e = (Throwable) request.getAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE);
    return e;
  }

  /**
   * 获取错误.(Error/Exception) javax.servlet.error.exception
   *
   * <p>获取方式：通过Request对象获取(Key="javax.servlet.error.exception").
   *
   * @see DefaultErrorAttributes #addErrorDetails
   */
  public Throwable getError(HttpServletRequest request) {
    // 根据Request对象获取错误.
    Throwable error =
        (Throwable)
            request.getAttribute(
                "org.springframework.boot.web.servlet.error.DefaultErrorAttributes.ERROR");
    // 当获取错误非空,取出RootCause.
    if (error != null) {
      while (error instanceof ServletException && error.getCause() != null) {
        error = error.getCause();
      }
    } // 当获取错误为null,此时我们设置错误信息即可.
    return error;
  }

  /** 构建错误信息. */
  public ResponseEntity getErrorInfo(HttpServletRequest request, HttpServletResponse response) {

    Throwable e = this.getError(request);
    if (e == null) {
      e = getCloudError(request);
    }

    //		if(e==null){
    //			// TODO .. 无论何时，不能出现无法取得异常的情况
    //			return e0(request,HttpStatus.BAD_REQUEST,ApiCode.E_ERROR.getMessage());
    //		}

    // 参数类型错误
    if (e instanceof MethodArgumentTypeMismatchException) {
      return e0(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    // 此方法不支持
    else if (e instanceof HttpRequestMethodNotSupportedException) {
      return e0(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
    }

    // 缺少参数
    else if (e instanceof MissingServletRequestParameterException) {
      return e0(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    else if( e instanceof HttpMessageNotReadableException){
      return e0(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    // 需要配置spring:
    //    mvc:
    //      throw-exception-if-no-handler-found: true
    // 这里走else，取得404的错误码返回
    // 404
    else if (e instanceof NoHandlerFoundException) {
      return e0(HttpStatus.NOT_FOUND);
    }

    //    // 参数校验错误
    //    else if (e instanceof ConstraintViolationException) {
    //      return e0(HttpStatus.BAD_REQUEST, e.getMessage());
    //    }

//    else if(e instanceof AccessDeniedException){
//      AccessDeniedException ee = (AccessDeniedException) e;
//      return e0(HttpStatus.FORBIDDEN);
//
//    }

    // Security相关的401和403异常，走这里。但是，必须有额外的message
    else if (e instanceof HttpStatusException) {
      HttpStatusException ee = (HttpStatusException) e;
      ee.setUri(request.getRequestURI());
      return e0(ee.getHttpStatus(), e.getMessage());
    }

    // 抛一个ErrorCodeException，直接返回此ErrorCode
    else if (e instanceof ErrorCodeException) {
      ErrorCodeException ee = (ErrorCodeException) e;
      ee.setUri(request.getRequestURI());
      return e0(HttpStatus.BAD_REQUEST, ee.getApiError());
    }
    // 抛一个ErrorCodeException，直接返回此ErrorCode
    else if (e instanceof NoContentException) {
      NoContentException ee = (NoContentException) e;
      ee.setUri(request.getRequestURI());
      return e0(HttpStatus.OK, ee.getApiError());
    }

    // Feign异常，不熔断，message是provider返回的ApiResult，直接返回provider的ApiResult
    else if (e instanceof HystrixBadRequestException) {
      try {
        ApiResult apiResult = GsonUtil.toJava(e.getMessage(), ApiResult.class);
        if (apiResult == null) {
          return e0(HttpStatus.BAD_REQUEST, e.getMessage());
        } else {
          return e0(HttpStatus.BAD_REQUEST, apiResult);
        }
      } catch (Throwable eee) {
        return e0(HttpStatus.BAD_REQUEST, e.getMessage());
      }
    }

//    else if( e instanceof AuthenticationException){
//      return e0(HttpStatus.UNAUTHORIZED);
//    }

    else if (e instanceof BindException){
      BindException be = (BindException)e;
      FieldError fe = be.getBindingResult().getFieldError();
      String msg = fe.getDefaultMessage();
      return e0(HttpStatus.BAD_REQUEST,msg);
    }

    // @Validated 表单方式参数验证提示
    else if (e instanceof ConstraintViolationException){
      ConstraintViolationException be = (ConstraintViolationException)e;
      String localizedMessage = be.getLocalizedMessage();
      System.out.println("localizedMessage = " + localizedMessage);
      List<String> defaultMsg = be.getConstraintViolations()
              .stream()
              .map(ConstraintViolation::getMessage)
              .collect(Collectors.toList());
      return e0(HttpStatus.BAD_REQUEST,defaultMsg.get(0));
    }

    else if (e instanceof MethodArgumentNotValidException){
      MethodArgumentNotValidException be = (MethodArgumentNotValidException)e;
      StringBuffer stringBuffer = new StringBuffer();
      for(FieldError fe : be.getBindingResult().getFieldErrors()){
        stringBuffer.append(fe.getField()+" "+fe.getDefaultMessage()+",");
      }
      stringBuffer.delete(stringBuffer.length()-1,stringBuffer.length());
      return e0(HttpStatus.BAD_REQUEST,stringBuffer.toString());
    }

    // 在程序中抛出的ApiException，message直接给客户端返回，必须throw new ApiException("验证码不正确");
    else if (e instanceof ApiException) {
      ApiException ee = (ApiException) e;
      ee.setUri(request.getRequestURI());
      return e0(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    // 内部服务错误，熔断，返回给客户端500，"暂时无法提供服务"
    else if (e instanceof BrokenException) {
      return e0(HttpStatus.INTERNAL_SERVER_ERROR, ApiError.INTERNAL_ERROR);

    }

    // 有SqlException之类的，不方便给客户端提供的走这里，返回"服务器内部错误"
    else if (e instanceof Throwable) {
      return e0(HttpStatus.BAD_REQUEST, ApiError.ERROR);
    } else {
      int code = (int) request.getAttribute(WebUtils.ERROR_STATUS_CODE_ATTRIBUTE);
      HttpStatus httpStatus = HttpStatus.valueOf(code);
      httpStatus = httpStatus == null ? HttpStatus.BAD_REQUEST : httpStatus;
      return e0(httpStatus);
    }
  }

  public String getErrorPath() {
    return this.errorProperties.getPath();
  }

  private ResponseEntity getResponseEntity(ApiResult apiResult, HttpStatus httpStatus) {

    MdcUtil.clear();
    ApiContext.release();

    if (httpAlwaysOK) {
      return new ResponseEntity(apiResult, HttpStatus.OK);
    } else {
      return new ResponseEntity(apiResult, httpStatus);
    }
  }
}
