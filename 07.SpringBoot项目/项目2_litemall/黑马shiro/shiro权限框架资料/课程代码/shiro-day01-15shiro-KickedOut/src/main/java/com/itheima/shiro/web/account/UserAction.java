package com.itheima.shiro.web.account;

import com.itheima.shiro.constant.SuperConstant;
import com.itheima.shiro.pojo.User;
import com.itheima.shiro.service.UserService;
import com.itheima.shiro.utils.EmptyUtil;
import com.itheima.shiro.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description  用户管理
 */
@Controller
@RequestMapping(value = "/user")
public class UserAction{

	@Autowired
	private UserService userService;
	
	/** 
	 * 
	 * @Description: 初始化列表
	 */
	@RequestMapping(value = "/listInitialize")
	public String listInitialize() {
		return "/user/user-listInitialize";
	}

	/**
	 * 
	 * list(分页列表)
	 * 
	 * @Description: 分页列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public ModelMap list(UserVo userVo, Integer rows, Integer page) {
		List<User> dateList = userService.findUserList(userVo, rows, page);
		Long total = userService.countUserList(userVo);
		ModelMap modelMap = new ModelMap();
		modelMap.put("rows", dateList);// 数据表格数据传递
		modelMap.put("total", total);// 统计条数传递
		return modelMap;
	}

	/**
	 * @Description: 新增,修改对象
	 * @param userVo
	 * @throws
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Boolean save(@ModelAttribute("user") UserVo userVo) throws IllegalAccessException, InvocationTargetException {
		return userService.saveOrUpdateUser(userVo);
	}
	
	/**
	 * 
	 * @Description: 验证用户唯一
	 *@param loginName
	 *@param oldLoginName
	 *@return 传人参数
	 * @throws
	 */
	@RequestMapping(value = "/checkLoginName")
	@ResponseBody
	public String checkLoginName(@RequestParam(value = "loginName") String loginName, @RequestParam(value = "oldLoginName") String oldLoginName) {
		if (!EmptyUtil.isNullOrEmpty(oldLoginName) && loginName.equals(oldLoginName)) {
			return "pass";
		}
		if (userService.getUserByLoginNameOrMobilOrEmail(loginName)) {
			return "pass";
		} else {
			return "fail";
		}
	}
	
	
	
	/**
	 * 
	 * @Description: 禁用用户
	 * @param ids
	 */
	@RequestMapping(value="/start")
	@ResponseBody
	public String start(@RequestParam(value="ids")String ids){

		String []idsStrings=ids.split(",");
		List<String>list=new ArrayList<String>();
		for (String id : idsStrings) {
			list.add(id);
		}
		Boolean flag = userService.updateByIds(list, SuperConstant.YES);
		if (flag) {
			return "success";
		}
		return "fail";
	}
	
	/**
	 * @Description: 启用
	 * @param ids
	 */
	@RequestMapping(value="/stop")
	@ResponseBody
	public String stop(@RequestParam(value="ids")String ids){

		String []idsStrings=ids.split(",");
		List<String>list=new ArrayList<String>();
		for (String id : idsStrings) {
			list.add(id);
		}
		Boolean flag = userService.updateByIds(list,SuperConstant.NO);
		if (flag) {
			return "success";
		}
		return "fail";
	}
	

	/**
	 * 
	 * @Description: 跳转到新增编辑页面
	 * @param userVo
	 */
	@RequestMapping(value = "/input")
	public ModelAndView input(@ModelAttribute("user") UserVo userVo) {
		if(!EmptyUtil.isNullOrEmpty(userVo.getId())){
			List<String> list = userService.findUserHasRoleIds(userVo.getId());
			StringBuffer roleIdsBuffer = new StringBuffer(100);
			for (int i = 0; i < list.size(); i++) {
				roleIdsBuffer.append(list.get(i));
				if (i+1!=list.size()) {
					roleIdsBuffer.append(",");
				}
			}
			userVo.setHasRoleIds(roleIdsBuffer.toString());
		}
		return new ModelAndView("/user/user-input").addObject("user", userVo);
	}

	@ModelAttribute("user")
	public UserVo getUserById(@RequestParam(value = "id", required = false) String id) {

		if (!EmptyUtil.isNullOrEmpty(id)) {
			return userService.getUserById(id);
		}
		return new UserVo();
	}
	
}
