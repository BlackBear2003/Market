package com.wzl.market.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzl.market.dao.GoodCommentMapper;
import com.wzl.market.dao.GoodMapper;
import com.wzl.market.dao.GoodPictureMapper;
import com.wzl.market.dao.StoreMapper;
import com.wzl.market.pojo.Good;
import com.wzl.market.pojo.GoodPicture;
import com.wzl.market.security.LoginUser;
import com.wzl.market.service.GoodService;
import com.wzl.market.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Wrapper;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodServiceImpl implements GoodService {

    @Autowired
    StoreMapper storeMapper;
    @Autowired
    GoodMapper goodMapper;
    @Autowired
    GoodPictureMapper goodPictureMapper;

    @Override
    @Transactional
    public ResponseResult putOnGood(Good good) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = loginUser.getUser().getUserId();
        int store_id=storeMapper.selectStoreIdByUserId(id);
        good.setGood_id(0);
        goodMapper.insert(good);
        storeMapper.insertGoodIdForStore(store_id,good.getGood_id());
        return new ResponseResult<>(200,"success",good);
    }

    @Override
    @Transactional
    public ResponseResult setGoodPicture(GoodPicture goodPicture) {
        QueryWrapper<Good> wrapper = new QueryWrapper<>();
        wrapper.eq("good_id",goodPicture.getGoodId());
        long cnt = goodMapper.selectCount(wrapper);
        if(cnt==1){
            goodPicture.setPictureId(0);
            goodPictureMapper.insert(goodPicture);
            return new ResponseResult<>(200,"success",goodPicture);
        }
        else return new ResponseResult<>(HttpStatus.SERVICE_UNAVAILABLE.value(), "没有找到对应商品");
    }

    @Override
    @Transactional
    public ResponseResult setPrice(int id, double price) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int user_id = loginUser.getUser().getUserId();
        int storeId=storeMapper.selectStoreIdByUserId(user_id);
        List<Integer> idList = storeMapper.getGoodIdByStoreId(storeId);
        for (int x:
             idList) {
            if(x==id) {
                Good good = goodMapper.selectById(id);
                good.setPrice(price);
                goodMapper.updateById(good);
                return new ResponseResult<>(200,"success",good);
            }
        }
        return new ResponseResult<>(HttpStatus.UNAUTHORIZED.value(), "不是您的商品");
    }

    @Override
    @Transactional
    public ResponseResult publish(int id) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int user_id = loginUser.getUser().getUserId();
        int storeId=storeMapper.selectStoreIdByUserId(user_id);
        List<Integer> idList = storeMapper.getGoodIdByStoreId(storeId);
        for (int x:
                idList) {
            if(x==id) {
                Good good = goodMapper.selectById(id);
                good.setPublishStatus(1);
                good.setPublishDate(new Date(System.currentTimeMillis()));
                goodMapper.updateById(good);
                return new ResponseResult<>(200,"success",good);
            }
        }
        return new ResponseResult<>(HttpStatus.UNAUTHORIZED.value(), "不是您的商品");
    }

    @Override
    @Transactional
    public ResponseResult unPublish(int id) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int user_id = loginUser.getUser().getUserId();
        int storeId=storeMapper.selectStoreIdByUserId(user_id);
        List<Integer> idList = storeMapper.getGoodIdByStoreId(storeId);
        for (int x:
                idList) {
            if(x==id) {
                Good good = goodMapper.selectById(id);
                good.setPublishStatus(0);
                goodMapper.updateById(good);
                return new ResponseResult<>(200,"success",good);
            }
        }
        return new ResponseResult<>(HttpStatus.UNAUTHORIZED.value(), "不是您的商品");
    }

    @Override
    @Transactional
    public ResponseResult getGood(int id) {
        Good good = goodMapper.selectById(id);
        return new ResponseResult<>(200,"success",good);
    }

    @Override
    @Transactional
    public ResponseResult getGoodsByCategoryId(int cateId) {
        List<Good> list = goodMapper.getGoodsByCategoryId(cateId);
        return new ResponseResult<>(200,"success",list);
    }

    @Override
    @Transactional
    public ResponseResult getGoodPicture(int goodId) {
        Map<String,Object> map = new HashMap<>();
        map.put("good_id",goodId);
        List<GoodPicture> list = goodPictureMapper.selectByMap(map);
        return new ResponseResult<>(200,"success",list);
    }

    @Override
    @Transactional
    public ResponseResult deleteGoodPicture(int pictureId) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int user_id = loginUser.getUser().getUserId();
        int storeId=storeMapper.selectStoreIdByUserId(user_id);
        List<Integer> idList = storeMapper.getGoodIdByStoreId(storeId);
        GoodPicture goodPicture=goodPictureMapper.selectById(pictureId);
        for (int x:
             idList) {
            if(x==goodPicture.getGoodId()){
                goodPictureMapper.deleteById(pictureId);
                return new ResponseResult<>(200,"success deleted");
            }
        }
        return new ResponseResult<>(HttpStatus.UNAUTHORIZED.value(), "不是您的商品");
    }


}
