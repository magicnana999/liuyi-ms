package com.creolophus.liuyi.common.jedis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Configuration
@ConditionalOnClass({Jedis.class})
public class JedisAutoConfig {

  private static final Logger logger = LoggerFactory.getLogger(JedisAutoConfig.class);

  @Value("${spring.redis.host:127.0.0.1}")
  public String host;

  @Value("${spring.redis.port:6379}")
  private int port;

  @Value("${spring.redis.password:My password is 123456}")
  private String password;

  @Value("${spring.redis.timeout:10000}")
  private int timeout;

  @Bean
  @ConditionalOnMissingBean
  public JedisClient redisClient() {
    if (logger.isInfoEnabled()) {
      logger.info("start -> JedisClient {}:{}", host, port);
    }
    JedisPool jedisPool = new JedisPool(null, host, port, timeout, password);
    return new JedisSingleClient(jedisPool);
  }

//  @Bean
//  @ConditionalOnMissingBean
//  @ConfigurationProperties(prefix = "spring.redis.jedis.pool")
//  public GenericObjectPoolConfig redisGenericObjectPoolConfig() {
//    return new GenericObjectPoolConfig();
//  }

  public void setHost(String host) {
    this.host = host;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public void setTimeout(int timeout) {
    this.timeout = timeout;
  }
}
