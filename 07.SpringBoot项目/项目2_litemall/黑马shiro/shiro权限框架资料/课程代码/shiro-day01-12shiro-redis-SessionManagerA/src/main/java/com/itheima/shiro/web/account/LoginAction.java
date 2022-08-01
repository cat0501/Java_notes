/*
 * <b>文件名</b>：LoginAction.java
 *
 * 文件描述：
 *
 *
 * 2017-4-6  上午11:15:23
 */

package com.itheima.shiro.web.account;


import com.itheima.shiro.constant.ShiroConstant;
import com.itheima.shiro.core.bridge.UserBridgeService;
import com.itheima.shiro.service.LoginService;
import com.itheima.shiro.service.UserService;
import com.itheima.shiro.vo.LoginVo;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;


/**
 * @Description 登陆
 */
@Controller
@RequestMapping(value="/login")
@Log4j2
public class LoginAction {
	
	@Autowired
	UserBridgeService userBridgeService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	LoginService loginService;

	/**
	 * @Description 首页
	 * @param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView login(){

		return new ModelAndView("/account/login");
	}

	/**
	 * @Description 登录操作
	 * @param loginVo 登录对象
	 * @return
	 */
	@RequestMapping(value="/usersLongin",method=RequestMethod.POST)
	public ModelAndView usersLongin(LoginVo loginVo){
		ModelAndView modelAndView = new ModelAndView("/account/login");
		String shiroLoginFailure=null;
		Map<String, String> map = new HashMap<String, String>();
		try {
			loginVo.setSystemCode(ShiroConstant.PLATFORM_MGT);
			loginService.route(loginVo);
		} catch (UnknownAccountException ex) {
			log.error("登陆异常:{}",ex);
			shiroLoginFailure="登录失败 - 账号不存在！";
			map.put("loginName", loginVo.getLoginName());
			map.put("shiroLoginFailure", shiroLoginFailure);
			modelAndView.addAllObjects(map);
		} catch (IncorrectCredentialsException ex) {
			log.error("登陆异常:{}",ex);
			shiroLoginFailure="登录失败 - 密码不正确！";
			map.put("shiroLoginFailure", shiroLoginFailure);
			map.put("loginName", loginVo.getLoginName());
			modelAndView.addAllObjects(map);
		} catch (Exception ex) {
			log.error("登陆异常:{}",ex);
			shiroLoginFailure="登录失败 - 请联系平台管理员！";
			map.put("shiroLoginFailure", shiroLoginFailure);
			map.put("loginName", loginVo.getLoginName());
			modelAndView.addAllObjects(map);
		}
		if (shiroLoginFailure!=null) {
			return modelAndView;
		}
		modelAndView.setViewName("redirect:/menus/system");
		return modelAndView;
	}
	
	/**
	 * @Description 退出系统
	 */
	@RequestMapping(value="/usersLongout")
	public String usersLongout(){
		Subject subject  = SecurityUtils.getSubject();
		if (subject!=null) {
			subject.logout();
		}
		return "/account/login";
	}
	
	/**
	 * @Description 编辑密码
	 */
	@RequestMapping(value = "/editorpassword")
	public ModelAndView editorPassword(){
		return new ModelAndView("/user/user-editor-password");
	}
	
	/**
	 * @Description 保存新密码
	 */
	@RequestMapping(value="/saveNewPassword")
	@ResponseBody
	public Boolean saveNewPassword(String oldPassword,String plainPassword)
			throws InvocationTargetException, IllegalAccessException {
		return userService.saveNewPassword(oldPassword,plainPassword);
	}
}
