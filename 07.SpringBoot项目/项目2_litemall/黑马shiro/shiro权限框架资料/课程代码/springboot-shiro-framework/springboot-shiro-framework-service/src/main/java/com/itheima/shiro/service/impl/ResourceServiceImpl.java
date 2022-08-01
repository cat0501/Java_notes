/*
 * <b>文件名</b>：ResourceServiceImpl.java
 *
 * 文件描述：
 *
 *
 * 2017-3-26  下午10:39:03
 */

package com.itheima.shiro.service.impl;

import com.itheima.shiro.constant.ResourceConstant;
import com.itheima.shiro.constant.SuperConstant;
import com.itheima.shiro.mapper.ResourceMapper;
import com.itheima.shiro.mapper.RoleResourceMapper;
import com.itheima.shiro.mapper.UserRoleMapper;
import com.itheima.shiro.mappercustom.ResourceServiceMapper;
import com.itheima.shiro.pojo.*;
import com.itheima.shiro.service.ResourceService;
import com.itheima.shiro.utils.EmptyUtil;
import com.itheima.shiro.vo.ResourceVo;
import com.itheima.shiro.vo.TreeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * @Description 资源服务类
 */
@Service
public class ResourceServiceImpl implements ResourceService {
	
	@Autowired
	ResourceMapper resourceMapper;
	
	@Autowired
	ResourceServiceMapper resourceServiceMapper;
	
	@Autowired
	UserRoleMapper userRoleMapper;
	
	@Autowired
	RoleResourceMapper roleResourceMapper;

	@Override
	public List<Resource> findValidResourceVoAll(String systemCode) {

		ResourceExample resourceExample = new  ResourceExample();
		resourceExample.createCriteria().andEnableFlagEqualTo(SuperConstant.YES)
				.andResourceTypeEqualTo(ResourceConstant.DUBBO)
				.andSystemCodeEqualTo(systemCode);
		return resourceMapper.selectByExample(resourceExample);
	}

	@Override
	public List<Resource> findResourceList(Resource resource, Integer rows, Integer page) {
		ResourceExample resourceExample = this.resourceListExample(resource);
		resourceExample.setPage(page);
		resourceExample.setRow(rows);
		return resourceMapper.selectByExample(resourceExample);
	}
	
	@Override
	public long countResourceList(Resource resource) {
		ResourceExample resourceExample = this.resourceListExample(resource);
		return resourceMapper.countByExample(resourceExample);
	}
	
	/**
	 * 
	 * <b>方法名：</b>：resourceListExample<br>
	 * <b>功能说明：</b>：组装多查询条件<br>
	 * @author <font color='blue'>束文奇</font> 
	 * @date  2017年11月8日 下午3:53:25
	 * @param resource
	 * @return
	 */
	private ResourceExample resourceListExample(Resource resource) {
		ResourceExample resourceExample = new ResourceExample();
		ResourceExample.Criteria criteria = resourceExample.createCriteria();
		if (!EmptyUtil.isNullOrEmpty(resource.getParentId())) {
			criteria.andParentIdEqualTo(resource.getParentId());
		}
		if (!EmptyUtil.isNullOrEmpty(resource.getResourceName())) {
			criteria.andResourceNameLike(resource.getResourceName());
		}
		if (!EmptyUtil.isNullOrEmpty(resource.getLabel())) {
			criteria.andLabelLike(resource.getLabel());
		}
		if (!EmptyUtil.isNullOrEmpty(resource.getRequestPath())) {
			criteria.andRequestPathLike(resource.getRequestPath());
		}
		criteria.andEnableFlagEqualTo(SuperConstant.YES).andParentIdNotEqualTo("-1");
		resourceExample.setOrderByClause("parent_id asc,sort_no asc");
		return resourceExample;
	}


