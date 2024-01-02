package com.creolophus.lucky.common.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author magicnana
 * @date 2021/8/23 15:00
 */
@Configuration
@ConditionalOnClass(RedisTemplate.class)
public class RedisAutoConfig {

  private static final Logger logger = LoggerFactory.getLogger(RedisAutoConfig.class);

  //  @Bean
  //  public LettuceClientConfigurationBuilderCustomizer
  // lettuceClientConfigurationBuilderCustomizer(
  //      RedisProperties properties) {
  //    return builder -> {
  //      if (builder instanceof LettucePoolingClientConfigurationBuilder) {
  //        if (properties.getLettuce() != null) {
  //          if (properties.getLettuce().getPool() != null) {
  //            properties.getLettuce().getPool().setMaxActive(MAX_THREADS);
  //            properties.getLettuce().getPool().setMaxIdle(MIN_THREADS);
  //            properties.getLettuce().getPool().setMinIdle(MIN_THREADS);
  //          }
  //        }
  //        LettucePoolingClientConfigurationBuilder poolingBuilder =
  //            (LettucePoolingClientConfigurationBuilder) builder;
  //        poolingBuilder.poolConfig(getPoolConfig(properties.getLettuce().getPool()));
  //
  //        if (logger.isInfoEnabled()) {
  //          logger.info("start -> LettuceClientConfigurationBuilderCustomizer");
  //        }
  //      }
  //    };
  //  }
  //
  //  private GenericObjectPoolConfig<?> getPoolConfig(Pool properties) {
  //    GenericObjectPoolConfig<?> config = new GenericObjectPoolConfig<>();
  //    config.setMaxTotal(properties.getMaxActive());
  //    config.setMaxIdle(properties.getMaxIdle());
  //    config.setMinIdle(properties.getMinIdle());
  //    if (properties.getTimeBetweenEvictionRuns() != null) {
  //
  // config.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRuns().toMillis());
  //    }
  //    if (properties.getMaxWait() != null) {
  //      config.setMaxWaitMillis(properties.getMaxWait().toMillis());
  //    }
  //    return config;
  //  }

  @Bean
  @ConditionalOnMissingBean
  public RedisTemplate<String, Object> redisTemplate(
      RedisConnectionFactory redisConnectionFactory) {

    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);

    // String的序列化
    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
    template.setKeySerializer(stringRedisSerializer);
    template.setHashKeySerializer(stringRedisSerializer);
    template.setValueSerializer(new GsonRedisSerializer());
    template.setHashValueSerializer(new GsonRedisSerializer());
    template.afterPropertiesSet();

    if (logger.isInfoEnabled()) {
      logger.info("start -> RedisTemplate");
    }
    return template;
  }

  @Bean
  @ConditionalOnMissingBean
  public RedisClient redisClient(RedisTemplate<String, Object> redisTemplate) {
    RedisClient rc = new RedisClient(redisTemplate);
    if (logger.isInfoEnabled()) {
      logger.info("start -> RedisClient");
    }
    return rc;
  }
}
