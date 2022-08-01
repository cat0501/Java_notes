package com.itheima.shiro.web.account;

import com.itheima.shiro.constant.SuperConstant;
import com.itheima.shiro.pojo.FilterChain;
import com.itheima.shiro.service.FilterChainService;
import com.itheima.shiro.utils.EmptyUtil;
import com.itheima.shiro.utils.ShiroUserUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
 * @Description 拦截器链管理
 */
@Controller
@RequestMapping(value= "/filterChain")
public class FilterChainAction {

    @Autowired
    private FilterChainService filterChainService;


    /**
     *@Description: 跳转到拦截器链的初始化页面
     */
    @RequestMapping(value = "listInitialize")
    public ModelAndView listInitialize(){
        return  new ModelAndView("/filterChain/filterChain-listInitialize");
    }


    /**
     *@Description:拦截器链的分页查询
     *@param filterChain 拦截器链对象
     *@param rows 分页个数
     *@param page 分页对象
     *@return  map map对象
     */
    @RequestMapping(value = "list")
    @ResponseBody
    public ModelMap list(FilterChain filterChain, Integer rows, Integer page) {
        Subject subject = SecurityUtils.getSubject();
        String shiroSessionId = ShiroUserUtil.getShiroSessionId();
        List<FilterChain> dataList = filterChainService.findFilterChainList(filterChain, rows, page);
        Long total = filterChainService.countFilterChainList(filterChain);
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
    public ModelAndView input(@ModelAttribute("filterChain") FilterChain filterChain) {
        return  new ModelAndView("/filterChain/filterChain-input").addObject("filterChain",filterChain);
    }

    /**
     *@Description: 拦截器链保存
     *@param filterChain 拦截器链对象
     *@return  true：保存成功，false:保存失败
     */
    @RequestMapping(value = "save")
    @ResponseBody
    public boolean save(@ModelAttribute("filterChain")FilterChain filterChain) throws IllegalAccessException, InvocationTargetException {
        return filterChainService.saveOrUpdateFilterChain(filterChain);
    }


    /**
     *@Description: 启用
     *@param ids 拦截器链id拼装的字符串
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
        Boolean flag = filterChainService.updateByIds(list, SuperConstant.YES);
        if (flag) {
            return "success";
        }
        return "fail";
    }

    /**
     *@Description: 禁用
     *@param ids 拦截器链id拼装的字符串
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
        Boolean flag = filterChainService.updateByIds(list, SuperConstant.NO);
        if (flag) {
            return "success";
        }
        return "fail";
    }


    /**
     *
     *@Description: 调用该控制器所有方法之前会调用该方法
     *@param id 主键
     */
    @ModelAttribute("filterChain")
    public FilterChain getFilterChainById(@RequestParam(value = "id", required = false) String id) {
        if (!EmptyUtil.isNullOrEmpty(id)) {
            return filterChainService.getFilterChainById(id);
        }
        return new FilterChain();
    }
}
