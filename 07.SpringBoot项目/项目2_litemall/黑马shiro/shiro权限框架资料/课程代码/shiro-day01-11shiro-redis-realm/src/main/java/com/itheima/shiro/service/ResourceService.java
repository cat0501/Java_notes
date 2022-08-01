/*
 * <b>文件名</b>：ResourceService.java
 *
 * 文件描述：
 *
 *
 * 2017-3-26  下午10:37:59
 */

package com.itheima.shiro.service;

import com.itheima.shiro.pojo.Resource;
import com.itheima.shiro.vo.TreeVo;

import java.util.List;


/**
 * @Description 资源服务
 */

public interface ResourceService {


    /**
     * @Description 资源多条件查询
     * @param
     * @return
     */
    public List<Resource> findResourceList(Resource resource,
                                           Integer rows, Integer page);

    /**
     * @Description 资源多条件查询统计
     * @param
     * @return
     */
    public long countResourceList(Resource resource);

    /**
     * @Description 主键查询资源
     * @param
     * @return
     */
    public Resource findOne(String id);

    /**
     * @Description 修改或修改资源对象
     * @param
     * @return
     */
    public void saveOrUpdateResource(Resource resource);

    /**
     * @Description 批量删除
     * @param
     * @return
     */
    public void deleteByids(List<String> ids);

    /**
     * @Description 按照标识查询资源
     * @param
     * @return
     */
    public String findByLabel(String label);

    /**
     * @Description 查找所有树按SortNo降序
     * @param
     * @return
     */
    public List<TreeVo> findAllOrderBySortNoAsc();

    /**
     * @Description 查找所有树按SortNo降序,并初始化已选选项
     * @param
     * @return
     */
    public List<TreeVo> findAllOrderBySortNoAscChecked(String resourceIds);


    /**
     * @Description 按父Id查询树
     * @param
     * @return
     */
    public List<TreeVo> findResourceTreeVoByParentId(
            String parentId);

    /**
     * @Description 按资源标识删除资源
     * @param
     * @return
     */
    public int deleteByLabel(String label);



}
