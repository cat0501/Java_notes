
package com.itheima.shiro.core.base;

import com.itheima.shiro.utils.ToString;
import lombok.Data;

import java.util.List;


/**
 * @Description 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息
 */
@Data
public class  ShiroUser extends ToString {

	/** serialVersionUID */
	private static final long serialVersionUID = -5024855628064590607L;

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 登录名称
	 */
	private String loginName;

	/**
	 * 真实姓名
	 */
	private String realName;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 密码
	 */
	private String passWord;

	/**
	 * 加密因子
	 */
	private String salt;

	/**
	 * 性别
	 */
	private Integer sex;

	/**
	 * 邮箱
	 */
	private String zipcode;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 固定电话
	 */
	private String tel;

	/**
	 * 电话
	 */
	private String mobil;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 职务
	 */
	private String duties;

	/**
	 * 排序
	 */
	private Integer sortNo;

	/**
	 * 是否有效
	 */
	private String enableFlag;
    
	private List<String> resourceIds;

	public ShiroUser() {
		super();
	}

	public ShiroUser(String id, String loginName) {
		super();
		this.id = id;
		this.loginName = loginName;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((loginName == null) ? 0 : loginName.hashCode());
		result = prime * result + ((mobil == null) ? 0 : mobil.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShiroUser other = (ShiroUser) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (loginName == null) {
			if (other.loginName != null)
				return false;
		} else if (!loginName.equals(other.loginName))
			return false;
		if (mobil == null) {
			if (other.mobil != null)
				return false;
		} else if (!mobil.equals(other.mobil))
			return false;
		return true;
	}
	
	
}
