package com.wzl.market.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzl.market.pojo.Cart;

import java.util.List;

public interface CartService extends IService<Cart> {
    public List<Cart> getCartsByUserId(int user_id);

    public Cart addCart(int user_id,Cart cart);

    public int deleteCart(int user_id,int cart_id);
}
