/*
 * <b>文件名</b>：LoginAction.java
 *
 * 文件描述：
 *
 *
 * 2017-4-6  上午11:15:23
 */

package com.itheima.shiro.web;


import com.itheima.shiro.base.BaseResponse;
import com.itheima.shiro.core.base.ShiroUser;
import com.itheima.shiro.core.bridge.UserBridgeService;
import com.itheima.shiro.service.LoginService;
import com.itheima.shiro.utils.ShiroUserUtil;
import com.itheima.shiro.vo.LoginVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @Description 登陆
 */
@Controller
@RequestMapping(value="/login")
@Log4j2
public class LoginAction {
	
	@Autowired
	LoginService loginService;

	@Autowired
	UserBridgeService userBridgeService;

	/**
	 * @Description jwt的json登录方式
	 * @param loginVo
	 * @return
	 */
	@RequestMapping("login-jwt")
	@ResponseBody
	public BaseResponse LoginForJwt(@RequestBody LoginVo loginVo){
		BaseResponse baseResponse = loginService.routeForJwt(loginVo);
		ShiroUser shiroUser = ShiroUserUtil.getShiroUser();
		userBridgeService.loadUserAuthorityToCache(shiroUser);
		return baseResponse;
	}

}
