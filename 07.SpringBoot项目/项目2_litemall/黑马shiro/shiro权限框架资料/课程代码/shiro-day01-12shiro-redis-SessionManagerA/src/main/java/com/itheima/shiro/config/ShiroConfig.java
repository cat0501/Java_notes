package com.itheima.shiro.config;


import com.itheima.shiro.core.ShiroDbRealm;
import com.itheima.shiro.core.filter.RolesOrAuthorizationFilter;
import com.itheima.shiro.core.impl.RedisSessionDao;
import com.itheima.shiro.core.impl.ShiroDbRealmImpl;
import com.itheima.shiro.properties.PropertiesUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 权限配置类
 */
@Configuration
@ComponentScan(basePackages = "com.itheima.shiro.core")
@EnableConfigurationProperties({ShiroRedisProperties.class})
@Log4j2
public class ShiroConfig {

    @Autowired
    private ShiroRedisProperties shiroRedisProperties;

    @Bean("redissonClientForShiro")
    public RedissonClient redissonClient(){
        //获取当前redis节点信息
        String[] nodes = shiroRedisProperties.getNodes().split(",");
        //创建配置信息：1、单机redis配置 2、集群redis配置
        Config config = new Config();
        if (nodes.length==1){
            //1、单机redis配置
            config.useSingleServer().setAddress(nodes[0])
                    .setConnectTimeout(shiroRedisProperties.getConnectTimeout())
                    .setConnectionMinimumIdleSize(shiroRedisProperties.getConnectionMinimumidleSize())
                    .setConnectionPoolSize(shiroRedisProperties.getConnectPoolSize())
                    .setTimeout(shiroRedisProperties.getTimeout());
        }else if(nodes.length>1) {
            //2、集群redis配置
            config.useClusterServers().addNodeAddress(nodes)
                    .setConnectTimeout(shiroRedisProperties.getConnectTimeout())
                    .setMasterConnectionMinimumIdleSize(shiroRedisProperties.getConnectionMinimumidleSize())
                    .setMasterConnectionPoolSize(shiroRedisProperties.getConnectPoolSize())
                    .setTimeout(shiroRedisProperties.getTimeout());
        }else {
            return null;
        }
        //创建redission的客户端，交于spring管理
        RedissonClient client = Redisson.create(config);
        return client;
    }

    /**
     * @Description 创建cookie对象
     */
    @Bean(name="sessionIdCookie")
    public SimpleCookie simpleCookie(){
        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setName("ShiroSession");
        return simpleCookie;
    }

    /**
     * @Description 权限管理器
     * @param
     * @return
     */
    @Bean(name="securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroDbRealm());
        securityManager.setSessionManager(shiroSessionManager());
        return securityManager;
    }

    /**
     * @Description 自定义RealmImpl
     */
    @Bean(name="shiroDbRealm")
    public ShiroDbRealm shiroDbRealm(){
        return new ShiroDbRealmImpl();
    }


    @Bean("redisSessionDao")
    public RedisSessionDao redisSessionDao(){
        RedisSessionDao redisSessionDao = new RedisSessionDao();
        redisSessionDao.setGlobalSessionTimeout(shiroRedisProperties.getGlobalSessionTimeout());
        return redisSessionDao;
    }
    /**
     * @Description 会话管理器
     */
    @Bean(name="sessionManager")
    public DefaultWebSessionManager shiroSessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDao());
        sessionManager.setSessionValidationSchedulerEnabled(false);
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(simpleCookie());
        sessionManager.setGlobalSessionTimeout(shiroRedisProperties.getGlobalSessionTimeout());
        return sessionManager;
    }

    /**
     * @Description 保证实现了Shiro内部lifecycle函数的bean执行
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * @Description AOP式方法级权限检查
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * @Description 配合DefaultAdvisorAutoProxyCreator事项注解权限校验
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(defaultWebSecurityManager());
        return new AuthorizationAttributeSourceAdvisor();
    }

    /**
     * @Description 拦截器链
     */
    private Map<String, String> filterChainDefinition(){
        List<Object> list  = PropertiesUtil.propertiesShiro.getKeyList();
        Map<String, String> map = new LinkedHashMap<>();
        for (Object object : list) {
            String key = object.toString();
            String value = PropertiesUtil.getShiroValue(key);
            log.info("读取防止盗链控制：---key{},---value:{}",key,value);
            map.put(key, value);
        }
        return map;
    }

    /**
     * @Description 自定义拦截器定义
     */
    private Map<String, Filter> filters() {
        Map<String, Filter> map = new HashMap<String, Filter>();
        map.put("role-or", new RolesOrAuthorizationFilter());
        return map;
    }

    /**
     * @Description Shiro过滤器
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(defaultWebSecurityManager());
        //使自定义拦截器生效
        shiroFilter.setFilters(filters());
        shiroFilter.setFilterChainDefinitionMap(filterChainDefinition());
        shiroFilter.setLoginUrl("/login");
        shiroFilter.setUnauthorizedUrl("/login");
        return shiroFilter;
    }

}
