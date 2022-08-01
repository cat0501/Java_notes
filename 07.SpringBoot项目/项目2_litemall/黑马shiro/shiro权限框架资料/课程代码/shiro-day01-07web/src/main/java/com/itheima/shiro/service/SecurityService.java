package com.itheima.shiro.service;

import java.util.List;
import java.util.Map;

/**
 * @Description：模拟数据库操作服务接口
 */
public interface SecurityService {

    /**
     * @Description 查找用户密码
     * @param loginName 用户名称
     * @return 密码
     */
    Map<String,String> findPasswordByLoginName(String loginName);

    /**
     * @Description 查询角色
     * @param loginName 用户名
     * @return 角色字符串列表
     */
    List<String> findRoleByLoginName(String loginName);

    /**
     * @Description 查询资源
     * @param loginName 用户名
     * @return 资源字符串列表
     */
    List<String> findPermissionByLoginName(String loginName);
}
