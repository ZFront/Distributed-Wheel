package com.wheel.demo.test.sharding.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @desc 订单实体
 * @author: zhouf
 */
@Getter
@Setter
public class Order implements Serializable {
    private Date createTime = new Date();

    private Date completeTime;

    private String userId;

    private String orderNo;

    private String trxNo;

    private BigDecimal orderAmount;
}
