package com.creolophus.liuyi.common.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author magicnana
 * @date 2019/3/1 下午6:38
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceAutoConfig {

  private static final Logger logger = LoggerFactory.getLogger(DataSourceAutoConfig.class);

  @Bean
  @ConditionalOnMissingBean
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSource dataSource() {
    if (logger.isInfoEnabled()) {
      logger.info("start -> DataSource");
    }
    return new DruidDataSource();
  }
}
