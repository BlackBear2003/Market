package com.wzl.market.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wzl.market.dao.UserMapper;
import com.wzl.market.pojo.User;
import com.wzl.market.service.UserService;
import com.wzl.market.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional
    public ResponseResult setGender(int id, String gender) throws Exception {
        User user = userMapper.selectById(id);
        user.setGender(gender);
        int rows=userMapper.updateById(user);
        if(rows==1){
            return new ResponseResult<>(200,"修改成功");
        }else{
            throw new Exception();
        }
    }

    @Override
    @Transactional
    public ResponseResult setAvatar(int id, String url) throws Exception {
        User user = userMapper.selectById(id);
        user.setAvatarUrl(url);
        int rows=userMapper.updateById(user);
        if(rows==1){
            return new ResponseResult<>(200,"修改成功");
        }else{
            throw new Exception();
        }
    }

    @Override
    @Transactional
    public ResponseResult setLocation(int id,String tempAddress,String tempAddressProvince,String tempAddressCity,String tempAddressArea,double tempAddressLat,double tempAddressLng) throws Exception {
        User user = userMapper.selectById(id);
        user.setTempAddress(tempAddress);
        user.setTempAddressProvince(tempAddressProvince);
        user.setTempAddressCity(tempAddressCity);
        user.setTempAddressArea(tempAddressArea);
        user.setTempAddressLat(tempAddressLat);
        user.setTempAddressLng(tempAddressLng);
        int rows=userMapper.updateById(user);
        if(rows==1){
            return new ResponseResult<>(200,"修改成功");
        }else{
            throw new Exception();
        }
    }

    @Override
    public ResponseResult setInfo(int id, String gender, String avatarUrl, String phoneNumber, String email) throws Exception {
        User user = userMapper.selectById(id);
        user.setGender(gender);
        user.setAvatarUrl(avatarUrl);
        user.setGender(gender);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        int rows=userMapper.updateById(user);
        if(rows==1){
            return new ResponseResult<>(200,"修改成功");
        }else{
            throw new Exception();
        }
    }
}
