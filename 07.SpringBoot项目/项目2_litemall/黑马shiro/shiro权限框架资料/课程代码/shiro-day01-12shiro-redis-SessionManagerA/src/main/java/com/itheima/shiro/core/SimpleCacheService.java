package com.itheima.shiro.core;

import org.apache.shiro.cache.Cache;

/**
 * @Description：实现缓存管理服务
 */
public interface SimpleCacheService {

    /**
     * @Description 创建缓存
     * @param cacheName 缓存名称
     * @param  cache 缓存对象
     * @return
     */
    void creatCache(String cacheName, Cache<Object,Object> cache);

    /**
     * @Description 获得缓存
     * @param cacheName 缓存名称
     * @return 缓存对象
     */
    Cache<Object,Object> getCache(String cacheName);

    /**
     * @Description 删除缓存
     * @param cacheName 缓存名称
     * @return
     */
    void removeCache(String cacheName);

    /**
     * @Description 更新缓存
     * @param cacheName 缓存名称
     * @param  cache 新的缓存对象
     * @return
     */
    void updateCache(String cacheName,Cache<Object,Object> cache);
}
