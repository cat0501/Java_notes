package com.itheima.shiro.cache;

import com.google.common.collect.Multimap;
import com.itheima.shiro.pojo.CacheWare;

/**
 * @Description：缓存仓库服务
 */
public interface CacheWareService {

    /**
     * @Description 清除缓存
     */
    void clearCacheWare();

    /**
     * @Description 向map容器中创建缓存
     * @param cacheWareMap
     */
    void createCacheWare(Multimap<String, CacheWare> cacheWareMap);

    /**
     * @Description 获得缓存仓库执行对象
     * @param serviceName 服务名
     * @param methodName  方法名
     * @return {@link CacheWare}
     *
     */
    CacheWare queryCacheWare(String serviceName, String methodName);


}