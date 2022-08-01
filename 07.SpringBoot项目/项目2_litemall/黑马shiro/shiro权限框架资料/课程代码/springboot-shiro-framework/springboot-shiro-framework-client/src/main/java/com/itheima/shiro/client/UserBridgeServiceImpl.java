package com.itheima.shiro.client;

import com.itheima.shiro.constant.CacheConstant;
import com.itheima.shiro.core.SimpleCacheService;
import com.itheima.shiro.core.base.ShiroUser;
import com.itheima.shiro.core.base.SimpleMapCache;
import com.itheima.shiro.core.base.SimpleToken;
import com.itheima.shiro.core.bridge.UserBridgeService;
import com.itheima.shiro.face.UserAdapterFace;
import com.itheima.shiro.utils.BeanConv;
import com.itheima.shiro.utils.EmptyUtil;
import com.itheima.shiro.utils.ShiroUserUtil;
import com.itheima.shiro.vo.ResourceVo;
import com.itheima.shiro.vo.RoleVo;
import com.itheima.shiro.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.util.ByteSource;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * @Description 权限桥接器
 */
@Slf4j
@Component("userBridgeService")
public class UserBridgeServiceImpl implements UserBridgeService {

    @Reference(version = "1.0.0")
    private UserAdapterFace userAdapterFace;

    @Autowired
    private SimpleCacheService simpleCacheService;

    @javax.annotation.Resource(name = "redissonClientForShiro")
    private RedissonClient redissonClient;

