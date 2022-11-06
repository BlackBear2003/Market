package com.wzl.market.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzl.market.pojo.Good;
import com.wzl.market.pojo.GoodComment;
import com.wzl.market.pojo.GoodPicture;
import com.wzl.market.security.LoginUser;
import com.wzl.market.utils.ResponseResult;

public interface GoodService extends IService<Good> {

    public ResponseResult putOnGood(int store_id,Good good);

    public ResponseResult setGoodPicture(GoodPicture goodPicture);

    public ResponseResult setPrice(int id,double price);

    public ResponseResult publish(int id);
    public ResponseResult unPublish(int id);

    public ResponseResult getGood(int id);

    public ResponseResult getGoodsByCategoryId(int cateId);

    public ResponseResult getGoodPicture(int goodId);

    public ResponseResult deleteGoodPicture(int pictureId);

    public ResponseResult deleteGood(int good_id);

    //TODO:public ResponseResult doComment(GoodComment goodComment);   COMMENT STUFF SHOULD BE DONE AFTER ORDER SECTION



}
