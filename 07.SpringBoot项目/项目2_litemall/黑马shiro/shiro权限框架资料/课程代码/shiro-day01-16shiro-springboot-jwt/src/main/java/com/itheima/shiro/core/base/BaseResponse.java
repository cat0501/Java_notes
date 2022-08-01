package com.itheima.shiro.core.base;

import com.itheima.shiro.utils.ToString;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Description 基础返回封装
 */
@Data
public class BaseResponse extends ToString {

    private Integer code ;

    private String msg ;

    private String date;

    private static final long serialVersionUID = -1;

    public BaseResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResponse(Integer code, String msg, String date) {
        this.code = code;
        this.msg = msg;
        this.date = date;
    }
}
