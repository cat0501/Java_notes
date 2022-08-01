package com.itheima.shiro.vo;

import com.itheima.shiro.utils.ToString;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description：资源Vo
 */
@Getter
@Setter
public class ResourceVo extends ToString {

    /**
     * 主键
     */
    private String id;

    /**
     * 是否有效
     */
    private String enableFlag;

    /**
     * 父Id
     */
    private String parentId;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源路径
     */
    private String requestPath;

    /**
     * 资源标识
     */
    private String label;

    /**
     * 图标
     */
    private String icon;

    /**
     * 是否叶子节点
     */
    private String isLeaf;

    /**
     * 资源类型
     */
    private String resourceType;

    /**
     * 排序
     */
    private Integer sortNo;

    /**
     * 描述
     */
    private String description;

    /**
     * 系统归属
     */
    private String systemCode;

    /**
     * 是否根节点
     */
    private String isSystemRoot;

    /**
     * 服务接口全限定路径
     */
    private String serviceName;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 传入参数全限定类型
     */
    private String methodParam;

    /**
     * DUBBO版本号
     */
    private String dubboVersion;

    /**
     * 接口轮训算法
     */
    private String loadbalance;

    /**
     * 接口超时时间
     */
    private Integer timeout;

    /**
     * 重试次数
     */
    private Integer retries;

    private static final long serialVersionUID = 1L;


}
