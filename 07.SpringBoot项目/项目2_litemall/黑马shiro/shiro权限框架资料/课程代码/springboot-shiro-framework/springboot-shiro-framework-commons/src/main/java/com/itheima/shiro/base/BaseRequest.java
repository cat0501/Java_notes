package com.itheima.shiro.base;

/**
 * @Description 基础请求封装
 */
public class BaseRequest extends HeadRequest {

    Object datas;

    public Object getDatas() {
        return datas;
    }

    public void setDatas(Object datas) {
        this.datas = datas;
    }
}
