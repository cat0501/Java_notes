package com.itheima.shiro.response;

import com.itheima.shiro.base.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description 单个泛型实例返回结果
 */
@Getter
@Setter
public class SingleResponse<T> extends BaseResponse {

    private static final long serialVersionUID = 4648290766510705991L;

    private T                 value;
}
