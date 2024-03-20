package com.creolophus.liuyi.common.jedis;

import lombok.Data;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
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
@Data
public class RedisAutoConfig {

  private static final Logger logger = LoggerFactory.getLogger(RedisAutoConfig.class);

  @Value("${spring.redis.host}")
  private String host;

  @Value("${spring.redis.port}")
  private int port;

  @Value("${spring.redis.password}")
  private String password;
  @Value("${spring.redis.timeout}")
  private int timeout;

  @Value("${spring.redis.pool.max-active}")
  private int maxActive;

  @Value("${spring.redis.pool.max-idle}")
  private int maxIdle;

  @Value("${spring.redis.pool.min-idle}")
  private int minIdle;

  @Bean
  @ConditionalOnMissingBean
  public RedisClient redisClient() {
    logger.info("start RedisClient");
    GenericObjectPoolConfig config = new GenericObjectPoolConfig();
    config.setMaxIdle(maxIdle);
    config.setMaxTotal(maxActive);
    config.setMinIdle(minIdle);
    config.setMaxWaitMillis(1000);
    config.setTestOnBorrow(true);
    JedisPool jedisPool = new JedisPool(config, host, port, 10000, password);
    return new RedisSingleClient(jedisPool);
  }
}
