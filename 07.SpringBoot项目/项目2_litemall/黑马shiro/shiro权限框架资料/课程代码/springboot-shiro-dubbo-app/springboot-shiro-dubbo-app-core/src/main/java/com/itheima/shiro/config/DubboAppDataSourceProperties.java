package com.itheima.shiro.config;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @Description  Xa数据源配置
 */
@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "itheima.datasource.dubbo")
public class DubboAppDataSourceProperties {

	//主键工作点ID
	private Integer workId;

	//主键名，不指定默认名称：id
	private String primaryKey ;

	//数据库实例名称
	private String dataSourceName ;

	//XA底层数据源名称
	public String xaDataSourceClassName;

	//数据库驱动名称
	public String driverClassName ;

	//数据库连接
	public String url ;

	//数据库密码
	public String password ;

	//数据库名称
	public String username ;

	public String initialSize;

	// 连接池最大连接数量
	public Integer maxActive ;

	// 连接池最小连接数量
	public Integer minIdle ;

	// 连接池中获取连接等待超时时间
	public Integer maxWait;

	// 最大存活时间
	public Integer maxLifeTime;

	public String validationQuery ;

	public String testOnBorrow ;

	public String testOnReturn;

	public String testWhileIdle;

	// 是否启用租期
	public String removeAbandoned;
	// 租期最大超时时间(规定时间内connection为close则自动收回)
	public String removeAbandonedTimeout;

	public String logAbandoned;

	public String filters;

	//实体类扫描位置
	private String typeAliasesPackage ;

	//XML文件扫描位置
	private String mapperLocations ;


}
