package com.itheima.shiro.service;

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
}