    public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken,String realmName) {
        SimpleToken token = (SimpleToken)authcToken;
        UserVo user  = this.findUserByLoginName(token.getUsername());
        if(EmptyUtil.isNullOrEmpty(user)){
            throw new UnknownAccountException("账号不存在");
        }
        ShiroUser shiroUser = BeanConv.toBean(user, ShiroUser.class);
        String sessionId = ShiroUserUtil.getShiroSessionId();
        String cacheKeyResourcesIds = CacheConstant.RESOURCES_KEY_IDS+sessionId;
        shiroUser.setResourceIds(this.findResourcesIdsList(cacheKeyResourcesIds,user.getId()));
        String salt = user.getSalt();
        String password = user.getPassWord();
        return new SimpleAuthenticationInfo(shiroUser, password, ByteSource.Util.bytes(salt), realmName);
    }

    @Override
    public SimpleAuthorizationInfo getAuthorizationInfo(ShiroUser shiroUser) {
        UserVo user = BeanConv.toBean(shiroUser, UserVo.class);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String sessionId = ShiroUserUtil.getShiroSessionId();
        //查询用户拥有的角色
        String cacheKeyRole = CacheConstant.ROLE_KEY + sessionId;
        info.addRoles(this.findRoleList(cacheKeyRole, user.getId()));

        //查询用户拥有的资源
        String cacheKeyResources = CacheConstant.RESOURCES_KEY + sessionId;
        info.addStringPermissions(this.findResourcesList(cacheKeyResources, user.getId()));
        return info;
    }


    @Override
    public List<String> findRoleList(String cacheKeyRole, String userId) {
        List<RoleVo> roles = new ArrayList<RoleVo>();
        if (simpleCacheService.getCache(cacheKeyRole) != null) {
            roles = (List<RoleVo>) simpleCacheService.getCache(cacheKeyRole).get(cacheKeyRole);
        } else {
            roles = userAdapterFace.findRoleByUserId(userId);
            if (roles.size() > 0) {
                //用户角色存放到map
                Map<Object, Object> mapRole = new HashMap<Object, Object>();
                mapRole.put(cacheKeyRole, roles);
                //新建SimpleMapCache实例并放入缓存管理器
                SimpleMapCache cacheRole = new SimpleMapCache(cacheKeyRole, mapRole);
                simpleCacheService.creatCache(cacheKeyRole, cacheRole);
            }
        }
        List<String> rolesLabel = new ArrayList<String>();
        for (RoleVo role : roles) {
            rolesLabel.add(role.getLabel());
        }
        return rolesLabel;
    }


    @Override
    public List<String> findResourcesList(String cacheKeyResources,String userId) {
        List<ResourceVo> resourcesList = new ArrayList<ResourceVo>();
        if (simpleCacheService.getCache(cacheKeyResources) != null) {
            resourcesList = (List<ResourceVo>) simpleCacheService.getCache(cacheKeyResources).get(cacheKeyResources);
        } else {
            resourcesList = userAdapterFace.findResourceByUserId(userId);
            if (resourcesList.size() > 0) {
                //用户资源存放到map
                Map<Object, Object> mapResource = new HashMap<Object, Object>();
                mapResource.put(cacheKeyResources, resourcesList);
                //新建SimpleMapCache实例并放入缓存管理器
                SimpleMapCache cacheResource = new SimpleMapCache(cacheKeyResources, mapResource);
                simpleCacheService.creatCache(cacheKeyResources, cacheResource);
            }
        }
        List<String> resourcesLabel = new ArrayList<String>();
        for (ResourceVo resources : resourcesList) {
            resourcesLabel.add(resources.getLabel());
        }
        return resourcesLabel;
    }


    @Override
    public UserVo findUserByLoginName(String loginName) {
        String key = CacheConstant.FIND_USER_BY_LOGINNAME+loginName;
        RBucket<UserVo> rBucket = redissonClient.getBucket(key);
        UserVo user = rBucket.get();
        if (!EmptyUtil.isNullOrEmpty(user)) {
            return user;
        }else {
            user = userAdapterFace.findUserByLoginName(loginName);
            if (!EmptyUtil.isNullOrEmpty(user)) {
                rBucket.set(user, 300, TimeUnit.SECONDS);
                return user;
            }
        }
        rBucket.set(new UserVo(), 3, TimeUnit.SECONDS);
        return null;
    }

    @Override
    public List<String> findResourcesIdsList(String cacheKeyResources,String userId) {
        List<ResourceVo> resourcesList = new ArrayList<ResourceVo>();
        if (simpleCacheService.getCache(cacheKeyResources) != null) {
            resourcesList = (List<ResourceVo>) simpleCacheService.getCache(cacheKeyResources).get(cacheKeyResources);
        } else {
            resourcesList = userAdapterFace.findResourceByUserId(userId);
            if (resourcesList.size() > 0) {
                //用户资源存放到map
                Map<Object, Object> mapResource = new HashMap<Object, Object>();
                mapResource.put(cacheKeyResources, resourcesList);
                //新建SimpleMapCache实例并放入缓存管理器
                SimpleMapCache cacheResource = new SimpleMapCache(cacheKeyResources, mapResource);
                simpleCacheService.creatCache(cacheKeyResources, cacheResource);
            }
        }
        List<String> resourcesLabel = new ArrayList<String>();
        for (ResourceVo resources : resourcesList) {
            resourcesLabel.add(resources.getId());
        }
        return resourcesLabel;
    }

    @Override
    public void loadUserAuthorityToCache(ShiroUser user) {
        String sessionId = user.getSessionId();
        List<RoleVo> roles = userAdapterFace.findRoleByUserId(user.getId());
        //创建角色cachaeKey
        String cacheKeyRole = CacheConstant.ROLE_KEY + sessionId;
        //用户角色存放到map
        Map<Object, Object> mapRole = new HashMap<Object, Object>();
        mapRole.put(cacheKeyRole, roles);
        //新建SimpleMapCache实例并放入缓存管理器
        SimpleMapCache cacheRole = new SimpleMapCache(cacheKeyRole, mapRole);
        simpleCacheService.creatCache(cacheKeyRole, cacheRole);

        List<ResourceVo> resourcesList = userAdapterFace.findResourceByUserId(user.getId());
        if (resourcesList.size() > 0) {
            //创建资源cachaeKey
            String cacheKeyResources = CacheConstant.RESOURCES_KEY + sessionId;
            //用户资源存放到map
            Map<Object, Object> mapResource = new HashMap<Object, Object>();
            mapResource.put(cacheKeyResources, resourcesList);
            //新建SimpleMapCache实例并放入缓存管理器
            SimpleMapCache cacheResource = new SimpleMapCache(cacheKeyResources, mapResource);
            simpleCacheService.creatCache(cacheKeyResources, cacheResource);
        }
    }
}
