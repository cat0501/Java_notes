package com.itheima.shiro.service.impl;

import com.itheima.shiro.constant.SuperConstant;
import com.itheima.shiro.mapper.RoleMapper;
import com.itheima.shiro.mapper.RoleResourceMapper;
import com.itheima.shiro.mappercustom.RoleServiceMapper;
import com.itheima.shiro.pojo.Role;
import com.itheima.shiro.pojo.RoleExample;
import com.itheima.shiro.pojo.RoleResource;
import com.itheima.shiro.pojo.RoleResourceExample;
import com.itheima.shiro.service.RoleService;
import com.itheima.shiro.utils.BeanConv;
import com.itheima.shiro.utils.EmptyUtil;
import com.itheima.shiro.utils.ExceptionsUtil;
import com.itheima.shiro.vo.ComboboxVo;
import com.itheima.shiro.vo.RoleVo;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.*;


/**
 * @Description 角色service接口实现类
 */
@Service
@Log4j2
public class RoleServiceImp implements RoleService {
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private RoleResourceMapper roleResourceMapper;
	
	@Autowired
	private RoleServiceMapper roleServiceMapper;
	
	@Override
	public List<Role> findRoleList(RoleVo roleVo, Integer rows, Integer page) {
		RoleExample roleExample = this.roleListExample(roleVo);
		roleExample.setPage(page);
		roleExample.setRow(rows);
		roleExample.setOrderByClause(" SORT_NO ASC");
		return roleMapper.selectByExample(roleExample);
	}
	
	@Override
	public long countRoleList(RoleVo roleVo) {
		RoleExample roleExample = this.roleListExample(roleVo);
		return roleMapper.countByExample(roleExample);
	}
	
	/**
	 * 
	 * <b>方法名：</b>：roleListExample<br>
	 * <b>功能说明：</b>：分页多条件组合<br>
	 * @author <font color='blue'>束文奇</font> 
	 * @date  2017年11月8日 下午4:24:32
	 * @param roleVo
	 * @return
	 */
	private RoleExample roleListExample(RoleVo roleVo) {
		RoleExample roleExample = new RoleExample();
		RoleExample.Criteria criteria = roleExample.createCriteria();
		if (!EmptyUtil.isNullOrEmpty(roleVo.getRoleName())) {
			criteria.andRoleNameLike(roleVo.getRoleName());
		}
		
		if (!EmptyUtil.isNullOrEmpty(roleVo.getLabel())) {
			criteria.andLabelEqualTo(roleVo.getLabel());
		}
		return roleExample;
	}

	
	/**
	 * 
	 * <b>方法名：</b>：roleListExample<br>
	 * <b>功能说明：</b>：分页多条件组合<br>
	 * @author <font color='blue'>束文奇</font> 
	 * @date  2017年11月8日 下午4:24:32
	 * @param roleVo
	 * @return
	 */
	private RoleExample roleListExampleForParttime(RoleVo roleVo) {
		RoleExample roleExample = new RoleExample();
		RoleExample.Criteria criteria = roleExample.createCriteria();
		if (!EmptyUtil.isNullOrEmpty(roleVo.getRoleName())) {
			criteria.andRoleNameLike(roleVo.getRoleName());
		}
		
		if (!EmptyUtil.isNullOrEmpty(roleVo.getLabel())) {
			criteria.andLabelEqualTo(roleVo.getLabel());
		}
		return roleExample;
	}
	
	@Override
	public RoleVo getRoleById(String id) {
		return  BeanConv.toBean(roleMapper.selectByPrimaryKey(id, null), RoleVo.class);
	}
	
	
	@Override
	@Transactional
	public boolean saveOrUpdateRole(RoleVo roleVo) throws IllegalAccessException, InvocationTargetException {
		Role role = new Role();
		BeanUtils.copyProperties(role, roleVo);
		Boolean flag = true;
		try {
			if (EmptyUtil.isNullOrEmpty(roleVo.getId())) {
				role.setEnableFlag(SuperConstant.YES);
				roleMapper.insert(role);
				roleVo.setId(role.getId());
			}else {
				roleMapper.updateByPrimaryKey(role);
				RoleResourceExample roleResourceExample = new RoleResourceExample();
				roleResourceExample.createCriteria().andRoleIdEqualTo(role.getId());
				roleResourceMapper.deleteByExample(roleResourceExample);
			}
			bachRoleResource(roleVo);
		} catch (Exception e) {
			log.error("保存角色出错{}", ExceptionsUtil.getStackTraceAsString(e));
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 
	 * <b>方法名：</b>：bachRoleResource<br>
	 * <b>功能说明：</b>：批量处理RoleResource中间表<br>
	 * @author <font color='blue'>束文奇</font> 
	 * @date  2017-7-11 下午4:01:12
	 */
	private void bachRoleResource(RoleVo roleVo){
		if (!EmptyUtil.isNullOrEmpty(roleVo.getHasResourceIds())) {
			List<RoleResource> list = new ArrayList<>();
			List<String> resourceIdList = Arrays.asList(roleVo.getHasResourceIds().split(","));
			for (String resourceId : resourceIdList) {
				RoleResource roleResource = new RoleResource();
				roleResource.setRoleId(roleVo.getId());
				roleResource.setResourceId(resourceId);
				roleResource.setEnableFlag(SuperConstant.YES);
				list.add(roleResource);
			}
			roleResourceMapper.batchInsert(list);
		}
	}


	@Override
	@Transactional
	public Boolean updateByIds(List<String> list,String enableFlag ) {
		RoleExample roleExample = new RoleExample();
		roleExample.createCriteria().andIdIn(list);
		Role role = new Role();
		role.setEnableFlag(enableFlag);
		int row = roleMapper.updateByExampleSelective(role, roleExample);
		if (row>0) {
			return true;
		}
		return false;
	}


	@Override
	public Role findRoleByLable(String Label) {
		RoleExample roleExample = new RoleExample();
		if (!EmptyUtil.isNullOrEmpty(Label)) {
			roleExample.createCriteria().andLabelEqualTo(Label);
		}
		List<Role> list = roleMapper.selectByExample(roleExample);
		if (list.size()==1) {
			return list.get(0);
		}
		return null;
	}


	@Override
	public List<ComboboxVo> findRoleComboboxVo(String roleIds) {
		RoleExample roleExample = new RoleExample();
		roleExample.createCriteria().andEnableFlagEqualTo(SuperConstant.YES);
		List<Role> roleList = roleMapper.selectByExample(roleExample);
		List<ComboboxVo> list = new ArrayList<>();
		for (Role role : roleList) {
			ComboboxVo comboboxVo = new ComboboxVo();
			comboboxVo.setId(role.getId());
			comboboxVo.setText(role.getRoleName());
			list.add(comboboxVo);
		}
		if (!EmptyUtil.isNullOrEmpty(roleIds)) {
			String[] ids = roleIds.split(",");
			for (String id : ids) {
				for (ComboboxVo comboboxVo : list) {
					if (id.equals(comboboxVo.getId())) {
						comboboxVo.setSelected(true);
						break;
					}
				}
			}
		}
		return list;
		
	}

	@Override
	public List<String> findRoleHasResourceIds(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("enableFlag", SuperConstant.YES);
		map.put("id", id);
		return roleServiceMapper.findRoleHasResourceIds(map);
	}


}
