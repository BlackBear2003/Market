package com.wzl.market.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

import static com.baomidou.mybatisplus.annotation.IdType.AUTO;
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@TableName("t_user")
public class User implements Serializable {
    private static final long serialVersionUID = 2873551059175095456L;
    @NonNull
    @TableId(value = "user_id",type =AUTO)
    private int userId;
    @NonNull
    private String userName;
    @NonNull
    private String password;
    private String avatarUrl;
    private String gender;
    private String phoneNumber;
    private String email;
    private String tempAddress;
    private String tempAddressProvince;
    private String tempAddressCity;
    private String tempAddressArea;
    private double tempAddressLat;
    private double tempAddressLng;
    private int auditStatus;

}
