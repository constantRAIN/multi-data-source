package com.multi.datasource.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: xiaodian-order
 * @author: zxliuyu@didiglobal.com
 * @create: 2021-01-14 14:55
 **/
@Configuration
public class DataSourceConfig {

    @Bean("dataSourceReadProperties")
    @ConditionalOnProperty(prefix = "spring.datasource.read", name = {"url", "username", "password"})
    public DataSourceReadProperties dataSourceReadProperties() {
        DataSourceReadProperties dataSourceReadProperties = new DataSourceReadProperties();
        return dataSourceReadProperties;
    }

    @Bean("dataSourceWriteProperties")
    @ConditionalOnProperty(prefix = "spring.datasource.write", name = {"url", "username", "password"})
    public DataSourceWriteProperties dataSourceWriteProperties() {
        DataSourceWriteProperties dataSourceWriteProperties = new DataSourceWriteProperties();
        return dataSourceWriteProperties;
    }

    @Bean("dataSourceRead")
    @ConditionalOnBean(name = "dataSourceReadProperties")
    public DataSource getDataSourceRead(@Qualifier("dataSourceReadProperties") DataSourceReadProperties dataSourceReadProperties) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(dataSourceReadProperties.getUrl());
        dataSource.setUsername(dataSourceReadProperties.getUsername());
        dataSource.setPassword(dataSourceReadProperties.getPassword());
        return dataSource;
    }

    @Bean("dataSourceWrite")
    @ConditionalOnBean(name = "dataSourceWriteProperties")
    public DataSource getDataSourceWrite(@Qualifier("dataSourceWriteProperties") DataSourceWriteProperties dataSourceWriteProperties) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(dataSourceWriteProperties.getUrl());
        dataSource.setUsername(dataSourceWriteProperties.getUsername());
        dataSource.setPassword(dataSourceWriteProperties.getPassword());
        return dataSource;
    }


    /**
     * 设置数据源路由，通过该类中的determineCurrentLookupKey决定使用哪个数据源
     */
    @Bean("routingDataSource")
    public AbstractRoutingDataSource routingDataSource(@Qualifier("dataSourceWrite") DataSource dataSourceWrite,
                                                       @Qualifier("dataSourceRead") DataSource dataSourceRead) {
        MyAbstractRoutingDataSource proxy = new MyAbstractRoutingDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>(2);
        targetDataSources.put(DbContextHolder.WRITE, dataSourceWrite);
        targetDataSources.put(DbContextHolder.READ, dataSourceRead);
        proxy.setDefaultTargetDataSource(dataSourceWrite);
        proxy.setTargetDataSources(targetDataSources);
        return proxy;
    }

    /**
     * 多数据源需要自己设置sqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("routingDataSource") AbstractRoutingDataSource routingDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(routingDataSource);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // mybatis的XML的配置
        bean.setMapperLocations(resolver.getResources("classpath*:mapper/*Mapper.xml"));
        return bean.getObject();
    }

    /**
     * 设置事务，事务需要知道当前使用的是哪个数据源才能进行事务处理
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("routingDataSource") AbstractRoutingDataSource routingDataSource) {
        return new DataSourceTransactionManager(routingDataSource);
    }
}
