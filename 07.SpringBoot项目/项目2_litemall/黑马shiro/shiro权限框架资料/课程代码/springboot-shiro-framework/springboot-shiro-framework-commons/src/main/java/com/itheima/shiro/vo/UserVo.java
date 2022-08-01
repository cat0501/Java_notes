/*
 * <b>文件名</b>：UserVo.java
 *
 * 文件描述：
 *
 *
 * 2017年11月9日  上午10:13:25
 */

package com.itheima.shiro.vo;

import com.itheima.shiro.utils.ToString;
import lombok.*;

import java.io.Serializable;

/**
 * @Description
 */
@Getter
@Setter
public class UserVo extends ToString {

    /**
     * 主键
     */
    private String id;

    /**
     * 登录名称
     */
    private String loginName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 密码
     */
    private String passWord;

    /**
     * 加密因子
     */
    private String salt;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 邮箱
     */
    private String zipcode;

    /**
     * 地址
     */
    private String address;

    /**
     * 固定电话
     */
    private String tel;

    /**
     * 电话
     */
    private String mobil;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 职务
     */
    private String duties;

    /**
     * 排序
     */
    private Integer sortNo;

    /**
     * 是否有效
     */
    private String enableFlag;

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3745165122177843152L;

    /**
     * 当前拥有的角色Ids
     **/
    private String hasRoleIds;

    /**
     * 零时密码
     **/
    private String plainPassword;


}
