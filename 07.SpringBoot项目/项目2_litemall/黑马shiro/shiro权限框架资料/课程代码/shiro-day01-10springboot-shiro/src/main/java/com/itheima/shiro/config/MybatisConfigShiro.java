package com.itheima.shiro.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.itheima.shiro.interceptor.ModifyArgsValueInterceptor;
import com.itheima.shiro.utils.IpAddrUtil;
import com.itheima.shiro.utils.SeqGenerator;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.logging.log4j2.Log4j2Impl;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @Description 数据库配置
 */
@Log4j2
@Configuration
@MapperScan(basePackages = {"com.itheima.shiro.mapper", "com.itheima.shiro.mappercustom"},
        sqlSessionFactoryRef = "sqlSessionFactoryBeanShiro")
@EnableConfigurationProperties({ShiroDataSourceProperties.class})
public class MybatisConfigShiro {


    @Autowired
    ShiroDataSourceProperties dataSourceProperties;


    private AtomikosDataSourceBean handleXaProperties(AtomikosDataSourceBean atomikosDataSourceBean) {

        Properties dataSourceHashMap = new Properties();
        dataSourceHashMap.put("driverClassName", dataSourceProperties.getDriverClassName());
        dataSourceHashMap.put("url", dataSourceProperties.getUrl());
        dataSourceHashMap.put("password", dataSourceProperties.getPassword());
        dataSourceHashMap.put("username", dataSourceProperties.getUsername());
        dataSourceHashMap.put("minIdle", dataSourceProperties.getMinIdle());
        dataSourceHashMap.put("maxActive", dataSourceProperties.getMaxActive());
        dataSourceHashMap.put("maxWait", dataSourceProperties.getMaxWait());
        dataSourceHashMap.put("validationQuery", dataSourceProperties.getValidationQuery());
        dataSourceHashMap.put("testOnBorrow", dataSourceProperties.getTestOnBorrow());
        dataSourceHashMap.put("testOnReturn", dataSourceProperties.getTestOnReturn());
        dataSourceHashMap.put("testWhileIdle", dataSourceProperties.getTestWhileIdle());
        dataSourceHashMap.put("logAbandoned", dataSourceProperties.getLogAbandoned());
        dataSourceHashMap.put("filters", dataSourceProperties.getFilters());
        dataSourceHashMap.put("initialSize", dataSourceProperties.getInitialSize());
        dataSourceHashMap.put("maxEvictableIdleTimeMillis", dataSourceProperties.getMaxLifeTime());
        atomikosDataSourceBean.setXaProperties(dataSourceHashMap);
        return atomikosDataSourceBean;
    }

    @Bean(name = "dataSourceShiro", initMethod = "init", destroyMethod = "close")
    public AtomikosDataSourceBean dataSourceShiro() {
        log.info("dataSourceShiro:抽象类创建开始!IP=" + IpAddrUtil.hostIpAddr());
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setUniqueResourceName(dataSourceProperties.getDataSourceName());
        atomikosDataSourceBean.setXaDataSourceClassName(dataSourceProperties.getXaDataSourceClassName());

        // 连接池最小连接数量
        atomikosDataSourceBean.setMinPoolSize(dataSourceProperties.getMinIdle());
        // 连接池最大连接数量
        atomikosDataSourceBean.setMaxPoolSize(dataSourceProperties.getMaxActive());
        // 连接池中获取连接等待超时时间
        atomikosDataSourceBean.setBorrowConnectionTimeout(dataSourceProperties.getMaxWait());
        // 最大获取数据时间(默认：5分钟)
        atomikosDataSourceBean.setReapTimeout(20);
        // 最大闲置时间，超过最小连接池连接的连接将将关闭
        atomikosDataSourceBean.setMaxIdleTime(60);
        // 连接回收时间
        atomikosDataSourceBean.setMaintenanceInterval(60);
        atomikosDataSourceBean.setMaxLifetime(dataSourceProperties.maxLifeTime);
        try {
            // 最大等待获取连接池datasources时间
            atomikosDataSourceBean.setLoginTimeout(60);
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("dataSourceShiro检查超时!IP=" + IpAddrUtil.hostIpAddr());
        }
        atomikosDataSourceBean.setTestQuery(dataSourceProperties.getValidationQuery());
        handleXaProperties(atomikosDataSourceBean);

        log.info("dataSourceShiro:抽象类创建完成!!IP=" + IpAddrUtil.hostIpAddr());
        return atomikosDataSourceBean;
    }

    @Bean(name = "sqlSessionFactoryBeanShiro")
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSourceShiro());
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(dataSourceProperties.getMapperLocations()));
        factoryBean.setTypeAliasesPackage(dataSourceProperties.getTypeAliasesPackage());
        factoryBean.setPlugins(new Interceptor[]{new ModifyArgsValueInterceptor(seqGeneratorShiro(),
                dataSourceProperties.getWorkId(), dataSourceProperties.getPrimaryKey())});
        //配置日志选型
        org.apache.ibatis.session.Configuration configuration =
                new org.apache.ibatis.session.Configuration();
        configuration.setLogImpl(Log4j2Impl.class);
        factoryBean.setConfiguration(configuration);
        return factoryBean;
    }

    @Bean("seqGeneratorShiro")
    public SeqGenerator seqGeneratorShiro() {
        return new SeqGenerator(dataSourceProperties.getWorkId());
    }

}
