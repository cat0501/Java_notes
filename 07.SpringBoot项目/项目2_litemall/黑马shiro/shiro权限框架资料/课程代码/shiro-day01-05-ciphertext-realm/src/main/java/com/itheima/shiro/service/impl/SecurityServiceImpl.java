package com.itheima.shiro.service.impl;

import com.itheima.shiro.service.SecurityService;
import com.itheima.shiro.tools.DigestsUtil;

import java.util.Map;

/**
 * @Description：模拟数据库操作服务接口实现
 */
public class SecurityServiceImpl implements SecurityService {

    @Override
    public Map<String,String> findPasswordByLoginName(String loginName) {
        return DigestsUtil.entryptPassword("123");
    }
}
