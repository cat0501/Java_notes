package com.itheima.shiro.request;

import com.itheima.shiro.base.BaseRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description 分页请求参数基础类
 */
@Getter
@Setter
public class PageRequest extends BaseRequest {

    protected int             page;

    protected int             row;

    /**
     * mysql 分页查询 limit 的起始值
     */
    public int start4Mysql() {
        return page > 1 ? (page - 1) * row : 0;
    }

    /**
     * hbase 分页查询 limit 的起始值
     */
    public int startHbase() {
        return start4Mysql();
    }

}
