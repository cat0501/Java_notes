package com.itheima.shiro.response;

import com.itheima.shiro.base.BaseResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Description 分页多泛型实例返回结果
 */
@Getter
@Setter
public class PageResponse<T> extends BaseResponse {

    /* 页码 */
    protected int             page;
    /* 总页数 */
    protected int             totalPage;
    /* 每页行数 */
    protected int             row;
    /* 查询结果总行数 */
    protected int             totalRows;
    /* 结果集*/
    private List<T>           values;

}
