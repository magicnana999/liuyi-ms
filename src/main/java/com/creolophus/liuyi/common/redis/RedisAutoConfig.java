package com.creolophus.liuyi.common.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Configuration
@ConditionalOnClass({Jedis.class})
public class RedisAutoConfig {

    private static final Logger logger = LoggerFactory.getLogger(RedisAutoConfig.class);


    @Value("${spring.jedis.host:127.0.0.1}")
    public String host;

    @Value("${spring.jedis.port:6379}")
    private int port;

    @Value("${spring.jedis.password:My password is 123456}")
    private String password;

    @Value("${spring.jedis.timeout:10000}")
    private int timeout;

    public void setHost(String host) {
        this.host = host;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationProperties(prefix="spring.jedis")
    public GenericObjectPoolConfig redisGenericObjectPoolConfig(){
        return new GenericObjectPoolConfig();
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisClient redisClient(@Qualifier("redisGenericObjectPoolConfig") GenericObjectPoolConfig genericObjectPoolConfig){
        logger.info("start RedisClient {}:{}", host, port);
        JedisPool jedisPool = new JedisPool(genericObjectPoolConfig,host,port,timeout,password);
        return new RedisSingleClient(jedisPool);
    }
}
