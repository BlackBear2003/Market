package com.wzl.market.controller;

import com.wzl.market.pojo.User;
import com.wzl.market.service.Impl.LoginServiceImpl;
import com.wzl.market.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    LoginServiceImpl loginService;

    @PostMapping("/user/login")
    public ResponseResult login(String username,String password){
        User user = new User();
        user.setUserId(0);
        user.setUserName(username);
        user.setPassword(password);
        return loginService.login(user);
    }

    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }

}
