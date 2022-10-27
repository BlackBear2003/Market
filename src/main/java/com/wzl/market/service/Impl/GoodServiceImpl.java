package com.wzl.market.service.Impl;

import com.wzl.market.pojo.Good;
import com.wzl.market.pojo.GoodPicture;
import com.wzl.market.security.LoginUser;
import com.wzl.market.service.GoodService;
import com.wzl.market.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class GoodServiceImpl implements GoodService {

    @Override
    public LoginUser putOnGood(Good good) {
        LoginUser loginUser=(LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(loginUser.getUser().getUserId());
        return loginUser;
    }

    @Override
    public ResponseResult setGoodPicture(GoodPicture goodPicture) {
        return null;
    }

    @Override
    public ResponseResult setPrice(int id, double price) {
        return null;
    }

    @Override
    public ResponseResult publish(int id) {
        return null;
    }

    @Override
    public ResponseResult unPublish(int id) {
        return null;
    }

    @Override
    public ResponseResult getGood(int id) {
        return null;
    }
}
