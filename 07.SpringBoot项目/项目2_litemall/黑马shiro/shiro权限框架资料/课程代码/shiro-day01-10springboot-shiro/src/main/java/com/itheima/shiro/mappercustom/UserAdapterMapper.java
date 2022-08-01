/*
 * <b>文件名</b>：UserAdapterMapper.java
 *
 * 文件描述：
 *
 *
 * 2017年11月8日  上午9:49:14
 */

package com.itheima.shiro.mappercustom;

import com.itheima.shiro.pojo.Resource;
import com.itheima.shiro.pojo.Role;

import java.util.List;
import java.util.Map;


/**
 * @Description 用户适配器Mapper
 * @param
 * @return
 */
public interface UserAdapterMapper {
	
	/**
	 * @Description 按用户Id查找对应角色
	 * @param
	 * @return
	 */
	public List<Role> findRoleByUserId(Map<String, Object> map);
	
	/**
	 * @Description 按用户Id查找对应资源
	 * @param
	 * @return
	 */
	public List<Resource> findResourceByUserId(Map<String, Object> map);

}
