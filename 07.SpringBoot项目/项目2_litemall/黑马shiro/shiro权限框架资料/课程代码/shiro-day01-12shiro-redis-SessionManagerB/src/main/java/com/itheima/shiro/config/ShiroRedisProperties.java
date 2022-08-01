package com.itheima.shiro.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @Description  redis配置文件
 */
@Data
@ConfigurationProperties(prefix = "itheima.framework.shiro.redis")
public class ShiroRedisProperties implements Serializable {

	/**
	 * redis连接地址
	 */
	private String nodes ;

	/**
	 * 获取连接超时时间
	 */
	private int connectTimeout ;

	/**
	 * 最小空闲连接数
	 */
	private int connectPoolSize;

	/**
	 * 最大连接数
	 */
	private int connectionMinimumidleSize ;

	/**
	 * 等待数据返回超时时间
	 */
	private int timeout ;

	/**
	 *  全局超时时间
	 */
	private long globalSessionTimeout;

}
