package com.itheima.shiro.core.bridge.impl;

import com.itheima.shiro.core.adapter.UserAdapter;
import com.itheima.shiro.core.base.ShiroUser;
import com.itheima.shiro.core.bridge.UserBridgeService;
import com.itheima.shiro.pojo.Resource;
import com.itheima.shiro.pojo.Role;
import com.itheima.shiro.pojo.User;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description：用户信息桥接（后期会做缓存）
 */
@Component("userBridgeService")
public class UserBridgeServiceImpl implements UserBridgeService {

    @Autowired
    UserAdapter userAdapter;

    @Override
    public User findUserByLoginName(String loginName) {
        return userAdapter.findUserByLoginName(loginName);
    }

    @Override
    public List<String> findResourcesIds(String userId) {
        List<Resource> resources = userAdapter.findResourceByUserId(userId);
        List<String> ids = new ArrayList<>();
        for (Resource resource : resources) {
            ids.add(resource.getId());
        }
        return ids;
    }

    @Override
    public AuthorizationInfo getAuthorizationInfo(ShiroUser shiroUser) {
        //查询用户对应的角色标识
        List<String> roleList = this.findRoleList(shiroUser.getId());
        //查询用户对于的资源标识
        List<String> resourcesList = this.findResourcesList(shiroUser.getId());
        //构建鉴权信息对象
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(roleList);
        simpleAuthorizationInfo.addStringPermissions(resourcesList);
        return simpleAuthorizationInfo;
    }

    @Override
    public List<String> findRoleList(String userId){
        List<Role> roles = userAdapter.findRoleByUserId(userId);
        List<String> roleLabel = new ArrayList<>();
        for (Role role : roles) {
            roleLabel.add(role.getLabel());
        }
        return roleLabel;
    }

    @Override
    public List<String> findResourcesList(String userId){
        List<Resource> resources = userAdapter.findResourceByUserId(userId);
        List<String> resourceLabel = new ArrayList<>();
        for (Resource resource : resources) {
            resourceLabel.add(resource.getLabel());
        }
        return resourceLabel;
    }


}
