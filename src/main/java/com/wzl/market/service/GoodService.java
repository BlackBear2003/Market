package com.wzl.market.service;

import com.wzl.market.pojo.Good;
import com.wzl.market.pojo.GoodPicture;
import com.wzl.market.security.LoginUser;
import com.wzl.market.utils.ResponseResult;

public interface GoodService {

    public LoginUser putOnGood(Good good);

    public ResponseResult setGoodPicture(GoodPicture goodPicture);

    public ResponseResult setPrice(int id,double price);

    public ResponseResult publish(int id);
    public ResponseResult unPublish(int id);

    public ResponseResult getGood(int id);

}
