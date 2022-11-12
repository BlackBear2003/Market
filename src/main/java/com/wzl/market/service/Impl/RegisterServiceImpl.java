package com.wzl.market.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzl.market.dao.UserMapper;
import com.wzl.market.dao.UserRoleBindMapper;
import com.wzl.market.pojo.User;
import com.wzl.market.pojo.UserRoleBind;
import com.wzl.market.service.RegisterService;
import com.wzl.market.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRoleBindMapper userRoleBindMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult register(String username, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",username);
        User user = userMapper.selectOne(wrapper);
        if(user==null){
            //可以注册
            user = new User(0,username,password);
            userMapper.insert(user);
            userMapper.setRole(user.getUserId(), 2);
            UserRoleBind bind = new UserRoleBind();
            bind.setUserId(user.getUserId());
            bind.setRoleId(2);
            userRoleBindMapper.insert(bind);
            return new ResponseResult(200,"注册成功");
        }else{
            return new ResponseResult(HttpStatus.NOT_ACCEPTABLE.value(), "用户名已被使用");
        }
    }
}
