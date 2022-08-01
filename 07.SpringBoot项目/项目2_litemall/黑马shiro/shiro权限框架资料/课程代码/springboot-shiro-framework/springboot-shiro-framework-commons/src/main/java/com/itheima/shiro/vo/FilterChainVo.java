package com.itheima.shiro.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterChainVo implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 描述
     */
    private String urlName;

    /**
     * 路径
     */
    private String url;

    /**
     * 拦截器名称
     */
    private String filterName;

    /**
     * 所需角色，可省略，用逗号分隔
     */
    private String roles;

    /**
     * 所需权限，可省略，用逗号分隔
     */
    private String permissions;

    /**
     * 排序
     */
    private Integer sortNo;

    private String enableFlag;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}