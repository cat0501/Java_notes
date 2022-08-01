package com.itheima.shiro.pojo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
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

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}