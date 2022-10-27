package com.wzl.market.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

import static com.baomidou.mybatisplus.annotation.IdType.AUTO;

@Data
@RequiredArgsConstructor
@TableName("t_order_detail")
public class OrderDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = AUTO)
    private int orderDetailId;
    private int orderId;
    private int goodId;
    private String goodName;
    private int goodCnt;
    private double goodPrice;

}
