package com.creolophus.liuyi.controller;

import com.creolophus.liuyi.test.AbstractTest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author magicnana
 * @date 2021/7/13 16:19
 */
public class HelloControllerTest extends AbstractTest {

  @Test
  public void foo1() throws Exception {
    String response =
        mvc.perform(
            MockMvcRequestBuilders.get("/test/hello/foo1")
                .header("Authorization", token)
                .header("x-liuyi-app-key", "be7fcee88904a31d40064240ac13d931")
                .header("User-Agent", USERAGENT)
                .header("x-liuyi-device-id", "2q3rwfdfgsd")
                .header("x-liuyi-platform-key", "7.0")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
    System.out.println("======================== 打印结果 ========================");
    System.out.println(response);
  }
}
