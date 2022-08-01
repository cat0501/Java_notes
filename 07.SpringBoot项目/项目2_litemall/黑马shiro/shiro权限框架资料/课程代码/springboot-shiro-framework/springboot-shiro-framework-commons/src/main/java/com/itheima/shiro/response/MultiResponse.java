package com.itheima.shiro.response;

import com.itheima.shiro.base.BaseResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Description 多泛型实例返回结果
 */
@Getter
@Setter
public class MultiResponse<T> extends BaseResponse {

    private List<T> values;

}
