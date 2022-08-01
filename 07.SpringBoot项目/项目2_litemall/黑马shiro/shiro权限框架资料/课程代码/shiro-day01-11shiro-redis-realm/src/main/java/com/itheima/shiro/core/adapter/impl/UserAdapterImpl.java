package com.itheima.shiro.core.adapter.impl;

import com.itheima.shiro.constant.SuperConstant;
import com.itheima.shiro.core.adapter.UserAdapter;
import com.itheima.shiro.mapper.UserMapper;
import com.itheima.shiro.mappercustom.UserAdapterMapper;
import com.itheima.shiro.pojo.Resource;
import com.itheima.shiro.pojo.Role;
import com.itheima.shiro.pojo.User;
import com.itheima.shiro.pojo.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * @Description 后台登陆用户适配器接口实现
 */
@Component("userAdapter")
public class UserAdapterImpl implements UserAdapter {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserAdapterMapper userAdapterMapper;

	@Override
	public User findUserByLoginName(String loginName) {
		UserExample userExample = new UserExample();
		userExample.createCriteria().andEnableFlagEqualTo(SuperConstant.YES).andLoginNameEqualTo(loginName);
		List<User> userList = userMapper.selectByExample(userExample);
		if (userList.size()==1) {
			return userList.get(0);
		}else {
			return null;
		}
	}

	@Override
	public List<Role> findRoleByUserId(String userId) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("userId", userId);
		values.put("enableFlag", SuperConstant.YES);
		List<Role> list = userAdapterMapper.findRoleByUserId(values);
		return list;
	}

	@Override
	public List<Resource> findResourceByUserId(String userId) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("userId", userId);
		values.put("enableFlag", SuperConstant.YES);
		List<Resource> list=userAdapterMapper.findResourceByUserId(values);
		return list;
	}
}
