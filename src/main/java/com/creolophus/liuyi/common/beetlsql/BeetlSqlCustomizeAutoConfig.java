package com.creolophus.liuyi.common.beetlsql;

import com.ibeetl.starter.BeetlSqlCustomize;
import com.ibeetl.starter.BeetlSqlSingleCondition;
import com.ibeetl.starter.BeetlSqlSingleConfig;
import org.beetl.sql.core.Interceptor;
import org.beetl.sql.ext.spring4.SqlManagerFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author magicnana
 * @date 2019/6/12 下午5:28
 */
@Configuration
@ConditionalOnClass(SqlManagerFactoryBean.class)
@AutoConfigureBefore(BeetlSqlSingleConfig.class)
public class BeetlSqlCustomizeAutoConfig {

  private static final Logger logger = LoggerFactory.getLogger(BeetlSqlCustomizeAutoConfig.class);

  @Bean
  @ConditionalOnMissingBean
  @Conditional(BeetlSqlSingleCondition.class)
  public BeetlSqlCustomize buildBeetlSqlCustomize() {
    return (sqlManager) -> {
      sqlManager.setInterceptors(new Interceptor[]{new LineSqlPrintInterceptor()});
      if (logger.isInfoEnabled()) {
        logger.info("start setInterceptor of LineSqlPrintInterceptor");
      }
    };
  }
}
