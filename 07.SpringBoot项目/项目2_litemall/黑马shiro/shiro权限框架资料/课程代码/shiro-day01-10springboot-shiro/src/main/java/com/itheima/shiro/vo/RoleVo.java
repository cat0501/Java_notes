/*
 * <b>文件名</b>：RoleVo.java
 *
 * 文件描述：
 *
 *
 * 2017年11月8日  下午4:11:02
 */

package com.itheima.shiro.vo;

import com.itheima.shiro.pojo.Role;

/**
 * <b>类名：</b>RoleVo.java<br>
 * <p><b>标题：</b>意真（上海）金融软件2.0</p>
 * <p><b>描述：</b>意真（上海）金融统一构建系统</p>
 * <p><b>版权声明：</b>Copyright (c) 2017</p>
 * <p><b>公司：</b>意真（上海）金融信息服务有限公司 </p>
 * @author <font color='blue'>束文奇</font> 
 * @version 1.0.1
 * @date  2017年11月8日 下午4:11:02
 * @Description 角色vo
 */

public class RoleVo extends Role {
	
	/** serialVersionUID */
	private static final long serialVersionUID = -8242413497560284592L;
	
	/**是否拥有资源**/
	private String hasResourceIds;

	public String getHasResourceIds() {
		return hasResourceIds;
	}

	public void setHasResourceIds(String hasResourceIds) {
		this.hasResourceIds = hasResourceIds == null ? null : hasResourceIds.trim();
	}
	
	
}
