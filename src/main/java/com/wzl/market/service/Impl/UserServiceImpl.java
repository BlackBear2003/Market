package com.wzl.market.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzl.market.dao.UserMapper;
import com.wzl.market.pojo.User;
import com.wzl.market.security.LoginUser;
import com.wzl.market.service.UserService;
import com.wzl.market.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional
    public ResponseResult setGender(String gender) throws Exception {
        LoginUser loginUser = (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = loginUser.getUser().getUserId();
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
    public ResponseResult setAvatar(String url) throws Exception {
        LoginUser loginUser = (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = loginUser.getUser().getUserId();
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
    @Transactional
    public ResponseResult setInfo(int id,String gender, String avatarUrl, String phoneNumber, String email) throws Exception {
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

    @Override
    @Transactional
    public ResponseResult getAllInfo(int id) {
        User user = userMapper.selectById(id);
        return new ResponseResult(200,"自己的全部信息",user);
    }

    @Override
    @Transactional
    public ResponseResult getPublicInfo(int id) {
        HashMap<String, Object> map = new HashMap<>();
        User user=userMapper.selectById(id);
        map.put("userName",user.getUserName());
        map.put("phoneNumber",user.getPhoneNumber());
        map.put("email",user.getEmail());
        map.put("avatarUrl",user.getAvatarUrl());
        map.put("gender",user.getGender());
        map.put("province",user.getTempAddressProvince());
        map.put("city",user.getTempAddressCity());
        return new ResponseResult(200,"用户的公开信息",map);
    }

    @Override
    @Transactional
    public ResponseResult getUsersByPage(int current,int size){
        Page<User> page = new Page<>(current,size);
        IPage<User> iPage = userMapper.selectPage(page,null);
        long total = iPage.getTotal();
        List<User> list = iPage.getRecords();
        return new ResponseResult(200,"total:"+total,list);
    }
}
