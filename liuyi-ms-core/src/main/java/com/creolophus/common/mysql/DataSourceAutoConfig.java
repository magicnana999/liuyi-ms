package com.creolophus.common.mysql;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author magicnana
 * @date 2019/3/1 下午6:38
 */
@EnableTransactionManagement
@Configuration
public class DataSourceAutoConfig {

  private static final Logger logger = LoggerFactory.getLogger(DataSourceAutoConfig.class);

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSource dataSource() {
    HikariDataSource ds = new HikariDataSource();
    if (logger.isInfoEnabled()) {
      logger.info("start -> DataSource");
    }
    return ds;
  }

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnClass(com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor.class)
  public MybatisPlusInterceptor mybatisPlusInterceptor() {
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
    interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
    return interceptor;
  }
}
