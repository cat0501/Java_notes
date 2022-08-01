package com.itheima.shiro.service;

import com.itheima.shiro.vo.FilterChainVo;

import java.util.List;

/**
 * @Description：过滤器同步接口
 */
public interface ShiroFilerChainService {

    /**
     * @Description 启动时，启动定时器，每隔2分钟动态加载数据库里面的过滤器链
     */
    void init();

    /**
     * @Description 使用DefaultFilterChainManager的addToChain方法构建过滤器链
     */
    void initFilterChains(List<FilterChainVo> filterChainVos);
}
