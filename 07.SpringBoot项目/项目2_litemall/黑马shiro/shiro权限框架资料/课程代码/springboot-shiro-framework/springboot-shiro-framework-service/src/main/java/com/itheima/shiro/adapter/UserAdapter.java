package com.itheima.shiro.adapter;

import com.itheima.shiro.pojo.Resource;
import com.itheima.shiro.pojo.Role;
import com.itheima.shiro.pojo.User;

import java.util.List;


/**
 * @Description 后台登陆用户适配器接口
 */

public interface UserAdapter {
	
	/**
	 * @Description 按用户名查找用户
	 * @param loginName 登录名
	 * @return
	 */
	User findUserByLoginName(String loginName);

	/**
	 * @Description 查找用户所有角色
	 * @param userId 用户Id
	 * @return
	 */
	List<Role> findRoleByUserId(String userId);

	/**
	 * @Description 查询用户有那些资源
	 * @param userId 用户Id
	 * @return
	 */
	List<Resource> findResourceByUserId(String userId);

}
