package cn.itcast.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class TbOrder implements Serializable {
    private String id;

    private Integer totalNum;

    private Integer totalMoney;

    private Integer preMoney;

    private Integer postFee;

    private Integer payMoney;

    private String payType;

    private Date createTime;

    private Date updateTime;

    private Date payTime;

    private Date consignTime;

    private Date endTime;

    private Date closeTime;

    private String shippingName;

    private String shippingCode;

    private String username;

    private String buyerMessage;

    private String buyerRate;

    private String receiverContact;

    private String receiverMobile;

    private String receiverProvince;

    private String receiverCity;

    private String receiverArea;

    private String receiverAddress;

    private String sourceType;

    private String transactionId;

    private String orderStatus;

    private String payStatus;

    private String consignStatus;

    private String isDelete;


}