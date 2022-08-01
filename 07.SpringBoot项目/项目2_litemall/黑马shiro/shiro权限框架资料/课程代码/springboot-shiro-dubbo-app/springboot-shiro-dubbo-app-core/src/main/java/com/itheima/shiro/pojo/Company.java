package com.itheima.shiro.pojo;

import java.io.Serializable;
import java.util.Date;
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
public class Company implements Serializable {
    private String id;

    /** 
    * 是否有效
    */
    private String enableFlag;

    /** 
    * 企业名称
    */
    private String companyName;

    /** 
    * 公司地址
    */
    private String address;

    /** 
    * 企业编码
    */
    private String companyNo;

    /** 
    * 法人
    */
    private String boss;

    /** 
    * 注册资金
    */
    private String registeredFund;

    /** 
    * 注册时间
    */
    private Date registeredTime;

    /** 
    * 在保人数
    */
    private Integer insuranceNumber;

    /** 
    * 状态 0：正常 1：拉黑
    */
    private String state;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}