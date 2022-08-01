package com.itheima.shiro.cache.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.itheima.shiro.cache.CacheWareService;
import com.itheima.shiro.pojo.CacheWare;
import com.itheima.shiro.utils.EmptyUtil;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @Description：缓存仓库服务实现
 */
@Service("cacheWareService")
public class CacheWareServiceImpl implements CacheWareService {

    private Multimap<String, CacheWare> cacheWareMap = ArrayListMultimap.create();

    @Override
    public void clearCacheWare() {
        cacheWareMap.clear();
    }

    @Override
    public void createCacheWare(Multimap<String, CacheWare> cacheWareMap) {
        this.cacheWareMap = cacheWareMap;
    }

    @Override
    public CacheWare queryCacheWare(String serviceName, String methodName) {
        if (EmptyUtil.isNullOrEmpty(serviceName)||EmptyUtil.isNullOrEmpty(methodName)){
            return null;
        }
        String key = serviceName+":"+methodName;
        Collection<CacheWare> cacheWares =cacheWareMap.get(key);
        return EmptyUtil.isNullOrEmpty(cacheWares)?null:cacheWares.iterator().next();
    }
}
