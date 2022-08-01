package com.itheima.shiro.utils;

import com.itheima.shiro.base.BaseResponse;
import com.itheima.shiro.enums.BaseEnum;
import com.itheima.shiro.response.MultiResponse;
import com.itheima.shiro.response.PageResponse;
import com.itheima.shiro.response.SingleResponse;

import java.util.List;

/**
 * @Description 响应结果集构建
 */
public class ResponseBuilder {

    public static BaseResponse succ() {
        BaseResponse rs = new BaseResponse();
        return rs;
    }

    @SuppressWarnings("unchecked")
    public static <T extends BaseResponse> T succ(Class<T> clz) {
        try {
            BaseResponse rs = clz.newInstance();
            return (T) rs;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> SingleResponse<T> succTSingle(T value) {
        SingleResponse<T> result = new SingleResponse<>();
        result.setValue(value);
        return result;
    }

    public static BaseResponse fail(String errcode, String errmsg) {
        BaseResponse rs = new BaseResponse();
        rs.setCode(errcode);
        rs.setMsg(errmsg);
        return rs;
    }

    public static BaseResponse fail(BaseEnum baseEnum) {
        return fail(baseEnum.getCode(), baseEnum.getDesc());
    }

    @SuppressWarnings("unchecked")
    public static <T extends BaseResponse> T fail(Class<T> clz, String errcode, String errmsg) {
        try {
            BaseResponse rs = clz.newInstance();
            rs.setCode(errcode);
            rs.setMsg(errmsg);
            return (T) rs;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> SingleResponse<T> failTSingle(String errcode, String errmsg) {
        SingleResponse<T> result = new SingleResponse<>();
        result.setCode(errcode);
        result.setMsg(errmsg);
        return result;
    }

    public static <T> SingleResponse<T> failTSingle(BaseEnum baseEnum) {
        return failTSingle(baseEnum.getCode(), baseEnum.getDesc());
    }

    public static <T> MultiResponse<T> succTMulti(List<T> values) {
        MultiResponse<T> result = new MultiResponse<>();
        result.setValues(values);
        return result;
    }

    public static <T> MultiResponse<T> failTMulti(String errcode, String errmsg) {
        MultiResponse<T> result = new MultiResponse<>();
        result.setCode(errcode);
        result.setMsg(errmsg);
        return result;
    }

    public static <T> PageResponse<T> failTPage(BaseEnum baseEnum) {
        return failTPage(baseEnum.getCode(), baseEnum.getDesc());
    }

    public static <T> PageResponse<T> failTPage(String errcode, String errmsg) {
        PageResponse<T> result = new PageResponse<>();
        result.setCode(errcode);
        result.setMsg(errmsg);
        return result;
    }

    public static <T> PageResponse<T> succTPage(List<T> values, int page, int rows, int totalCount) {
        PageResponse<T> result = new PageResponse<>();
        result.setValues(values);
        result.setPage(page);
        result.setRow(rows);
        int totalPage = totalCount % rows == 0 ? totalCount / rows : totalCount / rows + 1;
        result.setTotalPage(totalPage > 0 ? totalPage : 1);
        result.setTotalRows(totalCount);
        return result;
    }
}
