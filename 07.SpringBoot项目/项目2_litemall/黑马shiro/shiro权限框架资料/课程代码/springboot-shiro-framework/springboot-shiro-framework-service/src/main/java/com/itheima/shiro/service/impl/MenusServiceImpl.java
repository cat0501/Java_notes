package com.itheima.shiro.service.impl;

import com.itheima.shiro.constant.ResourceConstant;
import com.itheima.shiro.constant.SuperConstant;
import com.itheima.shiro.mappercustom.MenusServiceMapper;
import com.itheima.shiro.pojo.Resource;
import com.itheima.shiro.service.MenusService;
import com.itheima.shiro.utils.CnCalendarUtil;
import com.itheima.shiro.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description 菜单服务器层
 */
@Service("menusService")
public class MenusServiceImpl implements MenusService {

    @Value("${itheima.resource.systemcode}")
    private String systemCode;

    @Autowired
    private MenusServiceMapper menusServiceMapper;

    @Override
    public List<Resource> findTopLevel(List<String> ResourceIds) {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("resourceType", ResourceConstant.MENU);
        values.put("resourceIdList", ResourceIds);
        values.put("isSystemRoot", SuperConstant.YES);
        values.put("enableFlag", SuperConstant.YES);
        values.put("systemCode", systemCode);
        return menusServiceMapper.findTopLevel(values);
    }


    @Override
    public List<MenuVo> findByResourceType(String parentId,List<String> ResourceIds) {
        //查询二级菜单
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("parentId", parentId);
        values.put("resourceType", ResourceConstant.MENU);
        values.put("resourceIdList", ResourceIds);
        values.put("systemCode", systemCode);
        values.put("enableFlag", SuperConstant.YES);
        List<MenuVo> list = menusServiceMapper.findByResourceType(values);
        for (int i = 0; i < list.size(); i++) {
            values.put("parentId", list.get(i).getMenuid());
            List<MenuVo> iterableChildren = menusServiceMapper.findByResourceType(values);
            list.get(i).setMenus(iterableChildren);
        }
        return list;
    }

    @Override
    public Map<String, String> rollingTime() {
        Map<String, String> map = new HashMap<String, String>();
        String today = null;
        String lunar = null;
        String hourMinute = null;
        CnCalendarUtil cnCalendar = new CnCalendarUtil();
        lunar = cnCalendar.getNongli(new Date());
        String[] week = new String[]{"", "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        int weekNum = calendar.get(Calendar.DAY_OF_WEEK);
        today = "今天是: " + sdf.format(calendar.getTime()) + "（农历：" + lunar + "） " + week[weekNum];
        sdf = new SimpleDateFormat("HH:mm:ss");
        hourMinute = sdf.format(calendar.getTime());
        map.put("today", today);
        map.put("hourMinute", hourMinute);
        return map;
    }

}
