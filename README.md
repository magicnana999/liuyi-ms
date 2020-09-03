# liuyi-microservice
##### develop framework of microservice，base on SpringCloud &amp; Kubernetes

# Release Note

* 1.0.0
* 1.0.1 update OkHttp3Util method name
* 1.0.2 remove fastjson

# Components

* Spring Boot 2.0.6 Release
* Spring Cloud Finchley.SR2
* Work in Kubernetes
* Beetlsql[https://gitee.com/xiandafu/beetlsql.git]
* Jedis
* Swagger (SpringFox2.9.2)
* RocketMQ

# Guide
##### 1.Require spring boot starter parent 2.0.6-RELEASE
```
  <parent>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-parent</artifactId>
      <version>2.0.6.RELEASE</version>
  </parent>
```

##### 2. github package repository
```
 <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
```

##### 3. dependency
```
    <dependency>
        <groupId>com.github.magicnana999</groupId>
        <artifactId>liuyi-ms</artifactId>
        <version>x</version>
    </dependency>
```


##### 4. Main class
```
@SpringBootApplication(scanBasePackages = "com.creolophus")
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.creolophus.liuyi.feign")
@EnableSwagger2
@EnableScheduling
@EnableAsync
public class Start extends WebStart {

   @Override
    public ApiInterceptor apiInterceptor() {
        return new ApiInterceptor();
    }

}
```

# Generate Entity
##### 1. config datasource info in src/test/java/org/beetl/sql/GenPojo
##### 2. config table name 
##### 3. Run test

##### More info see [liuyi-ms-springcloud-demo](https://github.com/magicnana999/liuyi-ms-springcloud-demo) & [liuyi-ms-k8s-demo](https://github.com/magicnana999/liuyi-ms-k8s-demo)
