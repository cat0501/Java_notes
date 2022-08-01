package com.itheima.shiro.vo;

import java.util.List;

/**
 * @description:		菜单显示类
 */
public class MenuVo {

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

	@Override
	public String toString() {
		return "MenuVo [icon=" + icon + ", menuid=" + menuid + ", menuname="
				+ menuname + ", menus=" + menus + ", url=" + url + "]";
	}

	public String getMenuid() {
		return menuid;
	}
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getMenuname() {
		return menuname;
	}
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<MenuVo> getMenus() {
		return menus;
	}
	public void setMenus(List<MenuVo> menus) {
		this.menus = menus;
	}
	
}
