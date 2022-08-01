package com.zjl.litemall.admin.config;

import com.zjl.litemall.admin.shiro.AdminAuthorizingRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author cat
 * @description
 * @date 2022/4/5 下午12:16
 */
@Configuration
public class ShiroConfig {



    @Bean
    public Realm realm() {
        return new AdminAuthorizingRealm();
    }

    // 此类可以在shiro-spring包中看到，具体路径为：org.apache.shiro.spring.web.ShiroFilterFactoryBean
    // 根据类注释，可以知道此类作为管理shiro中核心filter集合和spring的重要集成手段。
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/admin/auth/kaptcha", "anon");
        filterChainDefinitionMap.put("/admin/auth/login", "anon");
        filterChainDefinitionMap.put("/admin/auth/401", "anon");
        filterChainDefinitionMap.put("/admin/auth/index", "anon");
        filterChainDefinitionMap.put("/admin/auth/403", "anon");
        filterChainDefinitionMap.put("/admin/index/*", "anon");

        filterChainDefinitionMap.put("/admin/**", "authc");
        shiroFilterFactoryBean.setLoginUrl("/admin/auth/401");
        shiroFilterFactoryBean.setSuccessUrl("/admin/auth/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/admin/auth/403");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

}
