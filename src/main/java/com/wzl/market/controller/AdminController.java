package com.wzl.market.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzl.market.pojo.Good;
import com.wzl.market.pojo.GoodComment;
import com.wzl.market.pojo.User;
import com.wzl.market.service.Impl.GoodCommentServiceImpl;
import com.wzl.market.service.Impl.GoodServiceImpl;
import com.wzl.market.service.Impl.UserServiceImpl;
import com.wzl.market.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    GoodServiceImpl goodService;
    @Autowired
    GoodCommentServiceImpl goodCommentService;

    @GetMapping("/management/user")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseResult getAllUser(int current,int size){
        return userService.getUsersByPage(current, size);
    }

    @GetMapping("/management/user/{user_id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseResult getUserInfo(@PathVariable("user_id")int id){
        return userService.getAllInfo(id);
    }

    @PutMapping("/management/user/{user_id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseResult changeUserInfo(@PathVariable("user_id")int id,int status){
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("user_id",id).set("audit_status",1);//ban
        userService.update(userUpdateWrapper);
        return userService.getAllInfo(id);
    }

    @GetMapping("/management/good")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseResult getAllGood(int current,int size){
        Page<Good> page = new Page<Good>(current,size);
        IPage<Good> iPage = goodService.page(page);
        return new ResponseResult(200,"success",iPage.getRecords());
    }

    @GetMapping("/management/good/{good_id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseResult getGoodInfo(@PathVariable("good_id")int id){
        return goodService.getGood(id);
    }

    @PutMapping("/management/good/{good_id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseResult changeGoodInfo(@PathVariable("good_id")int id,int status){
        UpdateWrapper<Good> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("good_id",id).set("audit_status",1);//ban
        goodService.update(userUpdateWrapper);
        return goodService.getGood(id);
    }

    @GetMapping("/management/comment")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseResult getAllComment(int current,int size){
        Page<GoodComment> page = new Page<GoodComment>(current,size);
        IPage<GoodComment> iPage = goodCommentService.page(page);
        return new ResponseResult(200,"success",iPage.getRecords());
    }

    @GetMapping("/management/comment/{comment_id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseResult getGoodCommentInfo(@PathVariable("comment_id")int id){
        return new ResponseResult(200,"success",goodCommentService.getById(id));
    }

    @PutMapping("/management/comment/{comment_id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseResult changeGoodCommentInfo(@PathVariable("comment_id")int id,int status){
        UpdateWrapper<GoodComment> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("comment_id",id).set("audit_status",1);//ban
        goodCommentService.update(userUpdateWrapper);
        return new ResponseResult(200,"success",goodCommentService.getById(id));
    }



}
