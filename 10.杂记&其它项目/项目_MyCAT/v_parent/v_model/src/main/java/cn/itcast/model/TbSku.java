package cn.itcast.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class TbSku implements Serializable {
    private String id;

    private String sn;

    private String name;

    private Integer price;

    private Integer num;

    private Integer alertNum;

    private String image;

    private String images;

    private Integer weight;

    private Date createTime;

    private Date updateTime;

    private String spuId;

    private Integer categoryId;

    private String categoryName;

    private String brandName;

    private String spec;

    private Integer saleNum;

    private Integer commentNum;

    private String status;

    private Integer version;

}