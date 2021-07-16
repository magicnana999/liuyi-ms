package com.creolophus.liuyi.common.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.ibeetl.starter.BeetlSqlSingleCondition;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author magicnana
 * @date 2019/3/1 下午6:38
 */
@Configuration
@ConditionalOnClass({DruidDataSource.class})
public class DataSourceAutoConfig {

  private static final Logger logger = LoggerFactory.getLogger(DataSourceAutoConfig.class);


  @Bean
  @ConditionalOnMissingBean
  @ConfigurationProperties(prefix = "spring.datasource")
  @Conditional(BeetlSqlSingleCondition.class)
  public DataSource dataSource() {
    logger.info("start DataSource");
    return new DruidDataSource();
  }

}
