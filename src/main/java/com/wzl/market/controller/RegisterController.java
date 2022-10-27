package com.wzl.market.controller;

import com.wzl.market.service.Impl.RegisterServiceImpl;
import com.wzl.market.service.RegisterService;
import com.wzl.market.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    @Autowired
    RegisterServiceImpl registerService;

    @PostMapping("/user/register")
    public ResponseResult register(String username,String password){
        return registerService.register(username, password);
    }
}
