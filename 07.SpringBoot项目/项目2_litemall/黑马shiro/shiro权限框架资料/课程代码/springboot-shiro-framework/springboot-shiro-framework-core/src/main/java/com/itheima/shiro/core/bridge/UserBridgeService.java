package com.itheima.shiro.core.bridge;

import com.itheima.shiro.core.base.ShiroUser;
import com.itheima.shiro.vo.UserVo;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.SimpleAuthorizationInfo;

import java.util.List;


/**
 * @Description 用户桥接器
 * @param
 * @return
 */
public interface UserBridgeService {


	/**
	 * @Description 认证方法
	 * @param authcToken
	 * @return
	 */
	AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken, String realmName);

	/**
	 * @Description 授权验证方法
	 * @param user 登录对象
	 * @return
	 */
	SimpleAuthorizationInfo getAuthorizationInfo(ShiroUser user);

	/**
	 * @Description 查询用户拥有的角色
	 * @param userId 用户id
	 * @return
	 */
	List<String> findRoleList(String cacheKeyRole, String userId);

	/**
	 * @Description 查询用户拥有的资源
	 * @param userId 用户id
	 * @return
	 */
	List<String> findResourcesList(String cacheKeyResources, String userId);

	/**
	 * @Description 按用户名查找用户
	 * @param loginName 登录名称
	 * @return
	 */
	UserVo findUserByLoginName(String loginName);

	/**
	 * @Description 获得资源ID的list
	 * @param
	 * @return
	 */
	List<String> findResourcesIdsList(String cacheKeyResources, String userId);


	/**
	 * @Description 缓存用户信息
	 * @param user
	 * @return
	 */
	void loadUserAuthorityToCache(ShiroUser user);
}
