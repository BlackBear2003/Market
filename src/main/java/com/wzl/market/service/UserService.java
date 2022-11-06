package com.wzl.market.service;

import com.wzl.market.pojo.User;
import com.wzl.market.utils.ResponseResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    ResponseResult setGender(String gender) throws Exception;

    ResponseResult setAvatar(String url) throws Exception;

    public ResponseResult setLocation(int id,String tempAddress,String tempAddressProvince,String tempAddressCity,String tempAddressArea,double tempAddressLat,double tempAddressLng) throws Exception;

    public ResponseResult setInfo(int id,String gender,String avatarUrl,String phoneNumber,String email) throws Exception;

    public ResponseResult getAllInfo(int id);
    public ResponseResult getPublicInfo(int id);

    ResponseResult getUsersByPage(int current, int size);
}
