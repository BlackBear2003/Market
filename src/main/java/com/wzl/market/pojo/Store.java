package com.wzl.market.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.awt.*;
import java.io.Serializable;

import static com.baomidou.mybatisplus.annotation.IdType.AUTO;

@Data
@RequiredArgsConstructor
@TableName("t_store")
public class Store implements Serializable {
    private static final long serialVersionUID=1L;
    @TableId(type=AUTO)
    private int storeId;
    private String storeName;
    private String storeIntroduction;
    private String store_phone;
    private String store_address;
    private String store_address_province;
    private String store_address_city;
    private String store_address_area;
    private double store_address_lat;
    private double store_address_lng;
}
