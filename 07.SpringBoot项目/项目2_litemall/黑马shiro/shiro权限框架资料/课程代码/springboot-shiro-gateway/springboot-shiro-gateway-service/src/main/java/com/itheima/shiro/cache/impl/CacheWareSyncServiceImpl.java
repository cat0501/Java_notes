package com.itheima.shiro.cache.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.itheima.shiro.cache.CacheWareService;
import com.itheima.shiro.cache.CacheWareSyncService;
import com.itheima.shiro.core.bridge.ResourceBridgeService;
import com.itheima.shiro.pojo.CacheWare;
import com.itheima.shiro.utils.EmptyUtil;
import com.itheima.shiro.vo.ResourceVo;
import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Description：缓存仓库同步服务接口实现
 */
@Service("cacheWareSyncService")
@Log4j2
public class CacheWareSyncServiceImpl implements CacheWareSyncService {

    @Value("${itheima.resource.systemcode}")
    private String systemCode;

    @Autowired
    CacheWareService cacheWareService;

    @Autowired
    ResourceBridgeService resourceBridgeService;

    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private RegistryConfig registryConfig;

    //线程池
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    @Override
    @PostConstruct
    public void initCacheWare() {
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                refreshCacheWare();
            }
        }, 0, 2, TimeUnit.MINUTES);

    }

    @Override
    public void refreshCacheWare() {
        //获得网关资源
        List<ResourceVo> resources = resourceBridgeService.findValidResourceVoAll(systemCode);
        //判断网关资源是否为空，则清空所有map容器中的可执行对象
        if (EmptyUtil.isNullOrEmpty(resources)){
            cacheWareService.clearCacheWare();
        }
        Multimap<String,CacheWare> cacheWareMultimap = ArrayListMultimap.create();
        //把网关资源转换为缓存仓库可执行对象
        for (ResourceVo resource : resources) {
            if (EmptyUtil.isNullOrEmpty(resource.getServiceName())||
                EmptyUtil.isNullOrEmpty(resource.getMethodName())){
                log.warn("网关资源定义不完整：{}",resource.toString());
                continue;
            }
            CacheWare cacheWare = resourceConvCacheWare(resource);
            if (!EmptyUtil.isNullOrEmpty(cacheWare)){
                String key = cacheWare.getServiceName()+":"+cacheWare.getMethodName();
                cacheWareMultimap.put(key, cacheWare);
            }
        }

        //放入map容器
        cacheWareService.createCacheWare(cacheWareMultimap);
    }

    @Override
    public CacheWare resourceConvCacheWare(ResourceVo resource) {
        //网关资源服务接口
        Class<?> serviceClass = null;
        try {
            serviceClass = Class.forName(resource.getServiceName());
        } catch (ClassNotFoundException e) {
            log.error("为在容器中发现{}接口类",resource.getServiceName());
            return null;
        }
        String serviceNameAll = resource.getServiceName();
        String serviceName = serviceNameAll.substring(serviceNameAll.lastIndexOf(".")+1).toLowerCase();
        //对应的执行方法
        Method[] methods = serviceClass.getDeclaredMethods();
        Method methodTarget = null;
        for (Method method : methods) {
            if (method.getName().equals(resource.getMethodName())){
                methodTarget = method;
                break;
            }
        }
        //如果方法获取失败
        if (EmptyUtil.isNullOrEmpty(methodTarget)){
            log.error("为在容器中发现{}方法",resource.getMethodName());
            return null;
        }
        //获得方法上的传入参数
        Class<?>[] parameterTypes = methodTarget.getParameterTypes();
        Class<?> methodParamsClassTarget = null;
        for (Class<?> parameterType : parameterTypes) {
            if (parameterType.getName().equals(resource.getMethodParam())){
                methodParamsClassTarget = parameterType;
                break;
            }
        }
        //构建服务代理类
        Object proxy = initProxy(serviceClass, resource.getLoadbalance(), resource.getDubboVersion(), resource.getTimeout(), resource.getRetries());
        //构建缓存仓库可执行对象
        CacheWare cacheWare = CacheWare.builder()
                .serviceName(serviceName)
                .methodName(resource.getMethodName())
                .method(methodTarget)
                .methodParamsClass(methodParamsClassTarget)
                .proxy(proxy).build();
        return cacheWare;
    }

    @Override
    public Object initProxy(Class<?> interfaceClass, String loadbalance, String version, Integer timeout, Integer retries) {
        ReferenceConfig<Object> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setLoadbalance(EmptyUtil.isNullOrEmpty(loadbalance)?"random":loadbalance);
        referenceConfig.setInterface(interfaceClass);
        referenceConfig.setVersion(version);
        referenceConfig.setTimeout(EmptyUtil.isNullOrEmpty(timeout)?20000:timeout);
        referenceConfig.setCheck(false);
        referenceConfig.setRetries(EmptyUtil.isNullOrEmpty(retries)?0:retries);
        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        return cache.get(referenceConfig);
    }

    @Override
    public void destoryCacheWare() {
        executorService.shutdownNow();
    }
}
