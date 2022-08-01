package com.itheima.shiro.utils;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @Description 序列化工具
 */
public class ToString implements Serializable {

    private static final long serialVersionUID = 2826274307960457747L;

    public ToString() {
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
