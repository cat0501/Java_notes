package com.itheima.shiro.service.impl;

import com.itheima.shiro.service.SecurityService;

/**
 * @Description：模拟数据库操作服务接口实现
 */
public class SecurityServiceImpl implements SecurityService {

    @Override
    public String findPasswordByLoginName(String loginName) {
        return "123";
    }
}
