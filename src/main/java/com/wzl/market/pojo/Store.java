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
    private String storePhone;
    private String storeAddress;
    private String storeAddressProvince;
    private String storeAddressCity;
    private String storeAddressArea;
    private double storeAddressLat;
    private double storeAddressLng;
}
