package com.wzl.market.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import static com.baomidou.mybatisplus.annotation.IdType.AUTO;

@Data
public class Cart implements Serializable {
    @TableId(type = AUTO)
    private String cartId;
    private Integer userId;
    private Integer goodId;
    private Integer goodAmount;
    private Date addTime;
}
