/*
 * <b>文件名</b>：ResourceServiceMapper.java
 *
 * 文件描述：
 *
 *
 * 2017年11月8日  下午3:28:31
 */

package com.itheima.shiro.mappercustom;

import com.itheima.shiro.pojo.Resource;

import java.util.List;
import java.util.Map;

/**
 * @Description 资源服务
 */

public interface ResourceServiceMapper {
	
	/**
	 * @Description 按父Id查询树
	 * @param
	 * @return
	 */
	List<Resource> findResourceTreeVoByParentId(Map<String, Object> map);

}
