package com.itheima.shiro.face;

import com.itheima.shiro.vo.ResourceVo;
import com.itheima.shiro.vo.RoleVo;
import com.itheima.shiro.vo.UserVo;

import java.util.List;

/**
 * @Description：用户服务接口定义
 */
public interface UserAdapterFace {

    /**
     * @Description 按用户名查找用户
     * @param loginName 登录名
     * @return
     */
    UserVo findUserByLoginName(String loginName);

    /**
     * @Description 查找用户所有角色
     * @param userId 用户Id
     * @return
     */
    List<RoleVo> findRoleByUserId(String userId);

    /**
     * @Description 查询用户有资源
     * @param userId 用户Id
     * @return
     */
    List<ResourceVo> findResourceByUserId(String userId);

}
