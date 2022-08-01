package com.itheima.shiro.service;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @Description：登录服务
 */
public interface LoginService {


    /**
     * @Description 登入操作
     * @param token 账户凭证信息
     * @return 是否登录成功
     */
    boolean login(UsernamePasswordToken token);

    /**
     * @Description 登出操作
     */
    void logout();
}
