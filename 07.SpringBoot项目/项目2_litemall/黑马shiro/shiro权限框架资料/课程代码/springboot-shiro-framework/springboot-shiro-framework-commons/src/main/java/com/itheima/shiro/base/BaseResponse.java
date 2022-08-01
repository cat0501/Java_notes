package com.itheima.shiro.base;

import com.itheima.shiro.utils.ToString;
import lombok.Data;

/**
 * @Description 基础返回封装
 */
@Data
public class BaseResponse extends ToString {
    private String code ;

    private String msg ;

    private String date;

    private static final long serialVersionUID = -1;

    public BaseResponse() {
    }

    public BaseResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResponse(String code, String msg, String date) {
        this.code = code;
        this.msg = msg;
        this.date = date;
    }


}
