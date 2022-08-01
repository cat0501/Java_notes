package com.itheima.shiro.vo;

import com.itheima.shiro.utils.ToString;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @description:		菜单显示类
 */
@Getter
@Setter
public class MenuVo extends ToString {

	/**菜单Id**/
	private String menuid;
	
	/**菜单图标**/
	private String icon;
	
	/**菜单名称**/
	private String menuname;
	
	/**菜单链接**/
	private String url;
	
	/**菜单子菜单**/
	private List<MenuVo> menus;
	
	public MenuVo() {
		super();
	}

	public MenuVo(String menuid, String icon, String menuname, String url,List<MenuVo> menus) {
		this.menuid = menuid;
		this.icon = icon;
		this.menuname = menuname;
		this.url = url;
		this.menus = menus;
	}
	
	public MenuVo(String menuid, String icon, String menuname, String url) {
		super();
		this.menuid = menuid;
		this.icon = icon;
		this.menuname = menuname;
		this.url = url;
	}

	
}
