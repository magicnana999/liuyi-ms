package com.creolophus.liuyi.common.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.ibeetl.starter.BeetlSqlSingleCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

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
    @ConfigurationProperties(prefix="spring.datasource")
    @Conditional(BeetlSqlSingleCondition.class)
    public DataSource dataSource(){
        logger.info("start DataSource");
        return new DruidDataSource();
    }

//    @Bean
//    @ConditionalOnMissingBean
//    public DataSource dataSource(DataSourceSetting dataSourceConfig) {
//        logger.info("start DataSource");
//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setMaxActive(dataSourceConfig.getMaxActive());
//        druidDataSource.setInitialSize(dataSourceConfig.getInitialSize());
//        druidDataSource.setMaxWait(dataSourceConfig.getMaxWait());
//        druidDataSource.setMinIdle(dataSourceConfig.getMinIdle());
//        druidDataSource.setTimeBetweenEvictionRunsMillis(dataSourceConfig.getTimeBetweenEvictionRunsMillis());
//        druidDataSource.setMinEvictableIdleTimeMillis(dataSourceConfig.getMinEvictableIdleTimeMillis());
//        druidDataSource.setValidationQuery(dataSourceConfig.getValidationQuery());
//        druidDataSource.setTestWhileIdle(dataSourceConfig.isTestWhileIdle());
//        druidDataSource.setTestOnBorrow(dataSourceConfig.isTestOnBorrow());
//        druidDataSource.setTestOnReturn(dataSourceConfig.isTestOnReturn());
//        druidDataSource.setRemoveAbandoned(dataSourceConfig.isRemoveAbandoned());
//        druidDataSource.setRemoveAbandonedTimeout(dataSourceConfig.getRemoveAbandonedTimeout());
//        druidDataSource.setLogAbandoned(dataSourceConfig.isLogAbandoned());
//
//        druidDataSource.setUrl(dataSourceConfig.getUrl());
//        druidDataSource.setUsername(dataSourceConfig.getUsername());
//        druidDataSource.setPassword(dataSourceConfig.getPassword());
//        druidDataSource.setDriverClassName(dataSourceConfig.getDriver());
//        return druidDataSource;
//    }

}
