/*
 * <b>文件名</b>：MenusService.java
 *
 * 文件描述：
 *
 *
 * 2017-4-6  下午10:23:08
 */

package com.itheima.shiro.service;


import com.itheima.shiro.pojo.Resource;
import com.itheima.shiro.vo.MenuVo;

import java.util.List;
import java.util.Map;


/**
 * @Description 菜单服务
 */

public interface MenusService {

    /**
     * @Description 查询子菜单
     * @param
     * @return
     */
    List<MenuVo> findByResourceType(String parentId,List<String> ResourceIds);

    /**
     * @Description 时间滚动
     * @param
     * @return
     */
    Map<String, String> rollingTime();

    /**
     * @Description 查询每个系统的顶级菜单
     * @param
     * @return
     */
    List<Resource> findTopLevel(List<String> ResourceIds);


}
