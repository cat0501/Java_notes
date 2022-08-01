package com.itheima.shiro.properties;

import com.itheima.shiro.utils.EmptyUtil;
import lombok.extern.log4j.Log4j2;

/**
 * @Description 读取Properties的工具类
 */
@Log4j2
public class PropertiesUtil {

    public static LinkProperties propertiesShiro = new LinkProperties();

    /**
     * 读取properties配置文件信息
     */
    static {
        String sysName = System.getProperty("sys.name");
        if (EmptyUtil.isNullOrEmpty(sysName)) {
            sysName = "application.properties";
        } else {
            sysName += ".properties";
        }
        try {
            propertiesShiro.load(PropertiesUtil.class.getClassLoader()
                    .getResourceAsStream("authentication.properties"));
        } catch (Exception e) {
            log.warn("资源路径中不存在authentication.properties权限文件，忽略读取！");
        }
    }

    /**
     * 根据key得到value的值
     */
    public static String getShiroValue(String key) {
        return propertiesShiro.getProperty(key);
    }

}
