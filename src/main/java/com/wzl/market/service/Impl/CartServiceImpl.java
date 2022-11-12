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
        for (String key:
             scan("cartCache::user_id_"+user_id)) {
            list.add(redisCache.getCacheObject(key));
        }
        return list;
    }

    @Override
    @CachePut(value = "cartCache",key = "'user_id_'+#user_id+'_cart_id_'+#result.cartId")
    @Transactional
    public Cart addCart(int user_id, Cart cart) {
        cart.setUserId(user_id);
        cart.setAddTime(new Date(System.currentTimeMillis()));
        cartMapper.insert(cart);
        return cart;
    }

    @Override
    @CacheEvict(value = "cartCache",key = "'user_id_'+#user_id+'_cart_id_'+#result")
    @Transactional
    public int deleteCart(int user_id, int cart_id) {
        cartMapper.deleteById(cart_id);
        return cart_id;
    }

    public Set<String> scan(String matchKey) {
        Set<String> keys = (Set<String>) redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            Set<String> keysTmp = new HashSet<>();
            Cursor<byte[]> cursor = connection.scan(KeyScanOptions.scanOptions().match("*" + matchKey + "*").count(1000).build());
            while (cursor.hasNext()) {
                keysTmp.add(new String(cursor.next()));
            }
            cursor.close();
            return keysTmp;
        });
        return keys;
    }

}
