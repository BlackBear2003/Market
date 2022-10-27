package com.wzl.market.service;

import com.wzl.market.pojo.User;
import com.wzl.market.utils.ResponseResult;

import java.util.List;

public interface UserService {

    ResponseResult setGender(int id,String gender) throws Exception;

    ResponseResult setAvatar(int id,String url) throws Exception;

    public ResponseResult setLocation(int id,String tempAddress,String tempAddressProvince,String tempAddressCity,String tempAddressArea,double tempAddressLat,double tempAddressLng) throws Exception;

    public ResponseResult setInfo(int id,String gender,String avatarUrl,String phoneNumber,String email) throws Exception;
}
