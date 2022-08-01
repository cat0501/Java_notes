package com.itheima.shiro.view;

import com.itheima.shiro.utils.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 返回结果
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JsonResult extends ToString {

    private static final long serialVersionUID = -1;

    private String result;

    private String code;

    private String msg;

    private Object datas;

}
