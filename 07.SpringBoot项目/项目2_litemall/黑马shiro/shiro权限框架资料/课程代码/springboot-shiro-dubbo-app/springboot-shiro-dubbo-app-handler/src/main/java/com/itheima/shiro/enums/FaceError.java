package com.itheima.shiro.enums;

/**
 * @Description：StudentFace接口异常定义
 */
public enum FaceError implements BaseEnum {

    COMPANY_IS_EMPTY("COMPANY_IS_EMPTY", "公司不存在"),
    COMPANY_SAVE_ERROR("COMPANY_SAVE_ERROR", "公司新增或更新失败"),

    ;

    private String code;
    private String desc;

    FaceError(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

}
