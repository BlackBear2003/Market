package com.wzl.market.controller;

import com.wzl.market.pojo.Good;
import com.wzl.market.pojo.User;
import com.wzl.market.security.LoginUser;
import com.wzl.market.service.GoodService;
import com.wzl.market.service.Impl.ChatMessageServiceImpl;
import com.wzl.market.service.Impl.GoodServiceImpl;
import com.wzl.market.utils.ResponseResult;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class DemoController {
    @Autowired
    GoodServiceImpl goodService;
    //@ResponseBody
    @Autowired
    ChatMessageServiceImpl chatMessageService;


    @GetMapping("/demo")
    //@PreAuthorize("hasAnyAuthority('goodbye')")
    public ResponseResult getDemo(){
        return null;
    }
    @GetMapping("/hello")
    @PreAuthorize("hasAnyAuthority('hello')")
    public String  getLook(){
        return "hello world";
    }


    @ResponseBody
    @GetMapping("/testJson")
    public User getJson(){
        User user = new User();
        user.setUserId(0);
        user.setUserName("test");
        user.setPassword("test");
        return user;
    }

    @GetMapping("testResponse")
    public ResponseResult getResponse(){
        User user = new User();
        user.setUserId(0);
        user.setUserName("test");
        user.setPassword("test");
        return new ResponseResult<>(200,"获得响应",user);
    }

    @PreAuthorize("hasAuthority('auth1')")
    @GetMapping("testAuth")
    public ResponseResult getAuth(){
        return new ResponseResult(200,"权限够了","假装是一条数据");
    }
    @PreAuthorize("hasAuthority('auth100')")
    @GetMapping("testUnAuth")
    public ResponseResult getUnAuth(){
        return new ResponseResult(403,"权限不够了","假装是一条数据");
    }
}
