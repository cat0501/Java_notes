/*
 * <b>文件名</b>：ShiroConstant.java
 *
 * 文件描述：
 *
 *
 * 2017年11月17日  下午1:36:42
 */

package com.itheima.shiro.constant;



/**
 * @Description shiro的常量类
 */

public class ShiroConstant {

	/** 后台系统 **/
	public static  final String PLATFORM_MGT = "platform_mgt";

	//未登录
	public static final String NO_LOGIN_CODE = "1";
	public static final String NO_LOGIN_MESSAGE = "请先进行登录";

	//登录成功
	public static final String LOGIN_SUCCESS_CODE = "2";
	public static final String LOGIN_SUCCESS_MESSAGE = "登录成功";

	//登录失败
	public static final String LOGIN_FAILURE_CODE = "3";
	public static final String LOGIN_FAILURE_MESSAGE = "登录失败";

	//注销
	public static final String LOGOUT_CODE = "4";
	public static final String LOGOUT_MESSAGE = "注销成功";

	//缺少用户权限
	public static final String NO_AUTH_CODE = "5";
	public static final String NO_AUTH_MESSAGE = "权限不足";

	//缺少用户角色
	public static final String NO_ROLE_CODE = "6";
	public static final String NO_ROLE_MESSAGE = "用户角色不符合";
}
