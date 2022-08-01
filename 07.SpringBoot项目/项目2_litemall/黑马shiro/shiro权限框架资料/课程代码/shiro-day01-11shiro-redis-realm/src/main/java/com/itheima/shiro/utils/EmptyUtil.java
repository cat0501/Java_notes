package com.itheima.shiro.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Description 判断对象是否为空的工具类
 */
public abstract class EmptyUtil {

	/**
	 * 
	 * @方法名：对于String类型的非空判断
	 * @功能说明：对于String类型的非空判断
	 * @author 束文奇
	 * @date  Mar 28, 2013 9:26:09 AM
	 * @param str
	 * @return boolean true-为空，false-不为空
	 */
	public static boolean isNullOrEmpty(String str) {
		if (str == null || "".equals(str.trim()) || "null".equalsIgnoreCase(str.trim()) || "undefined".equalsIgnoreCase(str.trim())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @方法名：对于StringBuffer类型的非空判断
	 * @功能说明：对于StringBuffer类型的非空判断
	 * @author 束文奇
	 * @date  Mar 28, 2013 9:26:09 AM
	 * @param str
	 * @return boolean true-为空，false-不为空
	 */
	public static boolean isNullOrEmpty(StringBuffer str) {
		return (str == null || str.length() == 0);
	}

	/**
	 * 
	 * @方法名：对于string数组类型的非空判断
	 * @功能说明：对于string数组类型的非空判断
	 * @author 束文奇
	 * @date  Mar 28, 2013 9:26:09 AM
	 * @param str
	 * @return boolean true-为空，false-不为空
	 */
	public static boolean isNullOrEmpty(String[] str) {
		if (str == null || str.length == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @方法名：对于Object类型的非空判断
	 * @功能说明：对于Object类型的非空判断
	 * @author 束文奇
	 * @date  Mar 28, 2013 9:26:09 AM
	 * @param obj
	 * @return boolean true-为空，false-不为空
	 */
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null || "".equals(obj)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @方法名：对于Object数组类型的非空判断
	 * @功能说明：对于Object数组类型的非空判断
	 * @author 束文奇
	 * @date  Mar 28, 2013 9:26:09 AM
	 * @param obj
	 * @return boolean true-为空，false-不为空
	 */
	public static boolean isNullOrEmpty(Object[] obj) {
		if (obj == null || obj.length == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @方法名：对于Collection类型的非空判断
	 * @功能说明：对于Collection类型的非空判断
	 * @author 束文奇
	 * @date  Mar 28, 2013 9:26:09 AM
	 * @param str
	 * @return boolean true-为空，false-不为空
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNullOrEmpty(Collection collection) {
		if (collection == null || collection.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @方法名：对于Map类型的非空判断
	 * @功能说明：对于Map类型的非空判断
	 * @author 束文奇
	 *@param map
	 *@return  传人参数
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNullOrEmpty( Map map) {
		if (map == null || map.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @方法名：removeNullUnit
	 * @功能说明： 删除集合中的空元素
	 * @author 束文奇
	 * @date  2014-6-16 上午8:50:14
	 * @param xllxList
	 * @return
	 */
	public static <T> List<T> removeNullUnit(List<T> xllxList) {
		List<T> need = new ArrayList<T>();
		for (int i = 0; i < xllxList.size(); i++) {
			if (!isNullOrEmpty(xllxList.get(i))) {
				need.add(xllxList.get(i));
			}
		}
		return need;
	}

}
