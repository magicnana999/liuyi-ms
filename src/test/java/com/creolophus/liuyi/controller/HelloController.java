package com.creolophus.liuyi.controller;

import com.creolophus.liuyi.common.api.ApiResult;
import com.creolophus.liuyi.common.base.AbstractController;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author magicnana
 * @date 2021/7/13 16:17
 */
@RestController
@RequestMapping(value = "/test/hello")
public class HelloController extends AbstractController {

  @RequestMapping(value = "/foo1", method = RequestMethod.GET)
  public ApiResult getVerify(HttpServletResponse httpServletResponse) {
    return new ApiResult();
  }

}
