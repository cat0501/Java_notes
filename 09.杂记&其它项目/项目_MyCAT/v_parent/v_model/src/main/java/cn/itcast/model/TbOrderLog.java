package cn.itcast.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@Data
@NoArgsConstructor
public class TbOrderLog implements Serializable {
    private String id;

    private String operater;

    private Date operateTime;

    private Long orderId;

    private String orderStatus;

    private String payStatus;

    private String consignStatus;

    private String remarks;

}