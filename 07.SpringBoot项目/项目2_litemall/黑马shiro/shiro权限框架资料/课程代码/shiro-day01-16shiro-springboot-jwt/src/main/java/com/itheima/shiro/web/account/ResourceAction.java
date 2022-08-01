package com.itheima.shiro.web.account;

import com.itheima.shiro.constant.SuperConstant;
import com.itheima.shiro.pojo.Resource;
import com.itheima.shiro.service.ResourceService;
import com.itheima.shiro.utils.EmptyUtil;
import com.itheima.shiro.utils.ExceptionsUtil;
import com.itheima.shiro.vo.TreeVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 资源管理
 */
@Controller
@RequestMapping(value = "/resource")
@Log4j2
public class ResourceAction {


    @Autowired
    private ResourceService resourceService;

    @InitBinder
    protected void initBinder(ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));// 去掉空格
    }

    /**
     * @description: 		初始化列表                                                                                                                
     */
    @RequestMapping(value = "/listInitialize")
    public String listInitialize() {
        return "/resource/resource-listInitialize";
    }

    /**
     * @description:		分页列表                                                                                                                 
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public ModelMap list(Resource resource, Integer rows, Integer page) {
        List<Resource> dateList = resourceService.findResourceList(resource, rows, page);
        Long total = resourceService.countResourceList(resource);
        ModelMap modelMap = new ModelMap();
        modelMap.put("rows", dateList);//数据表格数据传递
        modelMap.put("total", total);//统计条数传递
        return modelMap;
    }

    /**
     * @description:		新增页面                                                                                                                 
     */
    @RequestMapping(value = "/input")
    public ModelAndView input(Resource resource) {
        ModelAndView modelAndView = new ModelAndView("/resource/resource-input");
        Resource parentResource = null;
        if (resource.getId() == null) {
            parentResource = resourceService.findOne(resource.getParentId());
            if (!parentResource.getParentId().equals(SuperConstant.ROOT_PARENT_ID))
                resource.setSystemCode(parentResource.getSystemCode());
        } else {
            resource = resourceService.findOne(resource.getId());
            parentResource = resourceService.findOne(resource.getParentId());
        }
        modelAndView.addObject("parentId", resource.getParentId());
        modelAndView.addObject("parentName", parentResource.getResourceName());
        modelAndView.addObject("resource", resource);

        return modelAndView;
    }

    /**
     * @description:		新增修改                                                                                                                 
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public boolean save(@ModelAttribute("resource") Resource resource) {
        boolean flag = true;
        try {
            resourceService.saveOrUpdateResource(resource);
        } catch (Exception e) {
            log.error("保存资源出错{}", ExceptionsUtil.getStackTraceAsString(e));
            flag = false;
        }
        return flag;
    }

    /**
     * @description:		删除资源
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(@RequestParam("ids") String ids) throws SecurityException,
                                                         NoSuchFieldException {
        String[] idsStrings = ids.split(",");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < idsStrings.length; i++) {
            list.add(idsStrings[i]);
        }
        resourceService.deleteByids(list);
        return "success";
    }

    /**
     * @description: 		资源标识是否重复                                                                                                                
     */
    @RequestMapping(value = "/checkLabel")
    @ResponseBody
    public String checkLabel(@RequestParam("label") String label,
                             @RequestParam("oldLabel") String oldLabel) {
        if (label.equals(oldLabel)) {
            return "pass";
        } else if (EmptyUtil.isNullOrEmpty(resourceService.findByLabel(label))) {
            return "pass";
        }
        return "noPass";
    }

    /**
     * @description: 		树形页面                                                                                                                 
     */
    @RequestMapping(value = "/listTree")
    public ModelAndView listTree() {
        return new ModelAndView("/resource/resource-listTree");
    }

    /**
     * @description: 		树形json                                                                                                                 
     */
    @RequestMapping(value = "/tree")
    @ResponseBody
    public List<TreeVo> tree(String parentId) {
        List<TreeVo> list = resourceService.findResourceTreeVoByParentId(parentId);
        return list;
    }

    /**
     * @description: 		角色分配资源树形json                                                                                                                 
     */
    @RequestMapping(value = "/roleResourceTree")
    @ResponseBody
    public List<TreeVo> roleResourceTree(@RequestParam(value = "hasResourceIds") String hasResourceIds) {
        List<TreeVo> list = null;
        if (EmptyUtil.isNullOrEmpty(hasResourceIds)) {
            list = resourceService.findAllOrderBySortNoAsc();
        } else {
            list = resourceService.findAllOrderBySortNoAscChecked(hasResourceIds);
        }
        return list;
    }


    @ModelAttribute("resource")
    public Resource getResourcesById(@RequestParam(value = "id", required = false) String id) {
        if (!EmptyUtil.isNullOrEmpty(id)) {
            return resourceService.findOne(id);
        }
        return new Resource();
    }
}
