
package com.itheima.shiro.service;

import com.itheima.shiro.pojo.User;
import com.itheima.shiro.vo.UserVo;

import java.lang.reflect.InvocationTargetException;
import java.util.List;


/**
 * @Description 用户服务层
 */

public interface UserService {


	/**
	 * @Description 分页查询
	 * @param
	 * @return
	 */
	public List<User> findUserList(UserVo userVo, Integer rows, Integer page);

	/**
	 * @Description count分页查询
	 * @param
	 * @return
	 */
	public long countUserList(UserVo userVo);

	/**
	 * @Description 根据id查询用户
	 * @param
	 * @return
	 */
	public UserVo getUserById(String id);

	/**
	 * @Description 新增,修改对象
	 * @param
	 * @return
	 */
	public Boolean saveOrUpdateUser(UserVo userVo) throws IllegalAccessException, InvocationTargetException;


	/**
	 * @Description 验证用户是否存在
	 * @param
	 * @return
	 */
	public Boolean getUserByLoginNameOrMobilOrEmail(String loginName);

	/**
	 * @Description 通过登录名手机号电子邮箱地址获取user
	 * @param
	 * @return
	 */
	public User getUserIdByLoginNameOrMobilOrEmail(String loginName);

	/**
	 * @Description 伪删除用户
	 * @param
	 * @return
	 */
	public Boolean updateByIds(List<String> list, String enableFlag);

	/**
	 * @Description 密码加密
	 * @param
	 * @return
	 */
	public void entryptPassword(UserVo userVo);

	/**
	 * @Description 用户拥有的角色
	 * @param
	 * @return
	 */
	public List<String> findUserHasRoleIds(String id);

	/**
	 * @Description 修改密码
	 * @param
	 * @return
	 */
	public Boolean saveNewPassword(String userId,String oldPassword, String plainPassword) throws IllegalAccessException, InvocationTargetException;

}
