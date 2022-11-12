package com.wzl.market.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzl.market.pojo.Cart;
import com.wzl.market.pojo.Good;
import com.wzl.market.security.LoginUser;
import com.wzl.market.service.Impl.CartServiceImpl;
import com.wzl.market.service.Impl.GoodServiceImpl;
import com.wzl.market.service.Impl.LocationServiceImpl;
import com.wzl.market.service.Impl.SearchServiceImpl;
import com.wzl.market.utils.RedisCache;
import com.wzl.market.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;

@RestController
@RequestMapping("/api/shop")
public class ShopController {
    @Autowired
    GoodServiceImpl goodService;

    @Autowired
    SearchServiceImpl searchService;

    @Autowired
    LocationServiceImpl locationService;
    @Autowired
    RedisCache redisCache;
    @Autowired
    CartServiceImpl cartService;


    @PostMapping("/search")
    public ResponseResult search(double lng,double lat,String goodName,double down,double up,int categoryId, int current, int size){
        return searchService.search(lng,lat,goodName, down, up, categoryId, current, size);
    }

    @GetMapping("/cart")
    public ResponseResult getUserCart(){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int user_id = loginUser.getUser().getUserId();
        return new ResponseResult(200,"success",cartService.getCartsByUserId(user_id));
    }

    @PostMapping("/cart")
    public ResponseResult addCart(@RequestBody Cart cart){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int user_id = loginUser.getUser().getUserId();
        return new ResponseResult(200,"success",cartService.addCart(user_id,cart));
    }

    @DeleteMapping("/cart")
    public ResponseResult deleteCart(int cart_id) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int user_id = loginUser.getUser().getUserId();
        return new ResponseResult(200,"success",cartService.deleteCart(user_id,cart_id));
    }
}
