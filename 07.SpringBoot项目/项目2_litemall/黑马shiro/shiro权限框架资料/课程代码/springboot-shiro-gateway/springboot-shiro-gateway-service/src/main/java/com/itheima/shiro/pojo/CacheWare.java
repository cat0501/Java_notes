package com.itheima.shiro.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @Description 缓存仓库可执行类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CacheWare {

    //执行类
    private String serviceName;

    //执行方法
    private String methodName;

    //方法对象
    private Method method;

    //参数类型
    private Class<?> methodParamsClass;

    //代理类
    private Object proxy;


}
