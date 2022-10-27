package com.wzl.market.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import static com.baomidou.mybatisplus.annotation.IdType.AUTO;

import java.io.Serializable;
import java.util.Date;

@Data
@RequiredArgsConstructor
@TableName("t_order")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type= AUTO)
    private int orderId;
    private int userId;
    private String userName;
    private String province;
    private String city;
    private String area;
    private String address;
    private int paymentMethod;
    private double orderMoney;
    private int shipType;
    private String shippingCompName;
    private String shippingSn;
    private Date createTime;
    private Date shippingTime;
    private Date payTime;
    private Date receiveTime;
    private int orderStatus;

}
