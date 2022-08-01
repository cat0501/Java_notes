package com.itheima.shiro.web.account;

import com.itheima.shiro.constant.SuperConstant;
import com.itheima.shiro.pojo.Role;
import com.itheima.shiro.service.RoleService;
import com.itheima.shiro.utils.EmptyUtil;
import com.itheima.shiro.vo.ComboboxVo;
import com.itheima.shiro.vo.RoleVo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
 * @Description 角色管理
 */
@Controller
@RequestMapping(value= "/role")
public class RoleAction {
	
	@Autowired
	private RoleService roleService;
	
	
	/**
	 *@Description: 跳转到角色的初始化页面
	 */
	@RequestMapping(value = "listInitialize")
	@RequiresRoles(value ={"SuperAdmin","MangerRole"},logical = Logical.OR)
	public ModelAndView listInitialize(){
		return  new ModelAndView("/role/role-listInitialize");
	}

	
	/**
	 *@Description:角色的分页查询
	 *@param roleVo 角色对象
	 *@param rows 分页个数
	 *@param page 分页对象
	 *@return  map map对象
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public ModelMap list(RoleVo roleVo, Integer rows, Integer page) {
		List<Role> dataList = roleService.findRoleList(roleVo, rows, page);
        Long total = roleService.countRoleList(roleVo);
        ModelMap modelMap = new ModelMap();
        modelMap.put("rows", dataList);
        modelMap.put("total", total);
		return modelMap;
	}
	
	/**
	 *@Description: 跳转到添加和编辑页面
	 *@return  ModelAndView
	 */
	@RequestMapping(value = "/input")
	public ModelAndView input(@ModelAttribute("role") RoleVo roleVo) {
		if (!EmptyUtil.isNullOrEmpty(roleVo.getId())) {
			List<String> resourceIdList = roleService.findRoleHasResourceIds(roleVo.getId());
			StringBuffer resourceIdsBuffer = new StringBuffer(100);
			for (int i = 0; i < resourceIdList.size(); i++) {
				resourceIdsBuffer.append(resourceIdList.get(i));
				if (i+1!=resourceIdList.size()) {
					resourceIdsBuffer.append(",");
				}
			}
			roleVo.setHasResourceIds(resourceIdsBuffer.toString());
		}
		return  new ModelAndView("/role/role-input").addObject("role",roleVo);
	}
	
	/**
	 *@Description: 角色保存
	 *@param roleVo 角色对象
	 *@return  true：保存成功，false:保存失败
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public boolean save(@ModelAttribute("role")RoleVo roleVo) throws IllegalAccessException, InvocationTargetException {
		return roleService.saveOrUpdateRole(roleVo);
	}
	
	
	/**
	 *@Description: 启用
	 *@param ids 角色id拼装的字符串
	 *@return  success:保存成功
	 */
	@RequestMapping(value = "start")
	@ResponseBody
	public String start(String ids){
		
		String[] idArray = ids.split(",");
		List<String> list=new ArrayList<String>();
		for(int i=0;i<idArray.length;i++){
			list.add(idArray[i]);
		}
		Boolean flag = roleService.updateByIds(list, SuperConstant.YES);
		if (flag) {
			return "success";
		}
		return "fail";
	}
	
	/**
	 *@Description: 禁用
	 *@param ids 角色id拼装的字符串
	 *@return  success:保存成功
	 */
	@RequestMapping(value = "stop")
	@ResponseBody
	public String stop(String ids){
		
		String[] idArray = ids.split(",");
		List<String> list=new ArrayList<String>();
		for(int i=0;i<idArray.length;i++){
			list.add(idArray[i]);
		}
		Boolean flag = roleService.updateByIds(list, SuperConstant.NO);
		if (flag) {
			return "success";
		}
		return "fail";
	}
	
	/**
	 *@Description: 验证角色标志是否重复
	 *@param label 当前输入框的值
	 *@param oldLabel 原始值
	 *@return  pass:不重复，noPass：重复
	 *@throws
	 */
	@RequestMapping(value = "/checkLabel")
	@ResponseBody
	public String checkLabel(@RequestParam("label") String label,@RequestParam("oldLabel") String oldLabel) {
		if (label.equals(oldLabel)) {
			return "pass";
		} else if (roleService.findRoleByLable(label) == null) {
			return "pass";
		}
		return "fail";
	}
	
		
	/**
	 * @Description: 角色下拉框
	 * @param roleIds	已选角色ids
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/findRoleComboboxVo")
	@ResponseBody
	public List<ComboboxVo> findRoleComboboxVo(@RequestParam("roleIds") String roleIds){
		return roleService.findRoleComboboxVo(roleIds);
	}	
	
	/**
	 * 
	 *@Description: 调用该控制器所有方法之前会调用该方法
	 *@param id 主键
	 */
	@ModelAttribute("role")
	public RoleVo getRoleById(@RequestParam(value = "id", required = false) String id) {
		if (!EmptyUtil.isNullOrEmpty(id)) {
			return roleService.getRoleById(id);
		}
		return new RoleVo();
	}

}
