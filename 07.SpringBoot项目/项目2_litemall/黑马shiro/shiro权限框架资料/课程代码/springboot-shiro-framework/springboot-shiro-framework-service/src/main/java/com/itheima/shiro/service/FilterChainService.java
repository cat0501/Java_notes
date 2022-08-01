package com.itheima.shiro.service;

import com.itheima.shiro.pojo.FilterChain;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @Description 过滤器链service接口层
 */
public interface FilterChainService {

    /**
     * @Description 查询所有有效的过滤器链
     * @return
     */
    List<FilterChain> findFilterChainList();

    /**
     * @Description 过滤器链的分页查询
     * @param
     * @return
     */
     List<FilterChain> findFilterChainList(FilterChain filterChain, Integer rows, Integer page);


    /**
     * @Description 统计过滤器链的分页查询
     * @param
     * @return
     */
     long countFilterChainList(FilterChain filterChain);

    /**
     * @Description 按Id查询过滤器链
     * @param
     * @return
     */
     FilterChain getFilterChainById(String id);

    /**
     * @Description 保存或更新过滤器链
     * @param
     * @return
     */
     boolean saveOrUpdateFilterChain(FilterChain filterChain) throws IllegalAccessException, InvocationTargetException;

    /**
     * @Description 过滤器链删除
     * @param
     * @return
     */
     Boolean updateByIds(List<String> list, String enableFlag);




}