package com.wzl.market.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzl.market.dao.GoodMapper;
import com.wzl.market.dao.StoreMapper;
import com.wzl.market.pojo.Good;
import com.wzl.market.pojo.Store;
import com.wzl.market.security.LoginUser;
import com.wzl.market.service.GoodService;
import com.wzl.market.service.StoreService;
import com.wzl.market.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    StoreMapper storeMapper;
    @Autowired
    GoodMapper goodMapper;


    @Override
    @Transactional
    public ResponseResult create(Store store) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = loginUser.getUser().getUserId();
        store.setStoreId(0);
        storeMapper.insert(store);
        storeMapper.insertUserStoreBind(id,store.getStoreId());
        return new ResponseResult<>(200,"创建成功");
    }

    @Override
    @Transactional
    public ResponseResult locate(String tempAddress, String tempAddressProvince, String tempAddressCity, String tempAddressArea, double tempAddressLat, double tempAddressLng) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = loginUser.getUser().getUserId();
        int store_id=storeMapper.selectStoreIdByUserId(id);
        Store store = storeMapper.selectById(store_id);
        store.setStoreAddress(tempAddress);
        store.setStoreAddressProvince(tempAddressProvince);
        store.setStoreAddressCity(tempAddressCity);
        store.setStoreAddressArea(tempAddressArea);
        store.setStoreAddressLat(tempAddressLat);
        store.setStoreAddressLng(tempAddressLng);
        storeMapper.updateById(store);
        return new ResponseResult(200,"修改成功");
    }

    @Override
    @Transactional
    public ResponseResult delete() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = loginUser.getUser().getUserId();
        int storeId=storeMapper.selectStoreIdByUserId(id);
        storeMapper.deleteById(storeId);
        storeMapper.deleteUserStoreBind(id);
        return new ResponseResult(200,"删除成功");
    }

    @Override
    @Transactional
    public ResponseResult get(int storeId) {
        Store store = storeMapper.selectById(storeId);
        return new ResponseResult(200,"查询成功",store);
    }

    @Override
    @Transactional
    public ResponseResult update(Store store) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = loginUser.getUser().getUserId();
        int storeId=storeMapper.selectStoreIdByUserId(id);
        store.setStoreId(storeId);
        storeMapper.updateById(store);
        return new ResponseResult(200,"修改成功");
    }

    @Override
    @Transactional
    public ResponseResult listPublishGoods(int storeId) {
        List<Integer> idList = storeMapper.getGoodIdByStoreId(storeId);
        QueryWrapper<Good> wrapper=new QueryWrapper<>();
        wrapper.in("good_id",idList);
        wrapper.eq("publish_status",1);
        List<Good> list = goodMapper.selectList(wrapper);
        return new ResponseResult<>(200,"查询成功",list);
    }

    @Override
    @Transactional
    public ResponseResult listUnPublishGoods() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = loginUser.getUser().getUserId();
        int storeId=storeMapper.selectStoreIdByUserId(id);
        List<Integer> idList = storeMapper.getGoodIdByStoreId(storeId);
        QueryWrapper<Good> wrapper=new QueryWrapper<>();
        wrapper.in("good_id",idList);
        wrapper.eq("publish_status",0);
        List<Good> list = goodMapper.selectList(wrapper);
        return new ResponseResult<>(200,"查询成功",list);
    }
}
