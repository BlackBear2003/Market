package com.wzl.market.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzl.market.dao.CartMapper;
import com.wzl.market.pojo.Cart;
import com.wzl.market.service.CartService;
import com.wzl.market.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Duration;
import java.util.*;

@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    @Autowired
    CartMapper cartMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RedisCache redisCache;



    @Override
    public List<Cart> getCartsByUserId(int user_id) {
        List<Cart> list = new ArrayList<>();
        Cursor<Map.Entry<Object,Object>> cursor = redisTemplate.opsForHash().scan("cart:"+user_id,
                ScanOptions.NONE);
        while (cursor.hasNext()) {
            Map.Entry<Object,Object> entry = cursor.next();
            Object key = entry.getKey();
            Object valueSet = entry.getValue();
            list.add((Cart) valueSet);
        }
        //关闭cursor
        cursor.close();
        return list;
    }

    @Override
    public void addCart(int user_id, Cart cart) {
        cart.setUserId(user_id);
        cart.setCartId(UUID.randomUUID().toString());
        cart.setAddTime(new Date(System.currentTimeMillis()));
        redisTemplate.opsForHash().put("cart:"+user_id,"cart_id_"+cart.getCartId(),cart);
        redisTemplate.expire("cart:"+user_id, Duration.ofDays(30));
    }

    @Override
    public void deleteCart(int user_id, int cart_id) {
        redisTemplate.opsForHash().delete("cart:"+user_id,"cart_id_"+cart_id);
    }

}
