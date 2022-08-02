package cn.iocoder.yudao.module.system.controller.admin.dept.vo.post;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import cn.iocoder.yudao.module.system.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 岗位 Excel 导出响应 VO
 */
@Data
public class PostExcelVO {
    //① 每个字段上的 @ExcelProperty (opens new window)注解，声明 Excel Head 头部的名字。
    //② 每个字段的值，就是它对应的 Excel Row 行的数据值。

    @ExcelProperty("岗位序号")
    private Long id;

    @ExcelProperty("岗位编码")
    private String code;

    @ExcelProperty("岗位名称")
    private String name;

    @ExcelProperty("岗位排序")
    private Integer sort;

    // 注解的 converter 属性是 DictConvert 转换器，通过它将 status = 1 转换成“开启”列，status = 0 转换成”禁用”列。
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.COMMON_STATUS)
    private String status;

}
