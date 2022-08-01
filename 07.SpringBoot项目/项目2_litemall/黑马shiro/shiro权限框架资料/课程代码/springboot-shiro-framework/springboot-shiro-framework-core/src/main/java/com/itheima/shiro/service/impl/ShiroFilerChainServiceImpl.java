package com.itheima.shiro.service.impl;

import com.itheima.shiro.core.bridge.FilterChainBridgeService;
import com.itheima.shiro.core.impl.CustomDefaultFilterChainManager;
import com.itheima.shiro.service.ShiroFilerChainService;
import com.itheima.shiro.vo.FilterChainVo;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Description：过滤器同步接口实现
 */
@Service("shiroFilerChainService")
public class ShiroFilerChainServiceImpl implements ShiroFilerChainService {

    //注入过滤器链管理者
    @Autowired
    private CustomDefaultFilterChainManager defaultFilterChainManager;

    //使用桥接器读取数据库内的过滤器链
    @Autowired
    FilterChainBridgeService filterChainBridgeService;

    private Map<String, NamedFilterList> defaultFilterChains;

    //定时器
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    @Override
    @PostConstruct
    public void init() {
        defaultFilterChains = new LinkedHashMap<>();
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                initFilterChains(filterChainBridgeService.findFilterChainList()) ;
            }
        }, 0, 120, TimeUnit.SECONDS);
    }

    @Override
    public void initFilterChains(List<FilterChainVo> filterChainVos) {

        //1、首先删除以前老的filter，构建默认的过滤器链
        defaultFilterChainManager.getFilterChains().clear();
        //2、加载过滤器链
        for (FilterChainVo urlFilterVo : filterChainVos) {
            String url = urlFilterVo.getUrl();
            String filterName = urlFilterVo.getFilterName();
            String[] filterNames = filterName.split(",");
            for (String name : filterNames) {
                //注册所有filter，包含自定义的过滤器
                switch(name){
                    case "anon":
                        defaultFilterChainManager.addToChain(url, name);
                        break;
                    case "authc":
                        defaultFilterChainManager.addToChain(url, name);
                        break;
                    case "roles":
                        defaultFilterChainManager.addToChain(url, name, urlFilterVo.getRoles());
                        break;
                    case "perms":
                        defaultFilterChainManager.addToChain(url, name,urlFilterVo.getPermissions());
                        break;
                    case "role-or":
                        defaultFilterChainManager.addToChain(url, name,urlFilterVo.getRoles());
                        break;
                    case "kicked-out":
                        defaultFilterChainManager.addToChain(url, name);
                        break;
                    case "jwt-authc":
                        defaultFilterChainManager.addToChain(url, name);
                        break;
                    case "jwt-roles":
                        defaultFilterChainManager.addToChain(url, name, urlFilterVo.getRoles());
                        break;
                    case "jwt-perms":
                        defaultFilterChainManager.addToChain(url, name,urlFilterVo.getPermissions());
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
