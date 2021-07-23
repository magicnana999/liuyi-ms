package com.creolophus.liuyi.common.rocketmq;

import org.apache.rocketmq.client.impl.producer.DefaultMQProducerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(DefaultMQProducerImpl.class)
public class RocketMQAutoConfig {

  private static final Logger logger = LoggerFactory.getLogger(RocketMQAutoConfig.class);

  @Bean
  @ConditionalOnMissingBean
  public RocketMQProducer rocketMQProducer(RocketMQSetting rocketMQSetting) {
    if (logger.isInfoEnabled()) {
      logger.info(
          "start RocketMQ {}:{}",
          rocketMQSetting.getNamesrvAddr(),
        rocketMQSetting.getProducerGroupName());
    }
    return new RocketMQProducer(
        rocketMQSetting.getNamesrvAddr(), rocketMQSetting.getProducerGroupName());
  }

  @Bean
  @ConditionalOnMissingBean
  @ConfigurationProperties(prefix = "spring.rocketmq")
  public RocketMQSetting rocketMQSetting() {
    return new RocketMQSetting();
  }
}
