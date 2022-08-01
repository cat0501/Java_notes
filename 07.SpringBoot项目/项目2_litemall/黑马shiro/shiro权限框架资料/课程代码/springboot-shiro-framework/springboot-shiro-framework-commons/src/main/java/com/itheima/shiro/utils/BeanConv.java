package com.itheima.shiro.utils;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import javax.xml.bind.annotation.XmlElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @Description 对象转换工具
 */
public class BeanConv {

    public static <T, X> T toBean(X entity, Class<T> cls) {
        if (EmptyUtil.isNullOrEmpty(entity)) {
            return null;
        }
        T t = null;
        try {
            t = cls.newInstance();
            ConvertUtils.register(new DateConverter(null), Date.class);
            PropertyUtils.copyProperties(t, entity);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static <T, X> List<T> toBeanList(List<X> list, Class<T> cls) {
        if (EmptyUtil.isNullOrEmpty(list)) {
            return new ArrayList<T>();
        }
        List<T> result = new ArrayList<>();
        list.forEach(entity -> {
            result.add(toBean(entity, cls));
        });
        return result;
    }

    /**
     * 实体类非空属性转map（不包括父类属性，支持@XmlElement注解）
     * @param bean 实体类
     */
    public static <T> Map BeanToMap(T bean) {
        Map map = new HashMap();
        Class clazz = bean.getClass();
        Arrays.stream(clazz.getDeclaredFields()).forEach(field -> {
            setMap(field, bean, map);
        });
        return map;
    }

    /**
     * 获取所需字段的值转map（不包括父类属性，支持@XmlElement注解）
     * @param bean 实体类
     * @param fields 获取字段(逗号分割)
     */
    public static <T> Map BeanToMap(T bean, String fields)
    {
        Map map = new HashMap();
        Class clazz = bean.getClass();
        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> fields.indexOf(field.getName())!=-1?true:false)
                .forEach(field -> setMap(field,bean,map));
        return map;
    }

    /**
     * 转换空字符串
     * @param form 对象
     */
    public static void convEmptyString(Object form) {
        if (form == null)
            return;
        try {
            Class<?> cls = form.getClass();
            Field[] fields = cls.getDeclaredFields();
            for (int i = 0, size = fields.length; i < size; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                Object value = field.get(form);
                if ("".equals(value)) {
                    field.set(form, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取字段的值map
     * @param field
     */
    public static <T> void setMap(Field field, T bean, Map map) {
        //访问private限制
        field.setAccessible(true);
        try {
            Object fieldValue = field.get(bean);
            XmlElement xmlElement = field.getAnnotation(XmlElement.class);
            String key ;
            if (!EmptyUtil.isNullOrEmpty(xmlElement)){
                key = xmlElement.name();
            }else {
                key = field.getName();
            }
            if (fieldValue != null) {
                map.put(key, fieldValue);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
