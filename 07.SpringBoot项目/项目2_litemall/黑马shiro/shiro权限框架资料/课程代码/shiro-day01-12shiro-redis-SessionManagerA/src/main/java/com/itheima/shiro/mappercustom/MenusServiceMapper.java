/*
 * <b>文件名</b>：MenusServiceMapper.java
 *
 * 文件描述：
 *
 *
 * 2017年11月8日  下午1:42:00
 */

package com.itheima.shiro.mappercustom;

import com.itheima.shiro.pojo.Resource;
import com.itheima.shiro.vo.MenuVo;

import java.util.List;
import java.util.Map;

/**
 * @Description 菜单服务器层 Mapper 
 */

public interface MenusServiceMapper {

	/**
	 * @Description 查询每个系统的顶级菜单
	 * @param
	 * @return
	 */
	public List<Resource> findTopLevel(Map<String, Object> map);
	
	/**
	 * @Description 查询子菜单
	 * @param
	 * @return
	 */
	public List<MenuVo> findByResourceType(Map<String, Object> map);
}
