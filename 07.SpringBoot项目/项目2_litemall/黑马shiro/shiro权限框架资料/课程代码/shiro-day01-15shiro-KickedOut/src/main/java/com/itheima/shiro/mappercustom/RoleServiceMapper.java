/*
 * <b>文件名</b>：RoleServiceMapper.java
 *
 * 文件描述：
 *
 *
 * 2017年11月9日  上午9:49:10
 */

package com.itheima.shiro.mappercustom;

import com.itheima.shiro.pojo.Role;

import java.util.List;
import java.util.Map;

/**
 * @Description 角色服务层mapper
 * @param
 * @return
 */
public interface RoleServiceMapper {
	
	/**
	 * @Description 查询角色拥有的资源Id字符串
	 * @param
	 * @return
	 */
	List<String> findRoleHasResourceIds(Map<String, Object> map);


	/**
	 * @Description 查询任务角色
	 * @param
	 * @return
	 */
	List<Role> findRoleDetailByLenderId(Map<String, Object> map);
}
