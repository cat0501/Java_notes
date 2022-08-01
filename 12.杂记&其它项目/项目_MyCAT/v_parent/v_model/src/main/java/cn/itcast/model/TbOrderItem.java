package cn.itcast.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class TbOrderItem implements Serializable {

    private String id;

    private Integer categoryId1;

    private Integer categoryId2;

    private Integer categoryId3;

    private String spuId;

    private String skuId;

    private String orderId;

    private String name;

    private Integer price;

    private Integer num;

    private Integer money;

    private Integer payMoney;

    private String image;

    private Integer weight;

    private Integer postFee;

    private String isReturn;

}