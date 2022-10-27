package com.wzl.market.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Date;

import static com.baomidou.mybatisplus.annotation.IdType.AUTO;

@Data
@RequiredArgsConstructor
@TableName("t_refund_request")
public class RefundRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type=AUTO)
    private int refundId;
    private int orderId;
    private double refundMoney;
    private String reason;
    private String imgUrl;
    private int IsAgree;
    private int refundStatus;
    private Date requestTime;
    private Date confirmTime;
    private Date refundTime;
}
