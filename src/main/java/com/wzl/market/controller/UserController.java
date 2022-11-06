package com.wzl.market.controller;

import com.wzl.market.aop.UserAuthCheck;
import com.wzl.market.pojo.User;
import com.wzl.market.security.LoginUser;
import com.wzl.market.service.Impl.LoginServiceImpl;
import com.wzl.market.service.Impl.RegisterServiceImpl;
import com.wzl.market.service.Impl.UserServiceImpl;
import com.wzl.market.utils.ResponseResult;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    LoginServiceImpl loginService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    RegisterServiceImpl registerService;

    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping("")
    public ResponseResult getAllUser(@Param("current")int current,@Param("size")int size){
        return userService.getUsersByPage(current, size);
    }

    @PostMapping("")
    public ResponseResult register(String username,String password){
        return registerService.register(username, password);
    }

    @PostMapping("/account/login")
    public ResponseResult login(String username, String password){
        User user = new User();
        user.setUserId(0);
        user.setUserName(username);
        user.setPassword(password);
        return loginService.login(user);
    }
    @PostMapping("/account/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }


    @GetMapping("/{id}")
    @UserAuthCheck
    public ResponseResult getAllInfo(@PathVariable("id")int id){
        return userService.getAllInfo(id);
    }

    @GetMapping("/{id}/public")
    public ResponseResult getPublicInfo(@PathVariable("id")int id){
        return userService.getPublicInfo(id);
    }

    @PutMapping("/{id}")
    @UserAuthCheck
    public ResponseResult updateInfo(@PathVariable("id")int id,@RequestBody User user) throws Exception {
        return userService.setInfo(id,user.getGender(),user.getAvatarUrl(),user.getPhoneNumber(),user.getEmail());
    }

    @GetMapping("/{id}/location")
    @UserAuthCheck
    public ResponseResult getUserLocation(@PathVariable("id")int id){
        return userService.getAllInfo(id);
    }

    @PutMapping ("/{id}/location")
    public ResponseResult setLocation(@PathVariable("id")int id,String tempAddress,String tempAddressProvince,String tempAddressCity,String tempAddressArea,double tempAddressLat,double tempAddressLng) throws Exception {
        return userService.setLocation(id,tempAddress,tempAddressProvince,tempAddressCity,tempAddressArea,tempAddressLat,tempAddressLng);
    }

}
