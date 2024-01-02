package com.creolophus.lucky.common.cloud;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import java.nio.charset.Charset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author magicnana
 * @date 2019/1/2 上午11:14
 */
public class CustomErrorDecoder implements ErrorDecoder {

  private static Logger logger = LoggerFactory.getLogger(CustomErrorDecoder.class);

  @Override
  public Exception decode(String methodKey, Response response) {

    try {
      String json = Util.toString(response.body().asReader(Charset.forName("UTF-8")));

      if (response.status() >= 400 && response.status() < 500) {
        return new HystrixBadRequestException(json);
      } else {
        return feign.FeignException.errorStatus(methodKey, response);
      }
    } catch (Throwable e) {
      return new HystrixBadRequestException(e.getMessage());
    }
  }
}
