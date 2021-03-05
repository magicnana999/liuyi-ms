# liuyi-microservice
##### develop framework of microservice，base on SpringCloud &amp; Kubernetes

# Release Note

* 1.0.0
* 1.0.1 update OkHttp3Util method name
* 1.0.2 remove fastjson
* 1.0.3 update dependence
* 1.0.4 完善 JSON 的相关方法
* 1.1.0 增加@Api,以实现 SpringSecurity 外部的 API 的安全.
* 1.1.1 修改 AbstractVo 为 AbstractObject接口
* 1.2.0 内部接口用 Gson 序列化,外部接口用 jackson 序列化. 基于@API 这个注解实现.

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

    public static void main(String[] args) {
        SpringApplication.run(Start.class, args);
    }

}
```

# 区分 Api 范围
假设存在如下场景:
在一个需要 SpringSecurity 的外网中,存在某些 API 需要内部调用,不能外部调用.首先要加入到 security 的忽略列表,这样可以不用传 token,但是这样导致任何人都可以访问这个 api,不太安全.这里新增一个@Api,默认 inter 的 scope,当一个被 springSecurity 忽略的 api 有@Api 时,需要验证是内网来源才可以.保不齐以后需要其他的 scope,这里预留.


##### 1.把需要内网访问的 API 排除在 SpringSecurity 之外.
##### 2.把需要内网访问的 API 加上 @Api 注解
##### 3.SpringSecurity 管理的 Api 同时有 @Api 注解会怎样? 这样的话,两个验证机制同时满足才可以.
##### 4.SpringSecurity 外部的 API 上如果有 token,那么也会验证 token,正确的 token 会有 userId,不正确的 token 也没关系,反正没有UsernamePasswordAuthenticationToken在 SpringSecurity 的SecurityContextHolder中,也会正确进行,因为他在 SpringSecurity 之外.

# Generate Entity
##### 1. config datasource info in src/test/java/org/beetl/sql/GenPojo
##### 2. config table name 
##### 3. Run test

##### More info see [liuyi-ms-springcloud-demo](https://github.com/magicnana999/liuyi-ms-springcloud-demo) & [liuyi-ms-k8s-demo](https://github.com/magicnana999/liuyi-ms-k8s-demo)
