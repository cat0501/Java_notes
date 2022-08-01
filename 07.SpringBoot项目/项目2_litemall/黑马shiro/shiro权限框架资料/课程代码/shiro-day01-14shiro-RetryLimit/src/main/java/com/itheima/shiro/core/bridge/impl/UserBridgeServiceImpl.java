package com.itheima.shiro.core.bridge.impl;

import com.itheima.shiro.constant.CacheConstant;
import com.itheima.shiro.core.SimpleCacheService;
import com.itheima.shiro.core.adapter.UserAdapter;
import com.itheima.shiro.core.base.ShiroUser;
import com.itheima.shiro.core.base.SimpleMapCache;
import com.itheima.shiro.core.bridge.UserBridgeService;
import com.itheima.shiro.pojo.Resource;
import com.itheima.shiro.pojo.Role;
import com.itheima.shiro.pojo.User;
import com.itheima.shiro.utils.EmptyUtil;
import com.itheima.shiro.utils.ShiroUtil;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description：用户信息桥接（后期会做缓存）
 */
@Component("userBridgeService")
public class UserBridgeServiceImpl implements UserBridgeService {

    @Autowired
    UserAdapter userAdapter;

    @Autowired
    SimpleCacheService simpleCacheService;

    @Override
    public User findUserByLoginName(String loginName) {
        String key = CacheConstant.FIND_USER_BY_LOGINNAME + loginName;
        //获取缓存
        Cache<Object, Object> cache = simpleCacheService.getCache(key);
        //缓存存在
        if (!EmptyUtil.isNullOrEmpty(cache)){
            return (User) cache.get(key);
        }
        //缓存不存在
        User user = userAdapter.findUserByLoginName(loginName);
        if (!EmptyUtil.isNullOrEmpty(user)){
            Map<Object,Object> map = new HashMap<>();
            map.put(key, user);
            SimpleMapCache simpleMapCache = new SimpleMapCache(key, map);
            simpleCacheService.creatCache(key, simpleMapCache);
        }
        return user;
    }

    @Override
    public List<String> findResourcesIds(String userId) {
        String sessionId = ShiroUtil.getShiroSessionId();
        String key = CacheConstant.RESOURCES_KEY_IDS+sessionId;
        List<Resource> resources = new ArrayList<>();
        //获取缓存
        Cache<Object, Object> cache = simpleCacheService.getCache(key);
        //缓存存在
        if (!EmptyUtil.isNullOrEmpty(cache)){
            resources = (List<Resource>) cache.get(key);
        }else {
        //缓存不存在
            resources = userAdapter.findResourceByUserId(userId);
            if (!EmptyUtil.isNullOrEmpty(resources)){
                Map<Object,Object> map = new HashMap<>();
                map.put(key, resources);
                SimpleMapCache simpleMapCache = new SimpleMapCache(key, map);
                simpleCacheService.creatCache(key,simpleMapCache );
            }

        }

        List<String> ids = new ArrayList<>();
        for (Resource resource : resources) {
            ids.add(resource.getId());
        }
        return ids;
    }

    @Override
    public AuthorizationInfo getAuthorizationInfo(ShiroUser shiroUser) {
        String sessionId = ShiroUtil.getShiroSessionId();
        String roleKey = CacheConstant.ROLE_KEY+sessionId;
        String resourcesKey = CacheConstant.RESOURCES_KEY+sessionId;
        //查询用户对应的角色标识
        List<String> roleList = this.findRoleList(roleKey,shiroUser.getId());
        //查询用户对于的资源标识
        List<String> resourcesList = this.findResourcesList(resourcesKey,shiroUser.getId());
        //构建鉴权信息对象
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(roleList);
        simpleAuthorizationInfo.addStringPermissions(resourcesList);
        return simpleAuthorizationInfo;
    }

    @Override
    public List<String> findRoleList(String key,String userId){
        List<Role> roles = new ArrayList<>();
        //获得缓存
        Cache<Object, Object> cache = simpleCacheService.getCache(key);
        //缓存存在
        if (!EmptyUtil.isNullOrEmpty(cache)){
            roles = (List<Role>) cache.get(key);
        }else {
        //缓存不存在
            roles = userAdapter.findRoleByUserId(userId);
            if (!EmptyUtil.isNullOrEmpty(roles)){
                Map<Object,Object> map = new HashMap<>();
                map.put(key, roles);
                SimpleMapCache simpleMapCache = new SimpleMapCache(key, map);
                simpleCacheService.creatCache(key,simpleMapCache );
            }
        }

        List<String> roleLabel = new ArrayList<>();
        for (Role role : roles) {
            roleLabel.add(role.getLabel());
        }
        return roleLabel;
    }

    @Override
    public List<String> findResourcesList(String key,String userId){
        List<Resource> resources = new ArrayList<>();
        //获得缓存
        Cache<Object, Object> cache = simpleCacheService.getCache(key);
        //缓存存在
        if (!EmptyUtil.isNullOrEmpty(cache)){
            resources = (List<Resource>) cache.get(key);
        }else {
            //缓存不存在
            resources = userAdapter.findResourceByUserId(userId);
            if (!EmptyUtil.isNullOrEmpty(resources)){
                Map<Object,Object> map = new HashMap<>();
                map.put(key, resources);
                SimpleMapCache simpleMapCache = new SimpleMapCache(key, map);
                simpleCacheService.creatCache(key,simpleMapCache );
            }
        }
        List<String> resourceLabel = new ArrayList<>();
        for (Resource resource : resources) {
            resourceLabel.add(resource.getLabel());
        }
        return resourceLabel;
    }




    @Override
    public void loadUserAuthorityToCache(ShiroUser shiroUser) {
        String sessionId = ShiroUtil.getShiroSessionId();
        String roleKey = CacheConstant.ROLE_KEY+sessionId;
        String resourcesKey = CacheConstant.RESOURCES_KEY+sessionId;
        //查询用户对应的角色标识
        List<String> roleList = this.findRoleList(roleKey,shiroUser.getId());
        //查询用户对于的资源标识
        List<String> resourcesList = this.findResourcesList(resourcesKey,shiroUser.getId());
    }
}
