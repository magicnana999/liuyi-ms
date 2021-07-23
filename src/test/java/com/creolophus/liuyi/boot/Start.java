package com.creolophus.liuyi.boot;

import com.creolophus.liuyi.common.api.WebStart;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author magicnana
 * @date 2021/7/13 16:15
 */
@SpringBootApplication(scanBasePackages = "com.creolophus")
@EnableSwagger2
public class Start extends WebStart {}
