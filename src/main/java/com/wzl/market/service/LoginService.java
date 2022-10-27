package com.wzl.market.service;

import com.wzl.market.pojo.User;
import com.wzl.market.utils.ResponseResult;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
