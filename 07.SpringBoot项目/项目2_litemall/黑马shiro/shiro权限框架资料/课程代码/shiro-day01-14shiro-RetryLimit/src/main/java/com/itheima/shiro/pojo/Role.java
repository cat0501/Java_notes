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
public class Role implements Serializable {
    /** 
    * 主键
    */
    private String id;

    /** 
    * 角色名称
    */
    private String roleName;

    /** 
    * 角色标识
    */
    private String label;

    /** 
    * 角色描述
    */
    private String description;

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