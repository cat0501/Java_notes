/*
 * <b>文件名</b>：RoleVo.java
 *
 * 文件描述：
 *
 *
 * 2017年11月8日  下午4:11:02
 */

package com.itheima.shiro.vo;

import com.itheima.shiro.utils.ToString;
import lombok.*;

import java.io.Serializable;

/**
 * @Description 角色vo
 */
@Getter
@Setter
public class RoleVo extends ToString {

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 角色标识
	 */
	private String label;

	/**
	 * 角色描述
	 */
	private String description;

	/**
	 * 排序
	 */
	private Integer sortNo;

	/**
	 * 是否有效
	 */
	private String enableFlag;
	
	/** serialVersionUID */
	private static final long serialVersionUID = -8242413497560284592L;
	
	/**是否拥有资源**/
	private String hasResourceIds;

	
}
