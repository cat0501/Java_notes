package com.itheima.shiro.cache;

import com.itheima.shiro.pojo.CacheWare;
import com.itheima.shiro.vo.ResourceVo;

/**
 * @Description：缓存仓库同步接口
 */
public interface CacheWareSyncService {

    /**
     * @Description 初始化缓存仓库
     */
    void initCacheWare();

    /**
     * @Description 同步缓存仓库
     */
    void refreshCacheWare();

    /**
     * @Description 资源转换缓存仓库可执行对象
     */
    CacheWare resourceConvCacheWare(ResourceVo resource);

    /**
     * @Description 初始化代理对象
     * @param interfaceClass 接口
     * @param loadbalance 算法
     * @param version 版本
     * @param timeout 超时时间
     * @param retries 重试次数
     */
    Object initProxy(Class<?> interfaceClass,
                     String loadbalance,
                     String version,
                     Integer timeout,
                     Integer retries);

    /**
     * @Description 回收资源
     */
    void destoryCacheWare();
}