	@Override
	public Resource findOne(String id) {
		ResourceExample resourceExample = new ResourceExample();
		resourceExample.createCriteria().andIdEqualTo(id).andEnableFlagEqualTo(SuperConstant.YES);
		List<Resource> list = resourceMapper.selectByExample(resourceExample);
		if (list.size()==1) {
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public void saveOrUpdateResource(Resource resource) {
		if (!EmptyUtil.isNullOrEmpty(resource.getId())) {
			resourceMapper.updateByPrimaryKey(resource);
		}else {
			if (EmptyUtil.isNullOrEmpty(resource.getIsSystemRoot())) {
				resource.setIsSystemRoot(SuperConstant.NO);
			}
			resource.setEnableFlag(SuperConstant.YES);
			resourceMapper.insert(resource);
		}
	}

	@Transactional
	@Override
	public void deleteByids(List<String> ids) {
		ResourceExample resourceExample = new ResourceExample();
		resourceExample.createCriteria().andIdIn(ids);
		Resource resource = new Resource();
		resource.setEnableFlag(SuperConstant.NO);
		resourceMapper.updateByExampleSelective(resource, resourceExample);
	}
	
	@Transactional
	@Override
	public int deleteByLabel(String label) {
		ResourceExample resourceExample = new ResourceExample();
		resourceExample.createCriteria().andEnableFlagEqualTo(SuperConstant.YES).andLabelEqualTo(label);
		return resourceMapper.deleteByExample(resourceExample);
	}

	@Override
	public String findByLabel(String label) {
		ResourceExample resourceExample = new ResourceExample();
		resourceExample.createCriteria().andEnableFlagEqualTo(SuperConstant.YES).andLabelEqualTo(label);
		resourceExample.setResultColumn("label");
		List<Resource> list = resourceMapper.selectByExample(resourceExample);
		if (list.size()==1) {
			return list.get(0).getLabel();
		}
		return null;
	}

	@Override
	public List<TreeVo> findAllOrderBySortNoAsc() {
		ResourceExample resourceExample = new ResourceExample();
		resourceExample.createCriteria().andEnableFlagEqualTo(SuperConstant.YES);
		resourceExample.setResultColumn("id,parent_id,resource_name");
		resourceExample.setOrderByClause("sort_no asc");
		List<Resource> list  = resourceMapper.selectByExample(resourceExample);
		List<TreeVo> listHandle = new ArrayList<>();
		for (Resource resource : list) {
			TreeVo treeVo = new TreeVo();
			treeVo.setId(resource.getId());
			treeVo.setpId(resource.getParentId());
			treeVo.setName(resource.getResourceName());
			treeVo.setOpen(Boolean.TRUE);
			listHandle.add(treeVo);
		}
		return listHandle;
	}

	@Override
	public List<TreeVo> findAllOrderBySortNoAscChecked(String resourceIds) {
		List<TreeVo> treeVoIterable = this.findAllOrderBySortNoAsc();
		String[] resourceId =  resourceIds.split(",");
		for (String id : resourceId) {
			for (TreeVo treeVo : treeVoIterable) {
				treeVo.setOpen(Boolean.TRUE);
				if (treeVo.getId().equals(id)) {
					treeVo.setChecked(Boolean.TRUE);
				}
			}
		}
		return treeVoIterable;
	}
	
	@Override
	public List<TreeVo> findResourceTreeVoByParentId(String parentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Resource> list = new ArrayList<>();
		if(SuperConstant.ROOT_PARENT_ID.equals(parentId)){
			map.put("isSystemRoot", SuperConstant.YES);
			map.put("enableFlag", SuperConstant.YES);
			list.addAll(resourceServiceMapper.findResourceTreeVoByParentId(map));
		}else {
			ResourceExample resourceExample = new ResourceExample();
			resourceExample.createCriteria().andEnableFlagEqualTo(SuperConstant.YES).andParentIdEqualTo(parentId);
			resourceExample.setOrderByClause("sort_no asc ");
			list.addAll(resourceMapper.selectByExample(resourceExample));
		}
		List<TreeVo> treeVoList = new ArrayList<TreeVo>();
		for (Resource resources : list) {
			TreeVo treeVo = new TreeVo(resources.getId(), resources.getParentId(), resources.getResourceName());
			if(SuperConstant.ROOT_PARENT_ID.equals(parentId)){
				treeVo.setOpen(Boolean.TRUE);
			}
			treeVoList.add(treeVo);
		}
		return treeVoList;
	}

	
	private List<String> findLenderHasRoleIds(String id) {
		UserRoleExample userRoleExample = new UserRoleExample();
		userRoleExample.createCriteria().andUserIdEqualTo(id).andEnableFlagEqualTo(SuperConstant.YES);
		List<UserRole> userRoleList = userRoleMapper.selectByExample(userRoleExample);
		List<String> list = new ArrayList<>();
		userRoleList.forEach(n->list.add(n.getRoleId()));
		return list;
	}
	
	public List<String> findRoleHasResourceIds(List<String> roleIds) {
		RoleResourceExample roleResourceExample = new RoleResourceExample();
		roleResourceExample.createCriteria().andRoleIdIn(roleIds).andEnableFlagEqualTo(SuperConstant.YES);
		List<RoleResource> roleResourceList = roleResourceMapper.selectByExample(roleResourceExample);
		List<String> list = new ArrayList<>();
		roleResourceList.forEach(n->list.add(n.getResourceId()));
		return list;
	}

}
