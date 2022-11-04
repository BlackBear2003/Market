package com.wzl.market.controller;

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
@RequestMapping("/user")
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
    public ResponseResult getInfo(@PathVariable("id")int id){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int login_id = loginUser.getUser().getUserId();
        if(login_id==id||loginUser.getAuthorities().contains("admin")){
            return userService.getAllInfo(id);
        }
        else{
            return userService.getPublicInfo(id);
        }
    }

    @PutMapping("/{id}")
    public ResponseResult updateInfo(@PathVariable("id")int id,@RequestBody User user) throws Exception {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int login_id = loginUser.getUser().getUserId();
        if(login_id==id||loginUser.getAuthorities().contains("admin")){
            return userService.setInfo(login_id,user.getGender(),user.getAvatarUrl(),user.getPhoneNumber(),user.getEmail());
        }
        else{
            return new ResponseResult(403,"权限不足");
        }
    }

    @GetMapping("/location")
    public ResponseResult getUserLocation(){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int login_id = loginUser.getUser().getUserId();
        return userService.getAllInfo(login_id);
    }

    @PutMapping ("/location")
    public ResponseResult setLocation(String tempAddress,String tempAddressProvince,String tempAddressCity,String tempAddressArea,double tempAddressLat,double tempAddressLng) throws Exception {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int login_id = loginUser.getUser().getUserId();
        return userService.setLocation(login_id,tempAddress,tempAddressProvince,tempAddressCity,tempAddressArea,tempAddressLat,tempAddressLng);
    }

}
