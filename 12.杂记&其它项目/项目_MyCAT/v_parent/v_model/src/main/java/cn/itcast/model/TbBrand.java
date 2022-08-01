package cn.itcast.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class TbBrand implements Serializable{
    private Integer id;//品牌id
    private String name;//品牌名称
    private String image;//品牌图片地址
    private String firstChar;//品牌的首字母
    private Integer seq;//排序
}
